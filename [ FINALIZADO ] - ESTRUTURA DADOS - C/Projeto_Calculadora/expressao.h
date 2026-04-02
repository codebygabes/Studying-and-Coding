#ifndef EXPRESSAO_H
#define EXPRESSAO_H

typedef struct {
    char posFixa[512];   // Expressão na forma pos-fixa, ex: "3 12 4 + *"
    char inFixa[512];    // Expressão na forma infixa, ex: "3*(12+4)"
    float Valor;         // Valor numérico da expressão
} Expressao;

// Converte de pós-fixa para infixa
char* getFormaInFixa(char* StrPosFixa);
    
// Calcula o valor da expressão pós-fixa
float getValorPosFixa(char* StrPosFixa);

#endif
