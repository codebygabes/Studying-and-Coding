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
pessoa lerDados();

int main() {

    pessoa x = lerDados();
    printf("Pessoa ocupa %d bytes. \n", sizeof(x));
    descrever(x);
    return 0;
}

// void : a fução nao retorna valor.
void descrever(pessoa P) {

    printf("%s, nasceu no dia : %s \n", P.nome, P.dataN);
    printf("%s, tem salario anual de : %.2f \n", P.nome, P.salario);
    printf("Ademais, tem : %s. E e dententor do CPF : %s \n", P.escolaridade, P.CPF);

}

pessoa lerDados() {

    // Ordem dos dados : Nome, Data de Nascimento, Salario, Escolaridade, CPF
    // Cristiano Ronaldo dos Santos Aveio, 05/03/1985, 3000000.00, Fundamental Completo, 012.345.678-07

    pessoa P;
    // %[^,] : ler ate encontrar : ,
    // %[^\n] : ler até encontrar : \n ( enter )
    scanf("%[^,], %[^,], %f, %[^,], %[^\n]",
            P.nome,
            P.dataN,
            &P.salario,
            P.escolaridade,
            P.CPF
    );

    return P;

}