#include <stdio.h>
#include <stdlib.h>

void modificarValor (int *X) {  //

    *X = *X + 10;
    // [*]X ---> Ponteiro.
}

int main() {

    int A = 5;

    modificarValor(&A);  // Endereço de A  

    printf("Valor de A: %d\n", A);  // 

    return 0;

}