#include <stdio.h>
#include <stdlib.h>

typedef struct {

    float A;
    float B;
    float C;
    float Perimetro;
    float Area;
    char Nome[3];

} Triangulo;

int main() {

    int N = 1000;
    Triangulo * V = (Triangulo * )(N * sizeof(Triangulo));
    printf("Tentando alocar %d bytes!\n", N * sizeof(Triangulo));

    if (V == NULL)
    {
        printf("ERRO: falha na alocacao de memoria\n");
        exit(1);
    }
    
    printf("A memoria foi alocada com SUCESSO!\n");

    return 0;
}