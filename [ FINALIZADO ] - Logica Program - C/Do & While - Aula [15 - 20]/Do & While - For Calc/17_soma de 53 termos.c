#include <stdio.h>
#include <stdlib.h>

int main() {

  // valor da soma dos 53 termos de uma progressao do primeiro termo 2 e razao 3
    int n = 1;
    int termo = 2;
    int soma = 0;
   
   printf("N\tTermo\tSoma\n");
   while (n <= 53)
   {
    printf("%d\t%d\t%d\n", n, termo, soma);
    soma = soma + termo;
    termo = termo + 3;
    n++;
   }

   return 0;
   
}