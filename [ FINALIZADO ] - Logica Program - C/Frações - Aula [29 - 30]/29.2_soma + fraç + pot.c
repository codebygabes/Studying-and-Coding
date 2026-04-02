#include <stdio.h>
#include <stdlib.h>

int main () {

    system("cls");

    int n;
    int i;
    float pot_2 = 2.0;
    float pot_3 = 1.0;
    float soma = 0.0;

    printf("digite inteiro: ");
    scanf(" %d", &n);

    for ( i = 0; i < n; i++)
    {
        soma = soma + (pot_2) / (pot_3 + 1.0);
        pot_2 = pot_2 * 2;
        pot_3 = pot_3 * 3;
    }

    printf("soma: %.6f", soma);
    

   return 0;
    
}