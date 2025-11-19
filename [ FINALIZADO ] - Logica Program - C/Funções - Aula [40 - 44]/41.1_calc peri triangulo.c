#include <stdio.h>
#include <stdlib.h>

// Implementando uma funcao para calcular o perimetro de um triangulo qualquer

// Perimetro = (3, 4, 5) = 12
// Perimetro = (4, 4, 5) = 13

float perimetro(float A, float B, float C) {

    float temp = A + B + C;
    return temp;

}

int main() {

    printf("Perimetro : %.2f", perimetro(3, 4, 5));
    return 0;

}