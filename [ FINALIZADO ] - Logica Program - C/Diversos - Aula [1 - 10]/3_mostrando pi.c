#include <stdio.h>
#include <locale.h>
#include <math.h>

void main() {

    setlocale(LC_ALL, "Portuguese");

    float PI = 3.1415926535;
    float circ;
    printf("o número pi vale: %.0f\n", PI);
    printf("o número pi vale: %.1f\n", PI);
    printf("o número pi vale: %.2f\n", PI);
    printf("o número pi vale: %f\n", PI);   // Mostrar as 6 primeiras casas de pi

    printf("o número pi vale: %.2f\n", 2*PI*10);


    circ = PI * (pow(12, 2));
    printf("o valor da circunferencia e: %.3f", circ);
    
    return 0;
}