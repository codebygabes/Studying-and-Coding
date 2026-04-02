#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#define TAMANHO 1000




void InsertionSort(int * V, int N); // Ordenar o vetor V, de N elementos
void ExibirVetor(int * V, int N);   // Exibir vetor V, de N elementos

int main() {

    int Conjunto[TAMANHO];
    for (int i = 0; i < TAMANHO; i++) Conjunto[i] = rand();
    

    clock_t T0 = clock();
    InsertionSort(Conjunto, TAMANHO);
    clock_t TF = clock();

    ExibirVetor(Conjunto, TAMANHO);

    double TempoCPU = (double)(TF - T0) / CLOCKS_PER_SEC;
    printf("TEMPO DE CPU : %f\n", TempoCPU);

}

void InsertionSort(int * V, int N) {
    int Chave;  // Primeiro Elemento = Parte Não Ordenada | Proximo Ser Inserido = Parte Ordenada
    int i;  // Posição Elemento Chave = Dentro Vetor
    int j;  // Posição Elemento [ Parte Ordenada ] -- Que Será Comparada Com CHave

    for ( i = 1; i < N; i++ ) 
    {
        Chave = V[i];
        j = i - 1;

        while ( V[i] > Chave && j > 0 )
        {
            V[j + i] = V[j];
            j--;
        }

        V[j + i] = Chave;
        
    }

}

void ExibirVetor(int * V, int N) {
    int i;
    for ( i = 0; i < N; i++) printf("%d\t", V[i] );
    printf(" \n");
    
}