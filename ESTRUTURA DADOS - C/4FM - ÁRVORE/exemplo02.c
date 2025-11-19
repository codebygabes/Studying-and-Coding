#include <stdlib.h>
#include <stdio.h>

typedef struct No {
    char Chave;
    struct No * F[3];
    struct No * Esquerda;
    struct No * Direita;
} No;

No * criarNo (char Chave) {
    No * N = (No *) malloc(sizeof(No));
    if (N == NULL) {
        printf("ERRO : nao ha memoria para No!\n");
        return NULL;
    }

    N->Chave = Chave;
    N->Esquerda = NULL;
    N->Direita = NULL;

    return N;
}

int main() {
    No * A = criarNo('a');
    No * B = criarNo('b');
    No * C = criarNo('c');
    No * D = criarNo('d');

    A->Esquerda = B;
    A->Direita = C;
    C->Direita = D;

    system("cls");

    printf("%c", A->Direita->Direita->Chave);
    printf("%c", D->Chave);

}