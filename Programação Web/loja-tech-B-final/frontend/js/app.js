const grid = document.getElementById('produtosGrid')
const badge = document.getElementById('carrinhoBadge')
const btnAbrirCart = document.getElementById('btnAbrirCarrinho')
const btnFecharCart = document.getElementById('btnFecharCarrinho')
const painelCart = document.getElementById('painelCarrinho')
const overlay = document.getElementById('overlay')
const cartItens = document.getElementById('carrinhoItens')
const cartVazio = document.getElementById('carrinhoVazio')
const spanTotalItens = document.getElementById('totalItens')
const spanSubtotal = document.getElementById('subtotal')
const spanDesconto = document.getElementById('desconto')
const spanFinal = document.getElementById('valorFinal')
const spanDescInfo = document.getElementById('descontoInfo')
const btnFinalizar = document.getElementById('btnFinalizar')
const campoBusca = document.getElementById('campoBusca')
const divFiltros = document.getElementById('filtros')
const msgSemResultado = document.getElementById('msgSemResultado')
const modalOverlay = document.getElementById('modalOverlay')
const modalBody = document.getElementById('modalBody')
const modalTitulo = document.getElementById('modalTitulo')
const btnFecharModal = document.getElementById('btnFecharModal')
const btnCancelar = document.getElementById('btnCancelarModal')
const btnConfirmar = document.getElementById('btnConfirmarCompra')
const sucessoOverlay = document.getElementById('modalSucessoOverlay')
const msgSucesso = document.getElementById('msgSucesso')
const btnFecharSucesso = document.getElementById('btnFecharSucesso')
const navAuth = document.getElementById('navAuth')

let categoriaAtiva = 'Todos'
let termoBusca = ''

function atualizarNav() {
  if (!navAuth) return
  if (estaLogado()) {
    const u = getUsuario()
    let html = '<span class="nav-usuario">👤 ' + escaparHTML(u.nome.split(' ')[0]) + '</span>'
    html += '<a href="pedidos.html" class="nav-link">Meus Pedidos</a>'
    if (eAdmin()) {
      html += '<a href="admin.html" class="nav-link nav-admin">Painel Admin</a>'
    }
    html += '<button class="btn-logout" id="btnLogout">Sair</button>'
    navAuth.innerHTML = html
    document.getElementById('btnLogout').addEventListener('click', sair)
  } else {
    navAuth.innerHTML =
      '<a href="login.html" class="nav-link">Entrar</a>' +
      '<a href="cadastro.html" class="btn-cadastro-nav">Cadastrar</a>'
  }
}

const mostrarProdutos = (lista) => {
  grid.innerHTML = ''

  if (lista.length === 0) {
    msgSemResultado.classList.remove('escondido')
    return
  }

  msgSemResultado.classList.add('escondido')

  for (let i = 0; i < lista.length; i++) {
    const p = lista[i]
    const noCarrinho = carrinho.quantidadeDoProduto(p.id)
    const saldo = Math.max(0, p.estoque - noCarrinho)
    const disponivel = saldo > 0
    const baixo = p.estoqueEstasBaixo()
    const classeCard = p.estaDisponivel() ? 'card' : 'card indisponivel'
    const textoDisp = !p.estaDisponivel() ? '✖ Indisponível' : '✔ Disponíveis para adicionar: ' + saldo + ' un.'
    const classeDisp = disponivel ? 'card-disponibilidade disponivel' : 'card-disponibilidade indisponivel'
    const textoBotao = disponivel ? 'Adicionar ao Carrinho' : (noCarrinho ? 'Todas no carrinho' : 'Sem Estoque')
    const badgeBaixo = baixo ? '<span class="badge-estoque-baixo">⚠ Últimas unidades</span>' : ''

    const card = document.createElement('div')
    card.className = classeCard

    card.innerHTML =
      '<img class="card-img" src="' + escaparHTML(p.imagem) + '" alt="' + escaparHTML(p.nome) + '" loading="lazy">' +
      '<div class="card-corpo">' +
        badgeBaixo +
        '<span class="card-categoria">' + escaparHTML(p.categoria) + '</span>' +
        '<h3 class="card-nome">' + escaparHTML(p.nome) + '</h3>' +
        '<p class="card-preco">' + p.precoFormatado() + '</p>' +
        '<p class="' + classeDisp + '">' + textoDisp + '</p>' +
        (noCarrinho ? '<p class="card-reserva">' + noCarrinho + ' no carrinho · ' + p.estoque + ' em estoque</p>' : '') +
        '<button class="btn-adicionar" ' + (disponivel ? '' : 'disabled') + '>' + textoBotao + '</button>' +
      '</div>'

    const btn = card.querySelector('.btn-adicionar')
    if (disponivel) {
      btn.addEventListener('click', function() {
        adicionarProduto(p.id)
      })
    }

    grid.appendChild(card)
  }
}

