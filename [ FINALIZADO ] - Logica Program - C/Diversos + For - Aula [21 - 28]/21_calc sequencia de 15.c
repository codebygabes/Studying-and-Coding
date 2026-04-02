#include <stdio.h>
#include <stdlib.h>

int main () {

    system("cls");
    
    int soma = 0;
    int i;
    
    for (i = 1; i <= 15; i++) soma = soma + i * i;
    printf("1² + 2² + 3² + 4² + . . . + 15² = %d.\n", soma);
    
    return 0;

}