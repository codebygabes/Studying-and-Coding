#include <stdio.h>
#include <stdlib.h>

int main() {
    int num, invertido = 0, resto;


    printf("Digite um numero: ");
    scanf("%d", &num);

    // Inverte o número
    while (num != 0) {
        resto = num % 10;            // Obtém o último dígito
        invertido = invertido * 10 + resto; // Adiciona ao número invertido
        num /= 10;                   // Remove o último dígito do número original
    }


    printf("Numero invertido: %d\n", invertido);

    return 0;
}