function filtrar() {
  const lista = []
  for (const p of produtos) {
    const categoriaCorreta = categoriaAtiva === 'Todos' || p.categoria === categoriaAtiva
    if (categoriaCorreta && p.nome.toLowerCase().includes(termoBusca.toLowerCase())) lista.push(p)
  }

  mostrarProdutos(lista)
}

function adicionarProduto(id) {
  let prod = null
  let i = 0
  while (i < produtos.length) {
    if (produtos[i].id === id) {
      prod = produtos[i]
      break
    }
    i++
  }

  if (prod === null) return

  const res = carrinho.adicionarItem(prod, 1)

  if (res.sucesso) {
    atualizarCarrinho()
    filtrar()
    badge.style.transform = 'scale(1.6)'
    setTimeout(function() { badge.style.transform = 'scale(1)' }, 200)
    mostrarToast('Produto adicionado ao carrinho!', 'sucesso')
  } else {
    mostrarToast(res.mensagem, 'erro')
  }
}

function atualizarCarrinho() {
  const resumo = carrinho.obterResumo()

  badge.textContent = resumo.totalItens
  spanTotalItens.textContent = resumo.totalItens
  spanSubtotal.textContent = carrinho.formatarMoeda(resumo.subtotal)
  spanDesconto.textContent = carrinho.formatarMoeda(resumo.desconto.valor)
  spanFinal.textContent = carrinho.formatarMoeda(resumo.total)
  spanDescInfo.textContent = resumo.desconto.mensagem

  cartItens.innerHTML = ''

  if (carrinho.estaVazio()) {
    cartVazio.classList.remove('escondido')
    cartItens.appendChild(cartVazio)
    return
  }

  cartVazio.classList.add('escondido')

  for (let i = 0; i < resumo.itens.length; i++) {
    const item = resumo.itens[i]
    const el = montarItemCarrinho(item)
    cartItens.appendChild(el)
  }
}

const montarItemCarrinho = (item) => {
  const div = document.createElement('div')
  div.className = 'item-carrinho'

  div.innerHTML =
    '<p class="item-nome">' + escaparHTML(item.produto.nome) + '</p>' +
    '<p class="item-preco-unit">Preço unitário: ' + item.produto.precoFormatado() + '</p>' +
    '<div class="item-controles">' +
      '<div class="item-quantidade">' +
        '<button class="btn-qty btn-menos">−</button>' +
        '<span class="qty-valor">' + item.quantidade + '</span>' +
        '<button class="btn-qty btn-mais">+</button>' +
      '</div>' +
      '<span class="item-subtotal">' + item.subtotalFormatado() + '</span>' +
    '</div>' +
    '<button class="btn-remover">🗑 Remover</button>'

  div.querySelector('.btn-menos').addEventListener('click', function() {
    const res = carrinho.alterarQuantidade(item.produto.id, item.quantidade - 1)
    if (res.sucesso) {
      atualizarCarrinho()
      filtrar()
    }
  })

  div.querySelector('.btn-mais').addEventListener('click', function() {
    const res = carrinho.alterarQuantidade(item.produto.id, item.quantidade + 1)
    if (!res.sucesso) {
      mostrarToast(res.mensagem, 'erro')
    } else {
      atualizarCarrinho()
      filtrar()
    }
  })

  div.querySelector('.btn-remover').addEventListener('click', function() {
    carrinho.removerItem(item.produto.id)
    atualizarCarrinho()
    filtrar()
  })

  return div
}

