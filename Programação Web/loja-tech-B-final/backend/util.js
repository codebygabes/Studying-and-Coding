const pool = require('./db/connection')

function exigir(condicao, mensagem, status = 400) {
  if (!condicao) throw Object.assign(new Error(mensagem), { status })
}

function inteiro(valor, campo, minimo = 1) {
  exigir((typeof valor === 'number' || typeof valor === 'string') && /^\d+$/.test(String(valor)), `${campo} deve ser inteiro.`)
  const numero = Number(valor)
  exigir(Number.isSafeInteger(numero) && numero >= minimo && numero <= 2147483647, `${campo} fora do limite.`)
  return numero
}

function texto(valor, campo, limite) {
  exigir(typeof valor === 'string' && valor.trim().length > 0 && valor.trim().length <= limite, `${campo} inválido.`)
  return valor.trim()
}

function centavos(valor) {
  exigir((typeof valor === 'string' || typeof valor === 'number') && /^\d{1,8}(\.\d{1,2})?$/.test(String(valor)), 'Preço inválido: use até duas casas decimais.')
  const [reais, fracao = ''] = String(valor).split('.')
  return Number(reais) * 100 + Number(fracao.padEnd(2, '0'))
}

async function transacao(operacao) {
  const conn = await pool.getConnection()
  try {
    await conn.beginTransaction()
    const resultado = await operacao(conn)
    await conn.commit()
    return resultado
  } catch (erro) {
    await conn.rollback()
    throw erro
  } finally {
    conn.release()
  }
}

async function movimentar(conn, produto, tipo, quantidade, saldo, motivo, usuarioId) {
  inteiro(saldo, 'Saldo', 0)
  await conn.execute('UPDATE produtos SET estoque = ? WHERE id = ?', [saldo, produto.id])

  await conn.execute(`INSERT INTO movimentacoes_estoque
    (produto_id, tipo, quantidade, saldo_resultante, motivo, usuario_id) VALUES (?, ?, ?, ?, ?, ?)`,
  [produto.id, tipo, quantidade, saldo, motivo, usuarioId])
}

module.exports = { exigir, inteiro, texto, centavos, transacao, movimentar }
