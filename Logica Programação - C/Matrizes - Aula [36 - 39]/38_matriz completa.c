#include <stdio.h>
#include <stdlib.h>

int main () {

    int matriz[4][4];
    int l, c;

    for (c = 0; c < 4; c++)
    {
        for (l = 0; l < 4; l++)
        {
            printf("ELEMENTO [%d][%d] : ", l, c);
            scanf(" %d", &matriz[l][c]);
        }
        printf("\n");
    }

    system("cls"); // Limpa a tela antes de continuar;
    
    for (l = 0; l < 4; l++)
    {
        for (c = 0; c < 4; c++)
        {
            if (l == c)
            {
                printf(" %d ", matriz[l][c]);
            }
            else
            {
                printf(" ");
            }
        }
        printf("\n");
    }
    
    return 0;
    
}
