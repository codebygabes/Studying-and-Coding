#include <stdio.h>
#include <stdlib.h>

int main() {

    int P[] = {2, 4, 6, 7, 9, 2}; // Nao Precisa Indicar : Tamanho do Vetor
    char Frase[] = "Eles venceram, e o sinal esta fechado pra nos (...)";
    int X = P[2] + P[4] * P[1];
    printf("%d\n", X);
    printf("%s\n", Frase);

}