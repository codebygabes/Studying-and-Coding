#include <stdio.h>
#include <locale.h>
#include <math.h>

int main() {

    setlocale(LC_ALL, "Portuguese");
    
    int i;
    printf("Digite a idade: ");
    scanf("%d", &i);
    
    // Idade >- 18 E Idade <= 69
    
    if (i >= 18 && i <= 69) {
    	printf("Para a idade, voto obrigatorio!\n");
	}
	else {
		printf("Voto é facultativo!");
	}
	
}
	