function abrirCarrinho() {
  painelCart.classList.remove('escondido')
  overlay.classList.remove('escondido')
  document.body.style.overflow = 'hidden'
}

function fecharCarrinho() {
  painelCart.classList.add('escondido')
  overlay.classList.add('escondido')
  document.body.style.overflow = ''
}

function abrirResumo() {
  if (carrinho.estaVazio()) {
    mostrarToast('Carrinho vazio! Adicione algum produto antes de finalizar.', 'erro')
    return
  }

  if (!estaLogado()) {
    modalTitulo.textContent = 'Login necessário'
    modalBody.innerHTML =
      '<div class="modal-login-aviso">' +
        '<p>Para finalizar a compra você precisa estar logado.</p>' +
        '<p>Seus produtos no carrinho serão mantidos após o login.</p>' +
        '<a href="login.html" class="btn-ir-login">Fazer Login</a>' +
        '<a href="cadastro.html" class="btn-ir-cadastro">Criar conta</a>' +
      '</div>'
    document.getElementById('modalFooter').classList.add('escondido')
    modalOverlay.classList.remove('escondido')
    document.body.style.overflow = 'hidden'
    return
  }

  document.getElementById('modalFooter').classList.remove('escondido')
  const resumo = carrinho.obterResumo()
  modalBody.innerHTML = ''
  modalTitulo.textContent = 'Resumo do Pedido'

  for (let i = 0; i < resumo.itens.length; i++) {
    const item = resumo.itens[i]
    const linha = document.createElement('div')
    linha.className = 'modal-item'
    linha.innerHTML =
      '<span class="modal-item-nome">' + escaparHTML(item.produto.nome) + '</span>' +
      '<span class="modal-item-qty">x' + item.quantidade + '</span>' +
      '<span class="modal-item-sub">' + item.subtotalFormatado() + '</span>'
    modalBody.appendChild(linha)
  }

  const divRes = document.createElement('div')
  divRes.className = 'modal-resumo'
  divRes.innerHTML =
    '<div class="modal-resumo-linha"><span>Total de itens:</span><span>' + resumo.totalItens + '</span></div>' +
    '<div class="modal-resumo-linha"><span>Subtotal:</span><span>' + carrinho.formatarMoeda(resumo.subtotal) + '</span></div>' +
    '<div class="modal-resumo-linha desconto"><span>Desconto:</span><span>− ' + carrinho.formatarMoeda(resumo.desconto.valor) + '</span></div>' +
    '<div class="modal-resumo-linha total"><span>Valor final:</span><span>' + carrinho.formatarMoeda(resumo.total) + '</span></div>'
  modalBody.appendChild(divRes)

  modalOverlay.classList.remove('escondido')
  document.body.style.overflow = 'hidden'
}

function fecharModal() {
  modalOverlay.classList.add('escondido')
  document.getElementById('modalFooter').classList.remove('escondido')
  document.body.style.overflow = ''
}

