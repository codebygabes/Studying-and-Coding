#include <stdlib.h>
#include <stdio.h>

void bubble_sort(int *v, int n) 
{
    int troca = 1;
        
    while (troca)
    {
        troca = 0;
        for (int i = 0; i < n - 1; i++) 
        {
            if (v[i] > v[i + 1]) 
            {
                int aux = v[i];
                v[i] = v[i + 1];
                v[i + 1] = aux;
                troca = 1;
            }
        }
        n--;
    } 
    
}

void bubble_sort_recursivo(int *v, int n) {
    if (n < 2) return;
    for (int i = 0; i < n - 1; i++) 
    {
        if (v[i] > v[i + 1]) 
        {
            int aux = v[i];
            v[i] = v[i + 1];
            v[i + 1] = aux;
        }
    }
    bubble_sort_recursivo(v, n - 1);
}

void imprimir(int *v, int n) 
{
    for (int i = 0; i < n; i++) 
    {
        printf("%d ", v[i]);
    }
    printf("\n");
}

int main() 
{
    int vet[] = {5, 8, 9, 4, 10, 6, 3, 7, 1, 2};
    int n = 10;

    imprimir(vet, n);
    // bubble_sort(vet, n);

    bubble_sort_recursivo(vet, n);
    imprimir(vet, n);

    return 0;
}