#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct
{
    char nome [60];
    char dataN [20];
    char CPF [15];
    char escolaridade [20];
    float salario;
} pessoa;

void descrever(pessoa P);

int main() {

    pessoa x;
    printf("Pessoa ocupa %d bytes. \n", sizeof(x));

    strcpy(x.nome, "Cristiano Ronaldo dos Santos Aveio");
    strcpy(x.dataN, "05/02/1985");
    strcpy(x.CPF, "012.345.678-07");
    strcpy(x.escolaridade, "Fundamental Completo");
    x.salario = 300000000.00;

    descrever(x);
}

// void : a fução nao retorna valor.
void descrever(pessoa P) {

    printf("%s, nasceu no dia : %s \n", P.nome, P.dataN);
    printf("%s, tem salario anual de : %.2f \n", P.nome, P.salario);
    printf("Ademais, tem : %s. e e dententor do CPF : %s \n", P.escolaridade, P.CPF);

}