async function confirmarCompra() {
  if (!estaLogado()) {
    mostrarToast('Você precisa estar logado para finalizar a compra.', 'erro')
    return
  }

  const resumo = carrinho.obterResumo()
  const itensPedido = resumo.itens.map(it => ({
    produto_id: it.produto.id,
    quantidade: it.quantidade
  }))

  btnConfirmar.disabled = true
  btnConfirmar.textContent = 'Processando...'

  try {
    const resp = await fetchComAuth(API_URL + '/pedidos', {
      method: 'POST',
      body: JSON.stringify({ itens: itensPedido })
    })

    const data = await resp.json()

    if (!resp.ok) {
      mostrarToast(data.erro || 'Erro ao finalizar pedido.', 'erro')

      if (resp.status === 409) {
        await carregarProdutos()
        carrinho.sincronizarComProdutos(produtos)
        atualizarCarrinho()
        filtrar()
        fecharModal()
      }
      return
    }

    fecharModal()
    fecharCarrinho()
    carrinho.limpar()
    atualizarCarrinho()


    try {
      await carregarProdutos()
      filtrar()
    } catch {
      mostrarToast('Compra confirmada. Recarregue a página para atualizar o estoque.', 'erro')
    }

    const totalFmt = Number(data.total).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })
    msgSucesso.textContent = 'Pedido #' + data.pedido_id + ' confirmado! Valor total: ' + totalFmt + '. Obrigado por comprar na Nexvion Tech!'
    sucessoOverlay.classList.remove('escondido')
    document.body.style.overflow = 'hidden'
  } catch (err) {
    mostrarToast('Falha na comunicação com o servidor.', 'erro')
  } finally {
    btnConfirmar.disabled = false
    btnConfirmar.textContent = 'Confirmar Compra'
  }
}

function fecharSucesso() {
  sucessoOverlay.classList.add('escondido')
  document.body.style.overflow = ''
}

function mostrarToast(msg, tipo) {
  const aviso = document.createElement('div')
  aviso.textContent = msg
  const cor = tipo === 'erro' ? '#ff4d6d' : '#00b4d8'
  aviso.style.cssText = 'position:fixed;bottom:24px;left:50%;transform:translateX(-50%);background:' + cor + ';color:#fff;padding:12px 24px;border-radius:12px;font-weight:600;font-size:0.9rem;z-index:9999;max-width:calc(100vw - 32px);width:max-content;text-align:center;transition:opacity 0.5s;'
  document.body.appendChild(aviso)
  setTimeout(function() { aviso.style.opacity = '0' }, 2200)
  setTimeout(function() { if (aviso.parentNode) document.body.removeChild(aviso) }, 2700)
}

btnAbrirCart.addEventListener('click', abrirCarrinho)
btnFecharCart.addEventListener('click', fecharCarrinho)
overlay.addEventListener('click', fecharCarrinho)
btnFinalizar.addEventListener('click', abrirResumo)
btnFecharModal.addEventListener('click', fecharModal)
btnCancelar.addEventListener('click', fecharModal)
btnConfirmar.addEventListener('click', confirmarCompra)
btnFecharSucesso.addEventListener('click', fecharSucesso)

campoBusca.addEventListener('input', function() {
  termoBusca = this.value
  filtrar()
})

divFiltros.addEventListener('click', function(e) {
  const btn = e.target.closest('.btn-filtro')
  if (!btn) return
  const botoes = divFiltros.querySelectorAll('.btn-filtro')
  for (let i = 0; i < botoes.length; i++) {
    botoes[i].classList.remove('ativo')
  }
  btn.classList.add('ativo')
  categoriaAtiva = btn.getAttribute('data-categoria')
  filtrar()
})

document.addEventListener('keydown', function(e) {
  if (e.key === 'Escape') {
    if (!sucessoOverlay.classList.contains('escondido')) {
      fecharSucesso()
    } else if (!modalOverlay.classList.contains('escondido')) {
      fecharModal()
    } else if (!painelCart.classList.contains('escondido')) {
      fecharCarrinho()
    }
  }
})

atualizarNav()
carregarProdutos().then(function(lista) {
  carrinho.sincronizarComProdutos(lista)
  atualizarCarrinho()
  filtrar()
}).catch(function() {
  grid.textContent = 'Não foi possível carregar os produtos. Confira a conexão e recarregue a página.'
})
