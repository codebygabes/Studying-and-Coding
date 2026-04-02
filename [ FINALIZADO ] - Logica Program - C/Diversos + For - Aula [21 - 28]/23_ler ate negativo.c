// Ler números do usuário até que ele insira um número negativo.

#include <stdio.h>

int main() {

    int num;
printf("Digite um número (negativo para sair): ");
scanf("%d", &num);

while (num >= 0) {
    printf("Você digitou: %d\n", num);
    printf("Digite outro número: ");
    scanf("%d", &num);
}
return 0;
}
