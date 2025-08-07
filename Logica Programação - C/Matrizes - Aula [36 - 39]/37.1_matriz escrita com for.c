#include <stdio.h>
#include <stdlib.h>

int main () {

    int matriz[3][5];

    for (int linha = 0; linha < 3; linha++)
    {
        for (int coluna = 0; coluna < 5; coluna++)
        {
            printf("ELEMENTO [%d][%d] : ", linha, coluna);
            scanf("%d", &matriz[linha][coluna]);
        }
        
    }
    system("cls"); // Limpa a tela antes de continuar;
    printf("\n");
    for (int linha = 0; linha < 3; linha++)
    {
        for (int coluna = 0; coluna < 5; coluna++)
        {
            printf("%d ", matriz[linha][coluna]);
        }
        printf("\n");
    }

   return 0;
}