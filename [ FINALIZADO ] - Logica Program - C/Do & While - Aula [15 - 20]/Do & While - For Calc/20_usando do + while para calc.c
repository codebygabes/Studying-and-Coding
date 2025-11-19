#include <stdio.h>
#include <stdlib.h>

int main () {

    system("cls");
    
    float soma = 0.0;
    float n = 1.0;
    
    do
    {
        soma = soma + 1.0 / (n * (n + 3));
        n = n + 3;
        
    } while (n <= 2998);

    printf("VALOR DA SOMA: %.2f", soma);
    
    
    return 0;

}