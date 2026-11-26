const router = require('express').Router()
const pool = require('../db/connection')
const { exigir, inteiro, texto, centavos, transacao, movimentar } = require('../util')
const { autenticar, apenasAdmin } = require('../middleware/auth')

// O próprio id também é o código público do produto.
const consulta = `SELECT p.*, p.id AS codigo, c.nome AS categoria
  FROM produtos p JOIN categorias c ON c.id = p.categoria_id`

// Catálogo público mostra apenas produtos ativos, inclusive os esgotados.
router.get('/', async (req, res) => {
  const [rows] = await pool.execute(consulta + ' WHERE p.ativo = 1 ORDER BY p.nome')
  res.json(rows)
})

// Administrador também consulta os produtos desativados.
router.get('/todos', autenticar, apenasAdmin, async (req, res) => {
  const [rows] = await pool.execute(consulta + ' ORDER BY p.nome')
  res.json(rows)
})

// Categorias vêm do banco, sem lista simulada no servidor.
router.get('/categorias', async (req, res) => {
  const [rows] = await pool.execute('SELECT id, nome FROM categorias ORDER BY nome')
  res.json(rows)
})

// Produto desativado fica indisponível também na consulta pública individual.
router.get('/:id', async (req, res) => {
  const id = inteiro(req.params.id, 'Produto')
  const [[produto]] = await pool.execute(consulta + ' WHERE p.id = ? AND p.ativo = 1', [id])
  exigir(produto, 'Produto não encontrado.', 404)
  res.json(produto)
})

// Valida os campos comuns ao cadastro e à edição.
function dadosProduto(body) {
  const nome = texto(body.nome, 'Nome', 200)
  const categoria = inteiro(body.categoria_id, 'Categoria')
  const preco = centavos(body.preco) / 100
  const imagem = body.imagem_url === undefined || body.imagem_url === '' ? '/img/icone-carrinho.png' : body.imagem_url
  exigir(typeof imagem === 'string' && imagem.length <= 500, 'Imagem inválida.')
  exigir(imagem === '/img/icone-carrinho.png' || /^https?:\/\/\S+$/i.test(imagem), 'Imagem deve usar endereço HTTP ou HTTPS.')
  return [nome, categoria, preco, imagem]
}

// Produto e histórico inicial são gravados juntos ou desfeitos juntos.
router.post('/', autenticar, apenasAdmin, async (req, res) => {
  const dados = dadosProduto(req.body)
  const estoque = inteiro(req.body.estoque, 'Estoque', 0)
  const id = await transacao(async conn => {
    const [result] = await conn.execute('INSERT INTO produtos (nome, categoria_id, preco, imagem_url) VALUES (?, ?, ?, ?)', dados)
    if (estoque > 0) await movimentar(conn, { id: result.insertId }, 'entrada', estoque, estoque, 'Estoque inicial no cadastro', req.usuario.id)
    return result.insertId
  })
  res.status(201).json({ mensagem: 'Produto cadastrado!', id })
})

// Edição não altera estoque: o saldo só muda por movimentação registrada.
router.put('/:id', autenticar, apenasAdmin, async (req, res) => {
  const id = inteiro(req.params.id, 'Produto')
  const dados = dadosProduto(req.body)
  const [result] = await pool.execute('UPDATE produtos SET nome = ?, categoria_id = ?, preco = ?, imagem_url = ? WHERE id = ?', [...dados, id])
  exigir(result.affectedRows, 'Produto não encontrado.', 404)
  res.json({ mensagem: 'Produto atualizado!' })
})

// Desativar preserva os pedidos e históricos que referenciam o produto.
router.delete('/:id', autenticar, apenasAdmin, async (req, res) => {
  const id = inteiro(req.params.id, 'Produto')
  const [result] = await pool.execute('UPDATE produtos SET ativo = 0 WHERE id = ?', [id])
  exigir(result.affectedRows, 'Produto não encontrado.', 404)
  res.json({ mensagem: 'Produto desativado!' })
})

module.exports = router
