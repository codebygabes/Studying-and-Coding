#include <stdio.h>
#include <stdlib.h>
#include "vendas.h"

int main() {

    const char* Vendas = "dados_v.csv";

    printf("Calculando a Soma da Quantidade de Arquivo : %s\n", Vendas);

    //  Chame a função do TAD
    int total = somaQuantidadeVendas(Vendas);

    //  Verifica o Resultado
    if (total == -1)
    {
        printf("Ocorreu um ERRO ao processar o arquivo.\n");
        return 1; // Termina o Programa com um código de erro.
    } else {
        printf("A Soma Total da Quantidade é : [ %d ]\n", total);
    }

    return 0;
    
}