#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "cidades.h"

// Função auxiliar para comparar cidades pela posição (para ordenação)
int compararCidades(const void *a, const void *b) {
    Cidade *ca = *(Cidade **)a;
    Cidade *cb = *(Cidade **)b;
    return ca->Posicao - cb->Posicao;
}

// Função auxiliar para liberar memória
void liberarEstrada(Estrada *estrada) {
    if (!estrada) return;
    Cidade *cidade = estrada->Inicio;
    while (cidade) {
        Cidade *prox = cidade->Proximo;
        free(cidade);
        cidade = prox;
    }
    free(estrada);
}

Estrada *getEstrada(const char *nomeArquivo) {
    FILE *arq = fopen(nomeArquivo, "r");
    if (!arq) return NULL;
    int T, N, i;
    if (fscanf(arq, "%d", &T) != 1) { fclose(arq); return NULL; }
    if (fscanf(arq, "%d", &N) != 1) { fclose(arq); return NULL; }
    if (T < 3 || T > 1000000 || N < 2 || N > 10000) { fclose(arq); return NULL; }

    Cidade **arrayCidades = malloc(N * sizeof(Cidade*));
    int *vistos = malloc(T * sizeof(int));
    memset(vistos, 0, T * sizeof(int)); // marca posições repetidas
    for (i = 0; i < N; ++i) {
        int pos;
        char nome[256];
        if (fscanf(arq, "%d %[^\n]s", &pos, nome) != 2) {
            free(arrayCidades); free(vistos); fclose(arq); return NULL;
        }
        if (pos <= 0 || pos >= T || vistos[pos]) {
            free(arrayCidades); free(vistos); fclose(arq); return NULL;
        }
        vistos[pos] = 1;
        Cidade *cidade = malloc(sizeof(Cidade));
        strcpy(cidade->Nome, nome);
        cidade->Posicao = pos;
        cidade->Proximo = NULL;
        arrayCidades[i] = cidade;
    }
    fclose(arq); free(vistos);

    // Ordena cidades por posição (necessário para calcular vizinhança)
    qsort(arrayCidades, N, sizeof(Cidade*), compararCidades);

    Estrada *estrada = malloc(sizeof(Estrada));
    estrada->T = T;
    estrada->N = N;
    estrada->Inicio = arrayCidades[0];
    for (i = 0; i < N - 1; ++i) arrayCidades[i]->Proximo = arrayCidades[i + 1];
    arrayCidades[N - 1]->Proximo = NULL;

    free(arrayCidades);
    return estrada;
}

// Função para calcular vizinhança de uma cidade
double vizinhanca(Cidade *cidadeAntes, Cidade *cidade, Cidade *cidadeDepois, int T) {
    double esquerda = cidadeAntes ? (cidade->Posicao + cidadeAntes->Posicao) / 2.0 : cidade->Posicao / 1.0;
    double direita  = cidadeDepois ? (cidade->Posicao + cidadeDepois->Posicao) / 2.0 : (T + cidade->Posicao) / 2.0;
    if (!cidadeAntes) esquerda = cidade->Posicao;
    if (!cidadeDepois) direita = T - cidade->Posicao;
    return direita - esquerda;
}

double calcularMenorVizinhanca(const char *nomeArquivo) {
    Estrada *estrada = getEstrada(nomeArquivo);
    if (!estrada) return 0.0;
    double menor = 1e9;
    Cidade *ant = NULL, *atual = estrada->Inicio, *prox = atual ? atual->Proximo : NULL;
    while (atual) {
        double left = ant ? (atual->Posicao + ant->Posicao) / 2.0 : 0.0;
        double right = prox ? (atual->Posicao + prox->Posicao) / 2.0 : estrada->T * 1.0;
        double tam = right - left;
        if (tam < menor) menor = tam;
        ant = atual;
        atual = prox;
        prox = prox ? prox->Proximo : NULL;
    }
    liberarEstrada(estrada);
    return menor;
}

char *cidadeMenorVizinhanca(const char *nomeArquivo) {
    Estrada *estrada = getEstrada(nomeArquivo);
    if (!estrada) return NULL;
    double menor = 1e9;
    char nome[256] = "";
    Cidade *ant = NULL, *atual = estrada->Inicio, *prox = atual ? atual->Proximo : NULL;
    while (atual) {
        double left = ant ? (atual->Posicao + ant->Posicao) / 2.0 : 0.0;
        double right = prox ? (atual->Posicao + prox->Posicao) / 2.0 : estrada->T * 1.0;
        double tam = right - left;
        if (tam < menor) {
            menor = tam;
            strcpy(nome, atual->Nome);
        }
        ant = atual;
        atual = prox;
        prox = prox ? prox->Proximo : NULL;
    }
    liberarEstrada(estrada);
    char *resultado = malloc(strlen(nome)+1);
    strcpy(resultado, nome);
    return resultado;
}
