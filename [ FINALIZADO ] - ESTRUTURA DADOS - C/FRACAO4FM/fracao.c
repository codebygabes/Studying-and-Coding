
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include "fracao.h"

//  Desenvolvimento das Funções
Fracao criarFracao(int N, int D)
{
    Fracao F;
    F.Num = N;
    F.Den = D;
    return F;
}

void exibirFracao(Fracao F)
{
    printf("%d / %d\n", F.Num, F.Den);
}

int calcularMDC(int A, int B)
{
    if (A < 0) A = -A;
    if (B < 0) B = -B;

    while (A % B != 0)
    {
        int Temp = A;
        A = B;
        B = Temp % B;
    }
    return B;
}

Fracao simplificarFracao(Fracao F)
{
    int MDC = calcularMDC(F.Num, F.Den);
    F.Num = F.Num / MDC;
    F.Den = F.Den / MDC;
    return F;
}

Fracao somarFracoes(Fracao F, Fracao G)
{
    Fracao Resultado;
    Resultado.Den = F.Den * G.Den;
    Resultado.Num = F.Num * G.Num + G.Num * F.Num;
    Resultado = simplificarFracao(Resultado);
    return Resultado;
}