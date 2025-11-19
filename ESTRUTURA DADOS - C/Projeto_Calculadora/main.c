#include <stdio.h>
#include <string.h>
#include "expressao.h"

int main() {
    char testes[][64] = {
        "3 4 + 5 *",
        "7 2 * 4 +",
        "8 5 2 4 + * +",
        "6 2 / 3 + 4 *",
        "9 5 2 8 * 4 + * +",
        "2 3 + log 5 /",
        "10 log 3 ^ 2 +",
        "45 60 + 30 cos *",
        "0.5 45 sen 2 ^ +"
    };

    for (int i = 0; i < 9; i++) {
        // Cópias para não corromper as strings com strtok
        char exp_infixa[64], exp_valor[64];
        strncpy(exp_infixa, testes[i], 63); exp_infixa[63] = 0;
        strncpy(exp_valor, testes[i], 63); exp_valor[63] = 0;

        char *infixa = getFormaInFixa(exp_infixa);
        printf("Teste %d:\n", i+1);
        printf("  Pos-fixa: %s\n", testes[i]);
        if (infixa != NULL) printf("  Infixa: %s\n", infixa);
        else printf("  Infixa: [Expressao inválida]\n");
        printf("  Valor: %.2f\n\n", getValorPosFixa(exp_valor));
    }
    return 0;
}
