#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int C;  // Quantidade de IF's
int M;  // Quantidade de Iterações

void SelectioSort (int * V, int N); // Ordenar vetor V tamanho N

int main() {

    C = 0;
    M = 0;

    int Vetor [] = {4, 6, 1, 2, 5, 3, 7};
    int Tamanho = sizeof(Vetor) / sizeof(int);

    clock_t T0 = clock();   // Marcar = Inicio Contagem
    SelectioSort(Vetor, Tamanho);
    clock_t TF = clock();   // Marcar = Fim Contagem

    double TempoCPU = ((double)TF - T0) / CLOCKS_PER_SEC;

    for(int i = 0; i < Tamanho; i++) printf("%d\t", Vetor[i]);

    printf("\n");
    printf("C : %d\n", C);
    printf("M : %d\n", M);
    printf("\n\n TEMPO CPU : %lf SEG\n", TempoCPU);

    return 0;

}

void SelectioSort (int * V, int N)
{
    for (int i = 0; i < N - 1; i++)
    {
        int minIndex = i;

        for(int j = i + 1; j < N; j++) {
            C = C + 1;  // 1 IF = Iteração 2° For
            if(V[j] < V[minIndex]) minIndex = j;
        }
        
        M = M + 3;  // 3 Atribuições = Iteração 1° For
        int temp = V[minIndex];
        V[minIndex] = V[i];
        V[i] = temp;
    }
    
}