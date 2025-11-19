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

// Função Para Calcular Número de Nós da Árvore
int contarNos(No * N) {
    if (N == NULL) return 0;
    else return 1 + contarNos(N->Esquerda) + contarNos(N->Direita);
}

// Função Para Calcular Altura Árvore
int calcularAltura(No * N) {
    if (N == NULL) return 0;
    else {
        int h_Direita = calcularAltura(N->Direita);
        int h_Esquerda = calcularAltura(N->Esquerda);
        int Maior = (h_Direita > h_Esquerda) ? h_Direita : h_Esquerda;
        return 1 + Maior;
    }
}

int main() {
    // Nivel 1 ( raiz )
    No * Raiz = criarNo(10);

    // Nivel 2
    Raiz->Esquerda = criarNo(12);
    Raiz->Direita = criarNo(15);

    // Nivel 3
    Raiz->Esquerda->Esquerda = criarNo(16);
    Raiz->Esquerda->Direita = criarNo(18);
    Raiz->Direita->Direita = criarNo(20);

    // Nivel 4
    Raiz->Direita->Direita->Esquerda = criarNo(17);
    Raiz->Direita->Direita->Direita = criarNo(19);

    // Teste :

    printf("Numero de Nos : %d", contarNos(Raiz));
    printf("Altura : ")
}