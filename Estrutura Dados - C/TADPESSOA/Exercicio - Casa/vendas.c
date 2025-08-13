#include <stdio.h>
#include <stdlib.h>
#include "vendas.h"

Vendas atualizarEstoque(Vendas V)
{
    V.Quantidade = V.Quantidade + 1;
    return V;
}