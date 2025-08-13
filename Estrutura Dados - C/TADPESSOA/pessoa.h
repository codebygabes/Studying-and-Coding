
#ifndef PESSOA_H
#define PESSOA_H

//  Dados - TAD PESSOA : ID,Nome,Idade
typedef struct
{
    int ID;
    char Nome[50];
    int Idade;
} Pessoa;

//  Operações - TAD PESSOA
int inserirPessoa(Pessoa P);  //  0 = SUCESSO & 1 = ERRO
Pessoa alterarIdade(Pessoa P);
void excluirPessoa(Pessoa P);
Pessoa alterarIdade_V2(Pessoa P, int I);

//  Podem ser Escritas Várias Outras Pessoas

#endif