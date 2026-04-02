#include <stdio.h>
#include <stdlib.h>

int main () {

    system("cls");

    float den;
    float soma = 0.0;
    float sinal = + 1.0;

    for (den = 2.0; den <= 1000; den = den + 2)
    {
        soma = soma + sinal / den;
        sinal = sinal * ( -1.0);
    }

    printf("Soma: %.6f\n", soma);

    return 0;
    
}