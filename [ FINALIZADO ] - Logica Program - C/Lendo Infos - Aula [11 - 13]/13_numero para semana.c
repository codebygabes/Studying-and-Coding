#include <stdio.h>
#include <stdlib.h>

int main() {

    int numero;
    
    printf("Digite um numero de 1 a 7: ");
    scanf("%d", &numero);
    if (numero > 7)
    {
        printf("APENAS NUMERO DENTRE 1 A 7!");
    }
    
    switch (numero)
    {
    case 1:
        printf("O dia correspondente e SEGUNDA!");
        break;
    case 2:
        printf("O dia correspondente e TERCA!");
        break;
    case 3:
        printf("O dia correspondente e QUARTA!");
        break;
    case 4:
        printf("O dia correspondente e QUINTA!");
        break;
    case 5:
        printf("O dia correspondente e SEXTA!");
        break;
    case 6:
        printf("O dia correspondente e SABADO!");
        break;
    case 7:
        printf("O dia correspondente e DOMINGO!");
        break;
    }

    return 0;
}