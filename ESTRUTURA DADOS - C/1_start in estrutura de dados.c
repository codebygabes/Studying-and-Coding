#include <stdio.h>
#include <stdlib.h>
#include <math.h>

//  Implimentando o TAD Fração
typedef struct 
{
    int numerador;
    int denominador;
} fracao;

//  Protótipo das Funções
fracao criarFracao(int n, int d);
void exibirFracao(fracao f);
int calcularMDC(int a, int b);
fracao simplificarFracao(fracao f);
fracao somarFracoes(fracao f, fracao g);


int main()
{
    printf("A struct fracao ocupa, em memoria, %d bytes.\n", sizeof(fracao));
    printf("Double: %d bytes.\n", sizeof(double));
        //  sizeof : tamanho de ( ... )
    
    //  Criando Exemplos de Fração
    fracao f1 = criarFracao(3, 7);
    fracao f2 = criarFracao(16, 12);
    fracao f3 = criarFracao(-4, 6);
    fracao f4 = criarFracao(48, 72);

    //  Simplificando Exemplos de Fração
    f1 = simplificarFracao(f1);
    f2 = simplificarFracao(f2);
    f3 = simplificarFracao(f3);
    f4 = simplificarFracao(f4);

    //  Mostrando Exemplos de Fração
    exibirFracao(f1);
    exibirFracao(f2);
    exibirFracao(f3);
    exibirFracao(f4);

    //  Mostrando Exemplo de MDC na Fração
    printf("MDC : %d\n", calcularMDC(5, 7));
}

//  Desenvolvimento das Funções
fracao criarFracao(int n, int d)
{
    fracao f;
    f.numerador = n;
    f.denominador = d;
    return f;
}

void exibirFracao(fracao f)
{
    printf("%d / %d\n", f.numerador, f.denominador);
}

int calcularMDC(int a, int b)
{
    if (a < 0) a = -a;
    if (b < 0) b = -b;

    while (a % b != 0)
    {
        int temp = a;
        a = b;
        b = temp % b;
    }
    return b;
}

fracao simplificarFracao(fracao f)
{
    int MDC = calcularMDC(f.numerador, f.denominador);
    f.numerador = f.numerador / MDC;
    f.denominador = f.denominador / MDC;
    return f;
}

fracao somarFracoes(fracao f, fracao g)
{
    fracao resultado;
    resultado.denominador = f.denominador * g.denominador;
    resultado.numerador = f.numerador * g.numerador + g.numerador * f.numerador;
    resultado = simplificarFracao(resultado);
    return resultado;
}