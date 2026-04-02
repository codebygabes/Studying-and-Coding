#ifndef CIDADESH
#define CIDADESH

typedef struct Cidade {
    char Nome[256];      // Nome da cidade
    int Posicao;         // Coordenada da cidade
    struct Cidade * Proximo; // Ponteiro para a próxima cidade na estrada
} Cidade;

typedef struct {
    int N;              // Número de cidades
    int T;              // Comprimento da estrada
    Cidade * Inicio;     // Ponteiro para a primeira cidade na estrada
} Estrada;

Estrada *getEstrada(const char *nomeArquivo);
double calcularMenorVizinhanca(const char *nomeArquivo);
char *cidadeMenorVizinhanca(const char *nomeArquivo);

#endif
