algoritmo "impaparprocedimento"
var
   N : Inteiro
procedimento parouimpar (a : inteiro)
inicio
      se (a % 2 = 0) entao
         escreva("Seu n�mero � PAR!")
      senao
         escreva("Seu n�mero � IMPAR!")
      fimse
fimprocedimento
inicio
      escreva("Digite um numero: ")
      leia(N)
      parouimpar(N)
      escreval(a)
      escreval(n)
fimalgoritmo