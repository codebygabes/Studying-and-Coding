const router = require('express').Router()
const bcrypt = require('bcryptjs')
const jwt = require('jsonwebtoken')
const { randomUUID } = require('node:crypto')
const pool = require('../db/connection')
const { exigir, texto } = require('../util')
const { autenticar } = require('../middleware/auth')

function emailValido(valor) {
  const email = texto(valor, 'E-mail', 150).toLowerCase()
  exigir(/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email), 'E-mail inválido.')
  return email
}

router.post('/cadastro', async (req, res) => {
  const nome = texto(req.body.nome, 'Nome', 150)
  const email = emailValido(req.body.email)
  const { senha, confirmar_senha } = req.body
  exigir(typeof senha === 'string' && senha.length >= 6 && Buffer.byteLength(senha) <= 72, 'Senha deve ter ao menos 6 caracteres e até 72 bytes.')
  exigir(senha === confirmar_senha, 'As senhas não coincidem.')
  const hash = await bcrypt.hash(senha, 10)

  const [result] = await pool.execute("INSERT INTO usuarios (nome, email, senha_hash, perfil) VALUES (?, ?, ?, 'cliente')", [nome, email, hash])
  res.status(201).json({ mensagem: 'Cadastro realizado!', id: result.insertId })
})

router.post('/login', async (req, res) => {
  const email = emailValido(req.body.email)
  const { senha } = req.body
  exigir(typeof senha === 'string' && senha.length > 0 && Buffer.byteLength(senha) <= 72, 'Senha inválida.')
  const [[usuario]] = await pool.execute('SELECT * FROM usuarios WHERE email = ? AND ativo = 1', [email])
  exigir(usuario && await bcrypt.compare(senha, usuario.senha_hash), 'Credenciais inválidas.', 401)
  const id = randomUUID()
  const token = jwt.sign({}, process.env.JWT_SECRET, { subject: String(usuario.id), jwtid: id, expiresIn: '8h', algorithm: 'HS256' })
  await pool.execute('DELETE FROM sessoes WHERE expira_em <= NOW()')
  await pool.execute('INSERT INTO sessoes (id, usuario_id, expira_em) VALUES (?, ?, DATE_ADD(NOW(), INTERVAL 8 HOUR))', [id, usuario.id])
  const { nome, perfil } = usuario
  res.json({ token, usuario: { id: usuario.id, nome, email, perfil } })
})

router.post('/logout', autenticar, async (req, res) => {
  await pool.execute('DELETE FROM sessoes WHERE id = ?', [req.sessaoId])
  res.json({ mensagem: 'Sessão encerrada.' })
})

module.exports = router
