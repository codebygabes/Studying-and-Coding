const pool = require('./db/connection')

// Erros esperados carregam o código HTTP para o tratamento central.
function exigir(condicao, mensagem, status = 400) {
  if (!condicao) throw Object.assign(new Error(mensagem), { status })
}

// Rejeita nulos, booleanos, frações e números fora do INT do MySQL.
function inteiro(valor, campo, minimo = 1) {
  exigir((typeof valor === 'number' || typeof valor === 'string') && /^\d+$/.test(String(valor)), `${campo} deve ser inteiro.`)
  const numero = Number(valor)
  exigir(Number.isSafeInteger(numero) && numero >= minimo && numero <= 2147483647, `${campo} fora do limite.`)
  return numero
}

// Texto obrigatório tem tamanho limitado antes de chegar ao banco.
function texto(valor, campo, limite) {
  exigir(typeof valor === 'string' && valor.trim().length > 0 && valor.trim().length <= limite, `${campo} inválido.`)
  return valor.trim()
}

// Dinheiro é calculado em centavos inteiros, sem somar frações binárias.
function centavos(valor) {
  exigir((typeof valor === 'string' || typeof valor === 'number') && /^\d{1,8}(\.\d{1,2})?$/.test(String(valor)), 'Preço inválido: use até duas casas decimais.')
  const [reais, fracao = ''] = String(valor).split('.')
  return Number(reais) * 100 + Number(fracao.padEnd(2, '0'))
}

// Commit confirma tudo; qualquer erro desfaz tudo; finally libera a conexão.
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

// O produto já deve estar bloqueado por FOR UPDATE nesta mesma conexão.
async function movimentar(conn, produto, tipo, quantidade, saldo, motivo, usuarioId) {
  inteiro(saldo, 'Saldo', 0)
  await conn.execute('UPDATE produtos SET estoque = ? WHERE id = ?', [saldo, produto.id])
  // O histórico guarda a quantidade movimentada e o saldo após a operação.
  await conn.execute(`INSERT INTO movimentacoes_estoque
    (produto_id, tipo, quantidade, saldo_resultante, motivo, usuario_id) VALUES (?, ?, ?, ?, ?, ?)`,
  [produto.id, tipo, quantidade, saldo, motivo, usuarioId])
}

module.exports = { exigir, inteiro, texto, centavos, transacao, movimentar }
