// DOMINIO + CONTRA-DOMINIO!
#include <stdio.h>
#include <stdlib.h>

// Observação : traço é a soma dos elementos da diagonal principal.

// Dominio = {1, 2, 3, 4, 5,} (int)
// Contra-Dominio _ {A, B, C, D, E} (char)
// A função " f " transformará int's e, char's

char f (int X) {
    char resposta;
    if (X == 1) resposta = 'C';
    else if (X == 2) resposta = 'D';
    else if (X == 3) resposta = 'E';
    else if (X == 4) resposta = 'B';
    else if (X == 5) resposta = 'E';
    return resposta;
  
    
}

int main() {

    char teste = f(4);  // Imprime = 'B'
    printf("%c\n", teste);

    char teste2 = f(5);  // Imprime = 'E'
    printf("%c\n", teste2);

    return 0;
}