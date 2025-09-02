#include <stdio.h>
#include <stdlib.h>

int main () {

    system("cls");

    float d1 =1.0, d2 = 4.0;
    float n = 1.0;
    float soma = 0.0;

    do
    {
        soma = soma + n / (d1 * d2);
        d1 = d1 + 3;
        d2 = d2 + 3;
    } while (d1 <= 2998 && d2 <= 3001);

    printf("O VALOR DA SOMA: %.2f", soma);

    return 0;
    
}