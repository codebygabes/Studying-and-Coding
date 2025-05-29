#include <stdio.h>
#include <stdlib.h>

int main() {

    int n = 1;
    int soma = 0;

    while (n <= 100)
    {
        soma = soma + n;
        n = n + 1;
    }

    printf("%d\n", soma);
    

    return 0;
}