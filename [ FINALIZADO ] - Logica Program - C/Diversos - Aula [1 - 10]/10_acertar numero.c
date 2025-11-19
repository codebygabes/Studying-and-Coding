#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int main() {

    srand(time(NULL));
    int magic = rand() % 1001; // limitar = usar resto da divisao
    int palpite;

    printf("Digite um Palpite: ");
    scanf("%d", &palpite);

    if (palpite == magic)
    {
        printf("Voce Acertou!\n");
        printf("O numero era: %d", magic);
    }
    else if (palpite < magic)
    {
        printf("Seu numero e Menor!\n");
        printf("O numero era: %d", magic);
    }
    else if (palpite > magic)
    {
        printf("Seu numero e Maior!\n");
        printf("O numero era: %d", magic);
    }

     return 0;
    
}