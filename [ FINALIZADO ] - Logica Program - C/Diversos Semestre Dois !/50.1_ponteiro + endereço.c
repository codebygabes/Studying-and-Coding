#include <stdio.h>
#include <stdlib.h>

int main () {

    int I = 100;
    int *p;

    p = &I; // p recebe o endereço de memoria da variavel I.

    system("cls");
    printf("%d \n", * p); // Mostra o valor da variavel guardada em p [ 100 ].

    *p = *p * 2;
    printf("%d \n", * p); // Mostra o valor da variavel I vezes 2. [ guardada em p = 200 ].
    printf("%d \n", I);

}