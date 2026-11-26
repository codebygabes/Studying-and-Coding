class ItemCarrinho {
  constructor(produto, quantidade) {
    this.produto = produto
    this.quantidade = quantidade
  }

  calcularSubtotal() {
    return Math.round(this.produto.preco * 100) * this.quantidade / 100
  }

  subtotalFormatado() {
    return this.calcularSubtotal().toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })
  }
}

class Carrinho {
  constructor() {
    this.itens = []
    this.carregarDoStorage()
  }

  adicionarItem(produto, quantidade) {
    if (!produto.estaDisponivel()) {
      return { sucesso: false, mensagem: 'Produto sem estoque.' }
    }

    let achou = null
    let i = 0
    while (i < this.itens.length) {
      if (this.itens[i].produto.id === produto.id) {
        achou = this.itens[i]
        break
      }
      i++
    }

    if (achou !== null) {
      const novaQtd = achou.quantidade + quantidade
      if (novaQtd > produto.estoque) {
        return { sucesso: false, mensagem: 'Estoque insuficiente. Disponível: ' + produto.estoque + ' unidade(s).' }
      }
      achou.quantidade = novaQtd
    } else {
      if (quantidade > produto.estoque) {
        return { sucesso: false, mensagem: 'Estoque insuficiente. Disponível: ' + produto.estoque + ' unidade(s).' }
      }
      this.itens.push(new ItemCarrinho(produto, quantidade))
    }

    this.salvar()
    return { sucesso: true, mensagem: 'Produto adicionado!' }
  }

  removerItem(id) {
    for (let i = 0; i < this.itens.length; i++) {
      if (this.itens[i].produto.id === id) {
        this.itens.splice(i, 1)
        this.salvar()
        return true
      }
    }
    return false
  }

  alterarQuantidade(id, novaQtd) {
    for (let i = 0; i < this.itens.length; i++) {
      if (this.itens[i].produto.id === id) {
        if (novaQtd <= 0) {
          this.itens.splice(i, 1)
          this.salvar()
          return { sucesso: true, removido: true }
        }
        if (novaQtd > this.itens[i].produto.estoque) {
          return { sucesso: false, mensagem: 'Estoque máximo: ' + this.itens[i].produto.estoque + ' unidade(s).' }
        }
        this.itens[i].quantidade = novaQtd
        this.salvar()
        return { sucesso: true, removido: false }
      }
    }
    return { sucesso: false, mensagem: 'Item não encontrado.' }
  }

  calcularSubtotal() {
    let total = 0
    for (let i = 0; i < this.itens.length; i++) {
      total += Math.round(this.itens[i].produto.preco * 100) * this.itens[i].quantidade
    }
    return total / 100
  }

  calcularTotalItens() {
    let qtd = 0
    let i = 0
    while (i < this.itens.length) {
      qtd = qtd + this.itens[i].quantidade
      i++
    }
    return qtd
  }

  calcularDesconto() {
    const sub = this.calcularSubtotal()
    if (sub >= 500) {
      return { valor: Math.round(Math.round(sub * 100) * 15 / 100) / 100, taxa: 0.15, mensagem: '15% de desconto para compras acima de R$ 500,00!' }
    } else if (sub >= 300) {
      return { valor: Math.round(Math.round(sub * 100) * 10 / 100) / 100, taxa: 0.10, mensagem: '10% de desconto para compras acima de R$ 300,00!' }
    }
    return { valor: 0, taxa: 0, mensagem: '' }
  }

  // Consulta a quantidade local sem alterar o estoque real do produto.
  quantidadeDoProduto(id) {
    const item = this.itens.find(item => item.produto.id === id)
    return item ? item.quantidade : 0
  }

  estaVazio() {
    return this.itens.length === 0
  }

  limpar() {
    this.itens = []
    localStorage.removeItem('nexvion_carrinho')
  }

  obterResumo() {
    const sub = this.calcularSubtotal()
    const desc = this.calcularDesconto()
    return {
      itens: this.itens,
      totalItens: this.calcularTotalItens(),
      subtotal: sub,
      desconto: desc,
      total: Math.round((sub - desc.valor) * 100) / 100
    }
  }

  formatarMoeda(valor) {
    return valor.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })
  }

  salvar() {
    const dados = this.itens.map(item => ({
      produto_id: item.produto.id,
      quantidade: item.quantidade
    }))
    localStorage.setItem('nexvion_carrinho', JSON.stringify(dados))
  }

  carregarDoStorage() {
    const raw = localStorage.getItem('nexvion_carrinho')
    if (!raw) return
    try {
      const dados = JSON.parse(raw)
      this._pendente = Array.isArray(dados) ? dados : []
    } catch (e) {
      this._pendente = []
    }
  }

  sincronizarComProdutos(listaProdutos) {
    // Relê preços/saldos da API; mantém apenas quantidades ainda disponíveis.
    const pending = this._pendente || this.itens.map(item => ({ produto_id: item.produto.id, quantidade: item.quantidade }))
    this._pendente = null
    this.itens = []
    for (const item of pending) {
      if (!item || !Number.isInteger(item.quantidade) || item.quantidade <= 0) continue
      const p = listaProdutos.find(pr => pr.id === item.produto_id)
      if (p && p.estaDisponivel() && !this.quantidadeDoProduto(p.id)) {
        const qtd = Math.min(item.quantidade, p.estoque)
        this.itens.push(new ItemCarrinho(p, qtd))
      }
    }
    this.salvar()
  }
}

const carrinho = new Carrinho()
