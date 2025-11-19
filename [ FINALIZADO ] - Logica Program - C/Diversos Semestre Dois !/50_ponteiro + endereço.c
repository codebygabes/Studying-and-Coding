#include <stdio.h>
#include <stdlib.h>

int main () {

    int A = 10;
    int * ptr = &A; // Inicializa com ptr com o endereço de memoria de A.

    system("cls");
    printf("Valor da variavel A : %d \n", A);
    printf("O endereco da variavel A : %d \n", &A);
    printf("O endereco da variavel A : %d \n", ptr);
    printf("Valor da variavel A : %d \n", A);

}