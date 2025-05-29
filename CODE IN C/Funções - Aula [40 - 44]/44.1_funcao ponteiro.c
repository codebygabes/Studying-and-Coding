#include <stdio.h>
#include <stdlib.h>

void modificarValor (int * X, int N) {  // Passagem de Parametro por Referencia

    * X + 100;  // Atribui 100 para o primeiro elemento.
    X[2] = 500;  // Segunda Posição depois de X será Alterada para 500.
    // [*]X ---> Ponteiro.
}

int main() {

    int numeros[] = {1, 2, 3};

    modificarValor(numeros, 3);

    for (int i = 0; i < 3; i++)
    {
        printf("%d", numeros[i]);
    }

    return 0;

}