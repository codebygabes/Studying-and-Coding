#include <stdio.h>
#include <stdlib.h>

int main() {
    
    int numero;

    printf("Diga um numero: ");
    scanf("%d", &numero);

    if (numero < 0)
    {
        numero = -numero; // transformar numero negativo em positivo
    }
    
    switch (numero % 2)
    {
    case 0:
        printf("O numero e PAR!");
        break;
    case 1:
        printf("O numero e IMPAR!");
        break;
    }
}