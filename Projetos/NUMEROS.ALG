algoritmo "numeros"
var
   nome: caractere
   n1, n2, s, sm: inteiro
   sd, m, a, pp, ppp: real
inicio
// Intera��es.
    escreval ("Como voc� se chama? ")
    escreva ("--- Nome Aqui: ")
    leia (nome)
    escreval (" ")
    escreval ("Seja bem-vindo, ", nome)
    escreval (" ")
    escreval ("---Fim da Etapa 1---")
    escreval (" ")
// Intera��o + Soma.
     escreval ("Opa, me diga um n�mero.")
     escreva ("--- N�mero Aqui: ")
     leia (n1)
     escreval ("Boa, agora me fala outro.")
     escreva ("--- N�mero Aqui: ")
     leia (n2)
     s <- n1+n2
     escreval (" ")
     escreval ("E a soma deles �... ", s,"!")
     escreval (" ")
     escreval ("Mas nao acabamos por aqui.")
     escreval ("Pois irei mostrar mais resultados!")
     sm <- n1 * n2
     sd <- n1 / n2
     m <- (n1 +n2)/2
     escreval (" ")
     escreval ("A muliplica��o deles � ", sm,"!")
     escreval (" ")
     escreval ("A sua divis�o � igual a ", sd,"!")
     escreval (" ")
     escreval ("A sua media � igual a ", m,"!")
     escreval (" ")
     a <- int(raizq(s))
     escreval ("Sua RaizQ � igual a ",a,"!")
// Intera��o com Respostas.
     escreval (" ")
     pp <- pi
     escreval ("Agora vamos testar seus conhecimentos!")
     escreva ("Qual � o valor de PI? ")
     leia (ppp)
     
     se ppp = 3.14 entao
         escreva ("Correto! ",pp:2:2, " � o valor de PI.")
     senao
         escreva ("Errado! ",ppp, "n�o � o valor de PI.")
     fimse
     
     
fimalgoritmo