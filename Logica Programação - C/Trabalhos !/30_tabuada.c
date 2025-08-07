#include <stdio.h>
#include <stdlib.h>

int main () {

    int tabo;
    int i; 

    printf("diga um numero para a tabuada: ");
    scanf("%d", &tabo);

    for ( i = 1; i <= 10; i++)
    {
        printf("%d x %d = %d\n", tabo, i, tabo * i);
    }
    
    return 0;

}