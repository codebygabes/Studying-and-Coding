algoritmo "ESCOLHACASO_1"
var
   doar: inteiro
   valor: inteiro
inicio
      escreval ("-------------------------")
      escreval ("    CRIAN�A ESPERAN�A    ")
      escreval ("-------------------------")
      escreval ("Muito Obrigado por ajudar")
      escrevaL (" [1] PARA DOAR 10$ ")
      escrevaL (" [2] PARA DOAR 25$ ")
      escrevaL (" [3] PARA DOAR 50$ ")
      escrevaL (" [4] PARA DOAR OUTROS VALORES ")
      escrevaL (" [5] PARA CANCELAR ")
      leia(doar)
      Escolha doar
         Caso 1
              valor <- 10
              escrevaL("-----------------------")
              escrevaL("SUA DOA��O FOI DE:", valor)
              escrevaL("MUITO OBRIGADO!")
              escrevaL("-----------------------")
         Caso 2
              valor <- 25
              escrevaL("-----------------------")
              escrevaL("SUA DOA��O FOI DE:", valor)
              escrevaL("MUITO OBRIGADO!")
              escrevaL("-----------------------")
         Caso 3
              valor <- 50
              escrevaL("-----------------------")
              escrevaL("SUA DOA��O FOI DE:", valor)
              escrevaL("MUITO OBRIGADO!")
              escrevaL("-----------------------")
         Caso 4
              escreva("QUAL VALOR DESEJA DOAR? R$")
              leia(valor)
              escrevaL("-----------------------")
              escrevaL("SUA DOA��O FOI DE:", valor)
              escrevaL("MUITO OBRIGADO!")
              escrevaL("-----------------------")
         Caso 5
              valor <- 0
              se valor = 0 entao
                 escrevaL("-----------------------")
                 escrevaL("N�O TEVE DOA��O!")
                 escrevaL("VOLTE SEMPRE.")
                 escrevaL("-----------------------")
              fimse
              
      FimEscolha
      
fimalgoritmo