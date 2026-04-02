#include <stdio.h>
#include <stdlib.h>
#include "pessoa.h"

Pessoa alterarIdade(Pessoa P)
{
    P.Idade = P.Idade + 1;
    return P;
}

Pessoa alterarIdade_V2(Pessoa P, int I)
{
    P.Idade = I;
    return P;
}