#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>  // Incluindo a biblioteca para tolower() e toupper()

int main() {

    char letra;
    scanf("%c", &letra);

    // Convertendo a letra para maiúscula (caso seja minúscula)
    letra = toupper(letra);

    switch (letra)
    {
    case 'A':
        printf("Digitada a primeira letra da palavra ARANHA!");
        break;
    case 'B':
        printf("Digitada a primeira letra da palavra BARCO!");
        break;
    case 'C':
        printf("Digitada a primeira letra da palavra CACHORRO!");
        break;
    default: 
        printf("Nao foi digitado as letra: A, B, C!");
    }

    return 0;
    
}