algoritmo "BRINCANDO"
var
   a, b, c, rep: inteiro
   resposta: caractere
inicio
      escreval ("Escolha 3 n�meros!")
      leia (a)
      leia (b)
      leia (c)
      escreval (" ")
      escreval ("A maior que B? ",(a>b))
      escreval ("A igual a B? ",(a=b))
      escreval ("A menor que B? ",(a<b))
      escreval ("A diferente de B? ",(a<>b))
      escreval ("C = a soma de A com B? ",(c=a+b))
      escreval ("C = ou maior que B^A? ",(c>=b^a))
      escreval (" ")
      escreval (" ")
      escreval ("Agora, outra dinamica!")
      escreva ("A � = B OU C � > A? (S/N): ")
      leia(resposta)

          se (resposta = "S") ou (resposta = "s") entao
             escreval ("Par�bens! Acertou!")
             escreva ("Qual Correto? Digite 1: 'A = B' | 2: 'C > B':")
          senao
             escreva("Que Pena, voc� Errou!")
          fimse
      escreva (" ")

      leia (rep)

          se (rep = 1) e (a = b) ou (rep = 2) e (c > b) entao
             escreval ("Voc� � Ferra, Acertou!")
          senao
             escreval ("Que pena, voc� Errou!")
          fimse

fimalgoritmo