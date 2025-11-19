#include <stdio.h>
#include <stdlib.h>

int main () {

    int matriz[4][4];
    int l, c;

    for (c = 0; c < 4; c++)
    {
        for (l = 0; l < 4; l++)
        { 
           if (matriz[l][c] % 1)
           {
             matriz[l][c] = l + c + 1;
            printf(" %d ", matriz[l][c]);
           }
           else
           {
             matriz[l][c] = l + c + 0;
            printf(" %d ", matriz[l][c]);
           }
           
        }
        printf("\n");
    }

    return 0;
}