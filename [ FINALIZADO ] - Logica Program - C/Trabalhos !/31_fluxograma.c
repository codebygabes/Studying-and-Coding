#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main() {

    int abc, cba = 0;
    int xyz, zyx = 0;
    int centena, unidade;
    int resto;
    int resultado, oABC, oXYZ; // oABC | oXYZ = orginal.

    // Strings formatadas com zeros à esquerda;
    char strABC[5], strCBA[5], strXYZ[6], strZYX[5], strResultado[6];

    do {
        printf("====================================\n");
        printf("   ENTER A 3-DIGIT NUMBER (100-999)\n");
        printf("====================================\n");
        printf(">> ");
        scanf(" %d", &abc);

    // SEPARA OS DIGITOS.
    centena = abc / 100;
    unidade = abc % 10;

        // VERIFICAR SE ESTA DENTRO DO PEDIDO.
        if (abc < 100 || abc > 999) {
            printf("THIS IS NOT ACCORDING TO THE REQUEST!\n");
        } else if (abs(centena - unidade) < 2) // abs: absolute value.
        {
            printf("HUNDRED > UNIT | 2 VALUES!\n");
        }
        

    } while (abc < 100 || abc > 999 || abs(centena - unidade) < 2);

    system("cls"); // Limpa a tela antes de continuar;

    // INVERTE O NÚMERO ( ABC ).
    oABC = abc;
    resto = 0;
    while (abc != 0) {
        resto = abc % 10; // Obtém : último dígito.
        cba = cba * 10 + resto; // Adiciona : número invertido.
        abc /= 10; // Remove : último dígito -- número original.
    }

    // SOMAR ( ABC + CBA ).
    xyz = oABC + cba;

    // INVERTE O NÚMERO ( XYZ ).
    oXYZ = xyz;
    resto = 0;
    while (xyz != 0) {
        resto = xyz % 10; // Obtém : último dígito.
        zyx = zyx * 10 + resto; // Adiciona : número invertido.
        xyz /= 10; // Remove : último dígito -- número original.
    }

    // SOMAR ( XYZ + ZYX ).
    resultado = oXYZ + zyx;

     // FORMATAR PARA ZERO NA ESQUERDA.
    sprintf(strABC, "%03d", oABC);
    sprintf(strCBA, "%03d", cba);
    sprintf(strXYZ, "%04d", oXYZ);      // Até : 4 dígitos
    sprintf(strZYX, "%04d", zyx);       // Pode Ter : zero na esquerda.
    sprintf(strResultado, "%04d", resultado);

    // EXIBIR VALORES FINAIS.
    printf("====================================\n");
    printf("            FINAL RESULTS           \n");
    printf("====================================\n");
    printf("Original Number ( ABC )     : %*s\n", 6, strABC);
    printf("Reversed ( CBA )            : %*s\n", 6, strCBA);
    printf("Sum ( ABC + CBA ) = XYZ     : %*s\n", 6, strXYZ);
    printf("Reversed ( ZYX )            : %*s\n", 6, strZYX);
    printf("Final Result                : %*s\n", 6, strResultado);
    printf("====================================\n");

    return 0;

}