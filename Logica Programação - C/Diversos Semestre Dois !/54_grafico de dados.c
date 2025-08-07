#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct {
    char Data[20];
    int Valor;
} Elemento;

Elemento lerElemento ();
float calcularMedia(Elemento X[]);
void construirGrafico (Elemento X[]);

int main() {

    Elemento Dados[12];
    for(int i = 0; i < 12; i++) Dados[i] = lerElemento();
    construirGrafico(Dados);

    return 0;
}

Elemento lerElemento () {
    Elemento E;
    scanf("%[^;];%f", E.Data, &E.Valor);
    return E;
}

float calcularMedia(Elemento X[]) {
    float S = 0;
    for (int i = 0; i < 12; i++) S = S + X[i].Valor;
    return S/12.0;
}

void construirGrafico (Elemento X[]) {

    // Coloque aqui sua implementação para a função construir gráfico
    
}

/*

Dados de teste:

data;valor
01/01/2011;78
01/01/2012;78
01/01/2013;82
01/01/2014;83
01/01/2015;85
01/01/2016;84
01/01/2017;82
01/01/2018;99
01/01/2019;100
01/01/2020;134
01/01/2021;177
01/01/2022;209

 * 
 * 
 */

