const router = require('express').Router()
const pool = require('../db/connection')
const { exigir, inteiro, texto, transacao, movimentar } = require('../util')
const { autenticar, apenasAdmin } = require('../middleware/auth')

// Todo o módulo de estoque é exclusivo de administradores.
router.use(autenticar, apenasAdmin)

// Relaciona histórico, produto e responsável sem perder movimentos antigos.
router.get('/historico', async (req, res) => {
  const [rows] = await pool.execute(`SELECT m.*, p.nome AS produto_nome, u.nome AS usuario_nome
    FROM movimentacoes_estoque m JOIN produtos p ON p.id = m.produto_id
    LEFT JOIN usuarios u ON u.id = m.usuario_id ORDER BY m.id DESC`)
  res.json(rows)
})

// Estoque baixo significa saldo entre 1 e 3; zero aparece separadamente.
router.get('/dashboard', async (req, res) => {
  const [[metricas]] = await pool.execute(`SELECT
    (SELECT COUNT(*) FROM produtos WHERE ativo = 1) AS total_produtos,
    (SELECT COUNT(*) FROM produtos WHERE ativo = 1 AND estoque BETWEEN 1 AND 3) AS estoque_baixo,
    (SELECT COUNT(*) FROM produtos WHERE ativo = 1 AND estoque = 0) AS sem_estoque,
    (SELECT COUNT(*) FROM pedidos) AS total_pedidos,
    (SELECT COUNT(*) FROM usuarios WHERE perfil = 'cliente' AND ativo = 1) AS total_clientes`)
  res.json(metricas)
})

// Uma única implementação atende entrada, saída e ajuste nas mesmas URLs.
router.post('/:tipo', async (req, res) => {
  const { tipo } = req.params
  exigir(['entrada', 'saida', 'ajuste'].includes(tipo), 'Rota não encontrada.', 404)
  const id = inteiro(req.body.produto_id, 'Produto')
  const quantidade = inteiro(req.body.quantidade, 'Quantidade', tipo === 'ajuste' ? 0 : 1)
  const motivo = tipo === 'entrada' && !req.body.motivo ? 'Reposição administrativa' : texto(req.body.motivo, 'Motivo', 255)
  const saldo = await transacao(async conn => {
    // O bloqueio impede que duas operações sobrescrevam o mesmo saldo.
    const [[produto]] = await conn.execute('SELECT id, estoque FROM produtos WHERE id = ? AND ativo = 1 FOR UPDATE', [id])
    exigir(produto, 'Produto não encontrado.', 404)
    const novoSaldo = tipo === 'ajuste' ? quantidade : produto.estoque + (tipo === 'entrada' ? quantidade : -quantidade)
    exigir(novoSaldo >= 0, 'Estoque insuficiente.', 409)
    const diferenca = tipo === 'ajuste' ? Math.abs(novoSaldo - produto.estoque) : quantidade
    await movimentar(conn, produto, tipo, diferenca, novoSaldo, motivo, req.usuario.id)
    return novoSaldo
  })
  res.json({ mensagem: 'Movimentação registrada!', novo_estoque: saldo })
})

module.exports = router
