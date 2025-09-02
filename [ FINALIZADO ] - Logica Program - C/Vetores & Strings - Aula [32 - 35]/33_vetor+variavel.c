#include <stdio.h>
#include <stdlib.h>

#define TAMANHO 10
#define FRASE "Hoje e Sexta-Feira!"
#define DECIMAL 10.0 

int main() {

    int n = 10;
    float temp[n]; // Determinar Tamanho do Vetor : NAO USE VETOR!
    float vetor[TAMANHO]; // vetor com tamanho definido via #define
    
    printf("%d\n", TAMANHO);   // Mostra: 10
    printf("%s\n", FRASE);     // Mostra: Hoje e Sexta-Feira!
    printf("%.2f\n", DECIMAL); // Mostra: 10.00 (com 2 casas decimais)

    return 0;

} 