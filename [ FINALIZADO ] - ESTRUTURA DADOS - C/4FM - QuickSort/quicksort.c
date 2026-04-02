#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#include <quicksort.h>

//  OBSERVAÇÃO :
//  1° int * V = Passagem de Parâmetro por Referência (ENDEREÇO)
//  2° int N = Passagem de Parâmetro por 

void ExibirVetor(int * V, int N) {
    for (int i = 0; i < N; i++) printf("%d\t", V[i]);
    printf("\n");

}

void TrocarElemento(int * A, int * B) {

    int temp = * A; // * A é o valor apontado por A
    * A = * B;
    * B = temp;

}

int Particao(int * V, int Inf, int Sup) {

    int Pivot = V[(Inf + Sup) / 2];
    int i = Inf;
    int j = Sup;

    while(i <= j) {
        while(V[i] < Pivot) i++;
        while(V[j] > Pivot) j--;
        if(i <= j) {
            TrocarElemento(&V[i], &V[j]);
            i++;
            j--;
        }
    }
    return i;   // Posição Final do Pivot
}

void QuickSort(int * V, int Inf, int Sup) {
    if (Inf < Sup) {
        int P = Particao(V, Inf, Sup);  //Posição é 4 e corresponde ao elemento [ 5 ]
        QuickSort(V, Inf, P - 1);   // QuickSort (V, 0, 3)
        QuickSort(V, P, Sup);   // QuickSort (V, 4, 6)
    }
}