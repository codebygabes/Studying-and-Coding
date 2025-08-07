#include <stdio.h>
#include <stdlib.h>

int main() {

int n = 3;
    int soma = 0;

    while (n <= 81)
    {
        soma = soma + n;
        n = n + 2;
    }

    printf("%d\n", soma);

}