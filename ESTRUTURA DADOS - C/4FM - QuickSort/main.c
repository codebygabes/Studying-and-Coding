#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#include <quicksort.h>

#define TAMANHO 500000
int main() {

    int Vetor[] = {3, 6, 4, 5, 1, 7, 2};
    int Tamanho = sizeof(Vetor) / sizeof(int);
    
    int PosicaoPivot = Particao(Vetor, 0, Tamanho - 1);
    printf("Posicao do Pivot: %d\n", PosicaoPivot);

    QuickSort(Vetor, 0, Tamanho -1);
    ExibirVetor(Vetor, Tamanho);

}