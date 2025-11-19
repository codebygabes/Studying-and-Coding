#include <stdio.h>
#include <stdlib.h>

int main() {
    int n;
    int primo, primo2;
    int i, j, k;
    int encontrou = 0;

    printf("DECOMPOR NUMERO EM PRIMO: ");
    scanf("%d", &n);
    
    // NAO DECOMP = NUMERO < 2
    if (n <= 2) {
        printf("\n======== OPS! =========\n");
        printf("O %d PRECISA SER > 2!\n", n);
        printf("=======================\n");
        return 0;
    }

    // LOOP PARA I ATE N
    for (i = 2; i <= n / 2; i++) {  
        primo = 1; // I = PRIMO

        // VER SE I = PRIMO
        for (j = 2; j < i; j++) {
            if (i % j == 0) {
                primo = 0; // TEM DIVISOR = NAO É PRIMO
                break;
            }
        }

        if (primo != 0) {
            k = n - i; // NUMERO COMPLIMENTAR DE K

            if (k >= 2) { 
                primo2 = 1; // K = PRIMO

                // VER SE K = PRIMO
                for (j = 2; j < k; j++) {
                    if (k % j == 0) {
                        primo2 = 0;
                        break;
                    }
                }

                // EXIBE I & K = PRIMOS
                if (primo2 != 0) {
                    if (!encontrou) {
                        printf("\n======== ENCONTRADO! ========\n");
                        encontrou = 1; // DECOMP = ENCONTRADA
                    }
                    printf("%d + %d = %d\n", i, k, n);
                }
            }
        }
    }
                if (encontrou == 1)
                {
                    printf("=============================\n");
                }
    

    // NAO ENCONTRAR DECOMP
    if (!encontrou) {
        printf("\n======== SEM DECOMPOSICAO! ========\n");
        printf("NAO FOI POSSIVEL DECOMPOR!\n");
        printf("==============================\n");
    }

    return 0;
}
