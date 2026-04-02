#include <stdlib.h>
#include <stdio.h>

typedef struct no{
    int valor;
    struct no *proximo;
}No;

void inserirNaFila(No **fila, int num) {
    No *aux, *novo = malloc(sizeof(No));
    if(novo) {
        novo->valor = num;
        novo->proximo = NULL;
        if (*fila == NULL)
            *fila = novo;
        else {
            aux = *fila;
            while(aux->proximo)
                aux = aux->proximo;
            aux->proximo = novo;
        }
        
    }
    else
        printf("\nErro ao alocar memoria\n");
}

No* removerDaFila(No **fila) {
    No *remover = NULL;
    if(*fila) {
        remover = *fila;
        *fila = remover->proximo;
        remover->proximo = NULL;
    }
    else 
        printf("\nFila vazia\n");
    return remover;

}

void imprimirFila(No *fila) {
    printf("\t--------- FILA ---------\n\t");
    while(fila) {
        printf("%d ", fila->valor);
        fila = fila->proximo;
    }
    printf("\n\t------- FIM FILA -------\n");
}

int main() {
    No *r, *fila = NULL;
    int opcao, valor;

    do{
        printf("\t---- ---- ---- ---- ---- ---- ---- ---- ---- ----\n");
        printf("\t [ 0 ] - Sair\n\t [ 1 ] - Inserir\n\t [ 2 ] - Remover\n\t [ 3 ] - Imprimir\n");
        scanf("%d", &opcao);
        printf("\n\t---- ---- ---- ---- ---- ---- ---- ---- ---- ----\n");

        switch (opcao) {
            case 1:
                printf("\nDigite um numero: ");
                scanf("%d", &valor);
                inserirNaFila(&fila, valor);
                break;
            case 2:
                r = removerDaFila(&fila);
                if (r) {
                    printf("Removido: %d\n", r->valor);
                    free(r);
                }
                break; 
            case 3:
                imprimirFila(fila);
                break;
            default:
                if(opcao != 0)
                    printf("\nOpcao invalida\n");
        }
    } while(opcao != 0);

    return 0;
}
