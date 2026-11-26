const router = require('express').Router()
const pool = require('../db/connection')
const { exigir, inteiro, centavos, transacao, movimentar } = require('../util')
const { autenticar } = require('../middleware/auth')

router.use(autenticar)

router.get('/', async (req, res) => {
  const admin = req.usuario.perfil === 'admin'
  const [pedidos] = await pool.execute(`SELECT p.*, u.nome AS cliente_nome, u.email AS cliente_email
    FROM pedidos p JOIN usuarios u ON u.id = p.usuario_id
    ${admin ? '' : 'WHERE p.usuario_id = ?'} ORDER BY p.id DESC`, admin ? [] : [req.usuario.id])
  for (const pedido of pedidos) {
    const [itens] = await pool.execute('SELECT * FROM itens_pedido WHERE pedido_id = ? ORDER BY id', [pedido.id])
    pedido.itens = itens
  }
  res.json(pedidos)
})

router.get('/:id', async (req, res) => {
  const id = inteiro(req.params.id, 'Pedido')
  const [[pedido]] = await pool.execute('SELECT * FROM pedidos WHERE id = ?', [id])
  exigir(pedido, 'Pedido não encontrado.', 404)
  exigir(req.usuario.perfil === 'admin' || pedido.usuario_id === req.usuario.id, 'Acesso negado.', 403)
  const [itens] = await pool.execute('SELECT * FROM itens_pedido WHERE pedido_id = ? ORDER BY id', [id])
  res.json({ ...pedido, itens })
})

router.post('/', async (req, res) => {
  const { itens } = req.body
  exigir(Array.isArray(itens) && itens.length > 0 && itens.length <= 100, 'Carrinho deve ter de 1 a 100 produtos distintos.')
  const vistos = new Set()
  const carrinho = itens.map(item => {
    exigir(item && typeof item === 'object', 'Item inválido.')
    const id = inteiro(item.produto_id, 'Produto')
    const quantidade = inteiro(item.quantidade, 'Quantidade')
    exigir(!vistos.has(id), 'Produto repetido: envie uma linha com a quantidade total.')
    vistos.add(id)
    return { id, quantidade }
  }).sort((a, b) => a.id - b.id)

  const resultado = await transacao(async conn => {
    let subtotal = 0
    const confirmados = []

    for (const item of carrinho) {
      const [[produto]] = await conn.execute('SELECT * FROM produtos WHERE id = ? FOR UPDATE', [item.id])
      exigir(produto && produto.ativo, 'Produto inexistente ou indisponível.', 409)
      exigir(produto.estoque >= item.quantidade, `Estoque insuficiente para ${produto.nome}.`, 409)
      const preco = centavos(produto.preco)
      const valor = preco * item.quantidade
      subtotal += valor
      exigir(Number.isSafeInteger(subtotal) && subtotal <= 9999999999, 'Valor do pedido excede o limite.')
      confirmados.push({ produto, quantidade: item.quantidade, preco, valor })
    }

    const taxa = subtotal >= 50000 ? 15 : subtotal >= 30000 ? 10 : 0
    const desconto = Math.round(subtotal * taxa / 100)
    const total = subtotal - desconto
    const [pedido] = await conn.execute('INSERT INTO pedidos (usuario_id, subtotal, desconto, total) VALUES (?, ?, ?, ?)',
      [req.usuario.id, subtotal / 100, desconto / 100, total / 100])
    for (const item of confirmados) {

      await conn.execute(`INSERT INTO itens_pedido
        (pedido_id, produto_id, nome_produto, preco_unitario, quantidade, subtotal) VALUES (?, ?, ?, ?, ?, ?)`,
      [pedido.insertId, item.produto.id, item.produto.nome, item.preco / 100, item.quantidade, item.valor / 100])
      await movimentar(conn, item.produto, 'saida', item.quantidade, item.produto.estoque - item.quantidade,
        `Venda - Pedido #${pedido.insertId}`, req.usuario.id)
    }
    return { pedido_id: pedido.insertId, subtotal: subtotal / 100, desconto: desconto / 100, total: total / 100 }
  })
  res.status(201).json({ mensagem: 'Pedido confirmado!', ...resultado })
})

module.exports = router
