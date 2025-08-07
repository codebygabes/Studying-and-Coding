#include <stdio.h>
#include <stdlib.h>

int main() {

    int n, n2;
    int i = 0;
    int soma = 0;

    printf("NUMERO PARA SEQUENCIA: ");
    scanf(" %d", &n);

    n2 = n;

    while (i < n2)
    {
        soma = soma + n;
        printf("%d\n", n);
        n = n - 1;
        i++;
    }

    printf("A SOMA DE 1 ATE %d E: %d\n", n2, soma);
    printf("F I M !");
    
    return 0;
}