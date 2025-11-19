#include <stdio.h>
#include <locale.h>
#include <math.h>

int main() {

    setlocale(LC_ALL, "Portuguese");
    
    float a;
    float b;
    float c;
    
    printf("Diga-me um numero: ");
    scanf("%f", &a);
    printf("Diga-me um outro numero: ");
    scanf("%f", &b);
    printf("Numero para o intervalo: ");
    scanf("%f", &c);
    
    if (c >= a && c <= b) {
    	printf("Seu numero: %.0f, esta dentro do intervalo de: [%.2f , %.2f]", c , a , b);
	}
	else {
		printf("Seu numero: %.0f, nao esta dentro do intervalo de: [%2.f , %2.f]", c , a , b);
	}
}
