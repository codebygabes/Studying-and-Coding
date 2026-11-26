const API_URL = '/api'

class Produto {
  constructor(id, codigo, nome, categoria, preco, estoque, imagem) {
    this.id = id
    this.codigo = codigo
    this.nome = nome
    this.categoria = categoria
    this.preco = preco
    this.estoque = estoque
    this.imagem = imagem
  }

  estaDisponivel() {
    return this.estoque > 0
  }

  estoqueEstasBaixo() {
    return this.estoque > 0 && this.estoque <= 3
  }

  precoFormatado() {
    return this.preco.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })
  }

}

let produtos = []

async function carregarProdutos() {
  const resp = await fetch(API_URL + '/produtos')
  if (!resp.ok) throw new Error('Não foi possível consultar os produtos. Tente novamente.')
  const dados = await resp.json()
  produtos = []
  for (const d of dados) {
    produtos.push(new Produto(d.id, d.codigo, d.nome, d.categoria, Number(d.preco), d.estoque, d.imagem_url))
  }
  return produtos
}
