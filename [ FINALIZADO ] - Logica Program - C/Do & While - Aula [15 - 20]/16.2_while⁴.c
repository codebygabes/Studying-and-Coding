#include <stdio.h>
#include <stdlib.h>

int main() {

    int n = 8;
    int mult = 1;

    while (n >= 1)
    {
        mult = mult * n;
        n--; // é igual a n = n - 1
    }

    printf("%d\n", mult);
   
}