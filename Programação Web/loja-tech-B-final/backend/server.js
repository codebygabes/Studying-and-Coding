require('dotenv').config({ path: require('node:path').join(__dirname, '.env'), quiet: true })
const express = require('express')
const path = require('node:path')
const pool = require('./db/connection')

const app = express()
app.disable('x-powered-by')
app.use(express.json({ limit: '32kb' }))

app.use((req, res, next) => {
  req.body = req.body || {}
  next()
})

app.use('/api/auth', require('./routes/authRoutes'))
app.use('/api/produtos', require('./routes/produtoRoutes'))
app.use('/api/pedidos', require('./routes/pedidoRoutes'))
app.use('/api/estoque', require('./routes/estoqueRoutes'))

app.use(express.static(path.join(__dirname, '../frontend')))
app.use((req, res) => res.status(404).json({ erro: 'Rota não encontrada.' }))

app.use((erro, req, res, next) => {
  let status = erro.status || 500
  let mensagem = status < 500 ? erro.message : 'Erro interno ao acessar o banco.'
  if (erro.code === 'ER_DUP_ENTRY') { status = 409; mensagem = 'E-mail ou registro já cadastrado.' }
  if (erro.code === 'ER_NO_REFERENCED_ROW_2') { status = 400; mensagem = 'Categoria ou referência inexistente.' }
  if (['ER_LOCK_DEADLOCK', 'ER_LOCK_WAIT_TIMEOUT'].includes(erro.code)) { status = 409; mensagem = 'Conflito simultâneo. Tente novamente.' }
  if (erro.type === 'entity.parse.failed') { status = 400; mensagem = 'JSON inválido.' }
  if (erro.type === 'entity.too.large') { status = 413; mensagem = 'Requisição muito grande.' }
  if (status >= 500) console.error('Falha na API:', erro.code || erro.name)
  res.status(status).json({ erro: mensagem })
})

async function iniciar() {
  const chave = process.env.JWT_SECRET || ''
  if (chave.length < 32 || chave.includes('SUBSTITUA')) throw new Error('Defina JWT_SECRET aleatório com pelo menos 32 caracteres no .env.')
  if (!process.env.DB_USER || !process.env.DB_NAME) throw new Error('Preencha DB_USER e DB_NAME no .env.')
  await pool.query('SELECT 1 FROM usuarios LIMIT 1')
  return app.listen(Number(process.env.PORT || 3001), '127.0.0.1', () => console.log(`Loja: http://localhost:${process.env.PORT || 3001}`))
}

if (require.main === module) {
  iniciar().catch(async erro => {
    console.error('Não foi possível iniciar:', erro.code || erro.message)
    await pool.end()
    process.exitCode = 1
  })
}

module.exports = { app, iniciar }
