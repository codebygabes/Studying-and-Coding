#include <stdio.h>
#include <stdlib.h>

int main () {

    int x = 10;
    int * ptr; // Inicializando, primeiramente, o ponteiro * ptr !

    ptr = &x;  // Armazenar, em ptr, o endereço de memória da variavel X.

    printf(" %d\t %p\t ", ptr, ptr); // Será mostrado o endereço de X.

    /*
    %x : mostra o valor, em hexadecimal.
    %p : mostra o valor, em hexadecimal [ com 8 digitos ].
    %d : mostra o valor de um inteiro.

    */

}