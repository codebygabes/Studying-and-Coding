#include <stdio.h>
#include <stdlib.h>

int main () {

    int matriz[3][3];
    int l, c;

    for (c = 0; c < 3; c++)
    {
        for (l = 0; l < 3; l++)
        { 
            matriz[l][c] = l + c + 1;
            printf(" %d ", matriz[l][c]);
        }
        printf("\n");
    }

    return 0;
}