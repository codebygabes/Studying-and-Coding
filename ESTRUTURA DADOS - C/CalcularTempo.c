#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#define TAMANHO 1000

int main() {

    int V[TAMANHO];

    srand(time(NULL));

    for(int i = 0; i < TAMANHO; i++) V[i] = rand();
    clock_t T0 = clock();   // Marcar = Inicio Contagem .

    for(int i = 0; i < TAMANHO; i++) printf("%d\t", V[i]);
    clock_t TF = clock();   // Marcar = Final Contagem .

    double TempoCPU = ((double)TF - T0) / CLOCKS_PER_SEC;
    printf("\nTEMPO DE CPU = %lf SEG\n", TempoCPU);

    return 0;

}