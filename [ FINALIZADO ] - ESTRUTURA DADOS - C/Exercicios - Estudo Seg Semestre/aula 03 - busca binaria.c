#include <stdlib.h>
#include <stdio.h>

int busca_binaria_recursiva(int *vet, int chave, int inicio, int fim) {
    int meio;
    if(inicio <= fim) {
        meio = (inicio + fim) / 2;
        if(chave == vet[meio])
            return meio; // encontrado
        else {
            if(chave < vet[meio])
                return busca_binaria_recursiva(vet, chave, inicio, meio - 1);
            else
                return busca_binaria_recursiva(vet, chave, meio + 1, fim);
        }
    }
    return -1; // não encontrado
}

int busca_binaria_iterativa(int *vet, int chave, int fim) {
    int inicio = 0;
    int meio = (inicio + fim) / 2;

    while(inicio <= fim) {
        if(chave == vet[meio])
            return meio; // encontrado
        else {
            if(chave < vet[meio])
                fim = meio - 1;
            else
                inicio = meio + 1;
        }
        meio = (inicio + fim) / 2;
    }
    return -1; // não encontrado
}

int main() {

    int vet[] = {13, 14, 19, 43, 47, 52, 65, 82, 89, 91}; // tamanho = 10
    int valor, op;

    do 
    {
        printf("Digite o valor a ser buscado: ");
        scanf("%d", &valor);

        //printf("Resultados da Busca: %d\n", busca_binaria_recursiva(vet, valor, 0, 9));
        printf("Resultados da Busca Iterativa: %d\n", busca_binaria_iterativa(vet, valor, 9));

        printf("\n 0 - Sair\n 1 - Nova Busca\n");
        scanf("%d", &op);
    } while (op != 0);
    
    return 0;
}