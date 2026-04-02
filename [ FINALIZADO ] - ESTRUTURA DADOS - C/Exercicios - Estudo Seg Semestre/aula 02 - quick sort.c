#include <stdlib.h>
#include <stdio.h>

int particionar(int *v, int inicio, int fim) {
    int pivo = (v[inicio] + v[fim] + v[(inicio + fim) / 2]) / 3;
    while (inicio < fim)
    {
        while (inicio < fim && v[inicio] <= pivo)
            inicio++;

        while (inicio < fim && v[fim] > pivo)
            fim--;
        
        int aux = v[inicio];
        v[inicio] = v[fim];
        v[fim] = aux;
    }
    return inicio;
}

void quick_sort(int *v, int inicio, int fim) {
    if ( inicio < fim ) 
    {
        int pos = particionar(v, inicio, fim);
        quick_sort(v, inicio, pos - 1);
        quick_sort(v, pos, fim);

    }
}

void imprimir(int *v, int n) {
    for (int i = 0; i <= n; i++) 
    {
        printf("%d ", v[i]);
    }
    printf("\n");
}

int main() {

    int vet[] = {47, 35, 12, 49, 56, 25, 71, 83, 5, 3, 1, 70, 99, 10, 61};  // tamanho = 15

    imprimir(vet, 14);
    quick_sort(vet, 0, 14);
    imprimir(vet, 14);

    return 0;

}