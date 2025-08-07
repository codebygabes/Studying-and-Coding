// CALCULO DE PI

#include <stdio.h>
#include <stdlib.h>

int main () {

    system("cls");
    float soma = 0.0;
    float denominador = 1.0;
    float sinal = 1.0;

    int n = 1; // PARA CONTAR A QUANTIDADE DE TERMOS
    
    do
    {
        soma = soma + sinal * 1.0 / denominador;
        denominador = denominador + 2.0;
        sinal = -1.0 * sinal;
        n++;

    } while (n <= 200);

    float PI = 4.0 * soma;
    printf("VALOR DE PI: %.2f\n", PI);

    return 0;
    

    

}