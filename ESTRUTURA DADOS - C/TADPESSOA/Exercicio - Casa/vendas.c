#include <stdio.h>
#include <stdlib.h>
#include "vendas.h"

//  Função Pedida na Atividade
int somaQuantidadeVendas(const char* Vendas)
{
    FILE *arquivo;
    arquivo = fopen(Vendas, "r");

    if (arquivo == NULL)
    {
        //  stderr = standard error
        fprintf(stderr, "ERRO : Nao foi possivel abrir o arquivo '%s'\n", Vendas);
    }

    int soma_total = 0;
    char buffer[1024]; // Buffer pular cabeçalho

    if (fgets(buffer, sizeof(buffer), arquivo) == NULL)
    {
        //  Não Consegui Ler o Cabeçalho = Arquivo vazio ou com Erro
        fclose(arquivo);
        return 0;
    }

    //  Variaveis Temporarias para o Produto
    char produto[50];
    int quantidade;
    float preco;

    //  Lê o Arquivo linha por linha
    //  Retorno de 3 itens = linha tem formato esperado
    while (fscanf(arquivo, "%49[^,],%d,%f\n", produto, &quantidade, &preco) == 3)
    {
        soma_total += quantidade;
    }

    fclose(arquivo);
    return soma_total;
    
    
    
}