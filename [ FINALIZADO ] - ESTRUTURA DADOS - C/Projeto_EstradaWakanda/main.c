#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "cidades.h"

int main() {
    Estrada *t1 = getEstrada("teste01.txt");
    double d1 = calcularMenorVizinhanca("teste01.txt");
    char *c1 = cidadeMenorVizinhanca("teste01.txt");

    if (t1) {
        printf("Comprimento estrada: %d\n", t1->T);
        printf("Numero de cidades: %d\n", t1->N);
    } else {
        printf("Problema na inicializacao da estrada!\n");
    }

    printf("Menor vizinhanca: %.2lf\n", d1);
    printf("Cidade com menor vizinhanca: %s\n", c1);

    free(c1);
    // Liberar outras estruturas se necessário
    return 0;
}
