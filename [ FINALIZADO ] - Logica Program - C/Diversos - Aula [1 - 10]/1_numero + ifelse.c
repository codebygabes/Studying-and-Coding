#include <stdio.h>

int main() {

    int X;   // existe variavel X.
    X = 2 * 7 - 3;   // atribuição
    int numero;   // Teste para ifelse

    printf ("X e igual a: %d\n", X);

    printf ("Agora me diga seu numero favorito: ");
    scanf ("%d", &numero);

    if (numero > X)   // condição para ifelse funcionar.
    {
        printf ("O seu numero e maior que o calculo!");
    }
    else
    {
        printf ("O seu numero e menor!");
    }
    

    return 0;
}