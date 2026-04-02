#include <stdio.h>
#include <stdlib.h>

int main() {

    printf("Digite o tamanho do vetor : ");
    
    int N;
    scanf("%d", &N);

    int * V = (int *) malloc(N * sizeof(int));
    int Soma = 0;

    for (int i = 0; i < N; i++)
    {
        printf("DIgite um inteiro : ");
        scanf("%d", &V[i]);
        Soma = Soma + V[i];
    }
    
    printf("O valor da soma é : %d.\n", Soma);

    return 0;
}