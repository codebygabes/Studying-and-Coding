#include <stdio.h>
#include <stdlib.h>

#define ERRO -1

int fatorial(int N) {

    if(N < 0) return ERRO;
   
    int temp = 1;
    int i;
    for (i = 1; i <= N; i++)
    {
        temp = temp * i;
    }
    
    return temp;

}

int CoeficienteBinominal(int N, int P) {

    int numerador = fatorial(N);
    int denominador = fatorial(P) * fatorial(N - P);
    return numerador/denominador;

}

int main() {

    printf("%d\n", CoeficienteBinominal(6, 4));
    return 0;

}