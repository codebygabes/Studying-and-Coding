const jwt = require('jsonwebtoken')
const pool = require('../db/connection')
const { exigir } = require('../util')

// Valida assinatura, validade, sessão e perfil atual no banco.
async function autenticar(req, res, next) {
  const token = /^Bearer (\S+)$/.exec(req.headers.authorization || '')?.[1]
  exigir(token, 'Faça login para continuar.', 401)
  let dados
  try {
    dados = jwt.verify(token, process.env.JWT_SECRET, { algorithms: ['HS256'] })
  } catch {
    exigir(false, 'Token inválido ou expirado.', 401)
  }
  exigir(typeof dados.jti === 'string' && typeof dados.sub === 'string', 'Token inválido.', 401)
  // Logout exclui esta sessão; desativar o usuário também bloqueia o acesso.
  const [rows] = await pool.execute(`SELECT u.id, u.nome, u.email, u.perfil, u.criado_em
    FROM sessoes s JOIN usuarios u ON u.id = s.usuario_id
    WHERE s.id = ? AND u.id = ? AND s.expira_em > NOW() AND u.ativo = 1`, [dados.jti, dados.sub])
  exigir(rows.length, 'Sessão encerrada. Entre novamente.', 401)
  req.usuario = rows[0]
  req.sessaoId = dados.jti
  next()
}

// Esconder botões no navegador não substitui esta verificação.
function apenasAdmin(req, res, next) {
  exigir(req.usuario.perfil === 'admin', 'Acesso restrito a administradores.', 403)
  next()
}

module.exports = { autenticar, apenasAdmin }
