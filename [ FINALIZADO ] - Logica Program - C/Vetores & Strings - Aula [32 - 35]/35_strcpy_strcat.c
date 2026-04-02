// C O P I A R   S T I N G   E M   V A R I A V E L !

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main() {

    char minhaString[50];
    strcpy(minhaString, "Esta e uma frase de exemplo"); // COPIA EM minhaString
    printf("%s\n", minhaString);

    // SEGUNDO EXEMPLO !

    char frase[1000];

    strcpy(frase, "Hoje ");
    strcpy(frase, "e sexta");
    strcpy(frase, "-feira!");
    printf("%s\n", frase); // -feira!

    strcpy(frase, "Hoje ");
    strcat(frase, "e sexta");
    strcat(frase, "-feira!");
    printf("%s\n", frase); // Hoje é sexta-feira!

    // %s     : escreve a string
    // strcpy : copiar strings em outra string
    // strcat : concatenar strings ( COLOCA NO FINAL )
    // strlen : calcular tamanho da string

    int L = strlen(frase);

    char palavra[] = "ABCD";
    int i;

    for ( i = 0; i < 4; i++) palavra[i] = palavra[i] + 1;
    {
        printf("%s\n", palavra); // Hoje é sexta-feira!
    }
    
    return 0;

}