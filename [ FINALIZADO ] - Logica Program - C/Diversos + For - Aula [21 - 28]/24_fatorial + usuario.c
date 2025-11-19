#include <stdio.h>

int main() {
    int num, i;
    int fatorial = 1;
    char resp;

   do {

    fatorial = 1; // Reinicializa o fatorial para evitar acúmulo de valores anteriores
    
    // Solicita ao usuário que insira um número
    printf("DIGITE UM NUMERO POSITIVO + INTEIRO: ");
    scanf("%d", &num);

    // Verifica se o número é negativo
    if (num < 0) {
        printf("FATORIAL NAO PODE SER NEGATIVO!.\n");
    } else {
        // Calcula o fatorial
        for (i = 1; i <= num; i++) {
            fatorial *= i; // Multiplica o valor atual de fatorial pelo número do loop
        }
        
        // Exibe o resultado
        printf("O FATORIAL DE: %d, E %d\n", num, fatorial);

    }
    
    printf("DESEJA FAZER MAIS CONTAS? (S/N) ");
    scanf(" %c", &resp);

   } while (resp == 's' || resp == 'S');

    return 0;
}
