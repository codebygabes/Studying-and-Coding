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
int inserirVendas(Vendas V);  //  0 = SUCESSO & 1 = ERRO
Vendas atualizarEstoque(Vendas V);
void excluirProduto(Vendas V);

//  Podem ser Escritas Várias Outras Pessoas

#endif