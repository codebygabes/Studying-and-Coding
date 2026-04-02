#include <stdio.h>
#include <stdlib.h>

int main() {
    system("cls");
    

    float N = 1.0;
    float D1 = 1.0, D2 = 4.0;
    float Soma;
    
    Soma = 0.0; // Versão 1

    do {
        Soma = Soma + 1.0 / (N * (N + 3));
        N = N + 3;
    } while (N <= 2998);

    printf("O valor da soma (versão 01) é %f.\n", Soma);

    Soma = 0.0; // Versão 2

    do {
        Soma = Soma + 1.0 / (D1  * D2);
        D1 = D1 + 3;
        D2 = D2 + 3;
    } while (D1 <= 2998 && D2 <= 3001);

    printf("O valor da soma (versão 02) é %f.\n", Soma);

    Soma = (1.0/3.0) * (3001.0 - 1.0) / (3001.0);

    printf("O valor da soma (versão 03) é %f.\n", Soma);

    return 0;    

}