// pegando os elementos do html pelo id
const btnAdicionar = document.getElementById("btn-adicionar");
const btnDiminuir = document.getElementById("btn-diminuir");
const contadorTela = document.getElementById("contador");

const campoTexto = document.getElementById("campo-texto");
const qtdCaracteres = document.getElementById("quantidade-caracteres");
const areaTextos = document.getElementById("area-textos");

const tipoLista = document.getElementById("tipo-lista");
const btnLista = document.getElementById("btn-lista");
const areaListas = document.getElementById("area-listas");

// guarda a lista que foi criada
let listaAtual = null;

const btnReset = document.getElementById("btn-reset");

// essa variavel guarda o numero do contador
let contador = 0;

// aumenta o contador quando clicar
btnAdicionar.addEventListener("click", function () {
    contador = contador + 1;

    // atualiza o numero que aparece na tela
    contadorTela.textContent = contador;
});

// diminui o contador sem deixar ele negativo
btnDiminuir.addEventListener("click", function () {
    if (contador > 0) {
        contador = contador - 1;

        contadorTela.textContent = contador;
    } else {
        // mostra o aviso se ja estiver em zero
        alert("O contador já está em zero.");
    }
});

// conta os caracteres enquanto a pessoa digita
campoTexto.addEventListener("input", function () {
    // tirando os espacos antes de contar
    const textoSemEspacos =
        campoTexto.value.replace(/\s/g, "");

    qtdCaracteres.textContent = textoSemEspacos.length;
});

// adiciona o texto quando apertar Enter
campoTexto.addEventListener("keydown", function (evento) {
    if (evento.key === "Enter") {
        // trim tira espacos do comeco e do final
        const textoDigitado = campoTexto.value.trim();

        // evita criar paragrafo vazio
        if (textoDigitado !== "") {
            // criando um paragrafo pelo DOM
            const novoParagrafo =
                document.createElement("p");

            novoParagrafo.textContent = textoDigitado;

            // colocando o paragrafo dentro da area de textos
            areaTextos.appendChild(novoParagrafo);

            // limpando o campo depois de adicionar
            campoTexto.value = "";

            // zerando os caracteres
            qtdCaracteres.textContent = "0";
        }
    }
});

// adiciona itens na lista escolhida
btnLista.addEventListener("click", function () {
    const tipoEscolhido = tipoLista.value;

    // cria uma nova lista sempre que ainda nao existe
    // OU quando o tipo escolhido mudou em relacao a lista atual
    if (listaAtual === null || listaAtual.tagName.toLowerCase() !== tipoEscolhido) {
        listaAtual = document.createElement(tipoEscolhido);
        areaListas.appendChild(listaAtual);
    }

    // cria um novo item
    const novoItem = document.createElement("li");
    novoItem.textContent = "Novo item";

    // coloca o item dentro da lista atual
    listaAtual.appendChild(novoItem);
});

// limpa todas as interacoes
btnReset.addEventListener("click", function () {
    // zerando o contador
    contador = 0;
    contadorTela.textContent = "0";

    // limpando o campo de texto
    campoTexto.value = "";

    // zerando a quantidade de caracteres
    qtdCaracteres.textContent = "0";

    // removendo os paragrafos adicionados
    areaTextos.innerHTML = "";

    // removendo as listas adicionadas
    areaListas.innerHTML = "";

    // reseta a referência da lista, senão ela fica "fantasma"
    listaAtual = null;
});