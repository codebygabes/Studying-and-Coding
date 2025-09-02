#include <stdio.h>
#include <stdlib.h>

int main() {

    int n = 1;
    int soma = 0;

    while (n <= 2048)
    {
        soma = soma + n;
        n = n * 2;
    }

    printf("%d\n", soma);
   
}