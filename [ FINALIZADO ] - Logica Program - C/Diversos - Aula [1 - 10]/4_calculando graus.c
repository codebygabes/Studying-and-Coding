#include <stdio.h>
#include <locale.h>

void main() {

    setlocale(LC_ALL, "Portuguese");

    float temp;
    float f;
    printf("Quantos graus esta? ");
    scanf("%f", &temp);

    f = (temp * 9 / 5) + 32;
    printf("a temperatura no EUA e: %.2f", f);
    
    return 0;
}