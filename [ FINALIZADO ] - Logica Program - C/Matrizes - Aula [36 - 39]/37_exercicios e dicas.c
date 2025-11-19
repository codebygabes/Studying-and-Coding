// UTILIZANDO " FOR ( ... ) "

#include <stdio.h>

int main () {

    int matriz[4][5] = {
        {4, 7, 5, 3, 2},     
        {1, 2, 9, 0, 7},
        {3, 0, 7, 1, 2},
        {8, 1, 1, 0, 0}      
    };
    int c, l;
    int soma = 0;
    int produto = 1;

    for (c = 0; c < 5; c++) 
    {
        printf("%d ", matriz[2][c]); // SEGUNDA LINHA DA MATRIZ.
    }

    printf("\n--------------------\n");

    // EXERCICIO 01 :
    // SOMA = PRIMEIRA LINHA DA MATRIZ.

    for (c = 0; c < 5; c++)
    {
        printf("%d ", matriz[0][c]);
        soma = soma + matriz[0][c];
    }
    printf("\nSOMA DA PRIMEIRA LINHA : %d", soma);

    printf("\n--------------------\n");

    // EXERCICIO 02 :
    // PRODUTO = PRIMEIRA COLUNA DA MATRIZ.

    for (l = 0; l < 4; l++)
    {
        printf("{ %d }\n", matriz[l][0]);
        produto = produto * matriz[l][0];
    }
    printf("PRODUTO DA PRIMEIRA COLUNA : %d", produto);

    printf("\n--------------------\n");

    // D I C A :
    // EXIBIR A MATRIZ INTEIRA [ NÃO EFICIENTE ! ].

    for (c = 0; c < 5; c++)
    {
        printf("%d ", matriz[0][c]);
    }
    printf("\n");
    for (c = 0; c < 5; c++)
    {
        printf("%d ", matriz[1][c]);
    }
    printf("\n");
    for (c = 0; c < 5; c++)
    {
        printf("%d ", matriz[2][c]);
    }
    printf("\n");
    for (c = 0; c < 5; c++)
    {
        printf("%d ", matriz[3][c]);
    }

    printf("\n--------------------\n");

    // D I C A :
    // EXIBIR A MATRIZ INTEIRA [ EFICIENTE ! ].

    for (l = 0; l < 4; l++) // PERCORRER CADA LINHA DA MATRIZ.
    {
        for (c = 0; c < 5; c++) // PERCORRER CADA COLUNA DA MATRIZ.
        {
            printf("%d ", matriz[l][c]);
        }
        printf("\n");
    }
    
    return 0;
}