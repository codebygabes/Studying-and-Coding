
// Arquivo de cabeçalhos ( onde será definido o TAD Fração )

#ifndef FRACAO_H    //  Existe = Constante FRACAO_H ?
#define FRACAO_H

// Struct = Caracteriza Fração
typedef struct
{
    int Num;
    int Den;
} Fracao;

// Protótipos das Funções
void exibirFracao(Fracao F);
int calcularMDC(int A, int B);
Fracao simplificarFracao(Fracao F);
Fracao criarFracao(int N, int D);
Fracao somarFracoes(Fracao F, Fracao G);
Fracao subtrairFracoes(Fracao F, Fracao G);

#endif