#include <stdio.h>
#include <stdlib.h>

// Relacionar os lados {2, 3, 4, 5} com as areas {4, 9, 16, 25}
// Dominio = {2, 3, 4, 5}
// Contra-dominio = {4, 9, 16, 25} (int)

int area (int X) {

    if(X == 2) return 4;
    else if (X == 3) return 9;
    else if (X == 4) return 16;
    else if (X == 5) return 25;

}

int main () {

    int l = 2;
    int a = area(l);
    int i;

    printf("L\t-->\tA\n");
    for (i = 1; i < 4; i++)
    {
        a = l * l;
        printf("%d\t-->\t%d\n", l, a);
        l = l + 1;
    }
    

    return 0;

}