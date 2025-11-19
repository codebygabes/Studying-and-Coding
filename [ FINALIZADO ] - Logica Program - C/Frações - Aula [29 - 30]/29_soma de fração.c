#include <stdio.h>
#include <stdlib.h>

int main () {

    system("cls");

    float den;
    float soma = 0.0;

    for (den = 2.0; den <= 2048; den = den * 2)
    {
        soma = soma + 1.0 / den;
    }

    printf("Soma: %.6f\n", soma);

    return 0;
    
}