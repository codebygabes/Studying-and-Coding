#ifndef VENDAS_H
#define vENDAS_h

//  Dados - TAD VENDAS : Produto,Quantidade,Preço
typedef struct
{
    char Produto[50];
    int Quantidade;
    float Preco;
} Vendas;

//  Operações - TAD VENDA
int somaQuantidadeVendas(const char* Vendas);
//  Podem ser Escritas Várias Outras Pessoas

#endif