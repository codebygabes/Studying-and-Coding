#include <stdio.h>
#include <stdlib.h>

int main () {

    int x = 10;

    printf("%d\n", x); // Mostra valor de X : 10
    printf("%d\n", &x); // Mostra o endereço de memoria de X

    /*
    Ou seja, &X mostra o endereço de memoria da variavel int x;
    Mas se colocando sem o &, o que será mostrado será apenas o valor da variavel !
    */

   printf("%x\n", &x); // %x : mostra o valor, em hexadecimal.
   printf("%p\n", &x); // %p : mostra o valor, em hexadecimal [ com 8 digitos ].
   printf("%d\n", &x); // le o valor de X : 10 [ e guarda na memoria ].

}