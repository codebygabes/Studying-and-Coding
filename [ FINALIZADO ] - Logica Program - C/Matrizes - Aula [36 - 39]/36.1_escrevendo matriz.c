#include <stdio.h>

int main () {

    int matriz[4][5] = {
        {4, 7, 5, 3, 2},     
        {1, 2, 9, 0, 7},
        {3, 0, 7, 1, 2},
        {8, 1, 1, 0, 0}      
    };

    printf("LINHA DOIS, COLUNA ZERO: %d\n", matriz[2][0]);
    printf("LINHA DOIS, COLUNA UM: %d\n", matriz[2][1]);
    printf("LINHA DOIS, COLUNA DOIS: %d\n", matriz[2][2]);
    printf("LINHA DOIS, COLUNA TRES: %d\n", matriz[2][3]);
    printf("LINHA DOIS, COLUNA QUADRO: %d\n", matriz[2][4]);

    // OUTRA SOLUÇÃO :

    printf("\n%d, %d, %d, %d, %d", matriz[2][0], matriz[2][1], matriz[2][2], matriz[2][3], matriz[2][4]);

    return 0;
}