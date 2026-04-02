#include <stdio.h>
#include <stdlib.h>

int main() {

    int n1, n2;
    int primeiro = 1;

    printf("NUMERO INICIAL: ");
    scanf(" %d", &n1);

    printf("NUMERO FINAL: ");
    scanf(" %d", &n2);

    printf("NUMEROS IMPARES ENTRE %d E %d:\n", n1, n2);

    for (int i = n1; i <= n2; i++)
    {
        if (i % 2 != 0) {
            if (!primeiro) { // Se não for o primeiro número, imprime a vírgula antes
                printf(", ");
            }
            printf("%d", i);
            primeiro = 0; // Depois do primeiro número, ativa a flag
        }
        
    }

    printf("\nNUMEROS PARES ENTRE %d E %d:\n", n1, n2);

    primeiro = 0;
    for (int i = n1; i <= n2; i++)
    {
        if (i % 2 != 1) {
            if (primeiro) { // Inverso para numero impar
                printf(", ");
            }
            printf("%d", i);
            primeiro = 1; // Depois do primeiro número, ativa a flag
        }
        
    }

    printf("\n"); // Nova linha no final
    return 0;

}