#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct
{
    char Data [10];
    int Valor;
} Elemento;

Elemento lerElemento();
float calcularMedia(Elemento X[]);
void exibirGrafico(Elemento X[]);

int main() {

    Elemento X[12];
    for (int i = 0; i < 12; i++)
    {
        X[i] = lerElemento();
        float Media = calcularMedia(X);
        printf("Media : %.2f \n", Media);
    }

    return 0;
}

Elemento lerElemento () {

    Elemento E;
    scanf("%[^;]; %d", E.Data, &E.Valor);
    return E;
}

float calcularMedia(Elemento X[]) {

    float S = 0;
    for (int i = 0; i < 12; i++)
    {
        S = S + X[i].Valor;
    }
    return S/12.0;
}