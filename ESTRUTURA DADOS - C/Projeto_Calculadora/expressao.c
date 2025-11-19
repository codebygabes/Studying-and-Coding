#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <ctype.h>
#include "expressao.h"

#ifndef M_PI
#define M_PI 3.14159265358979323846
#endif

// Estrutura da pilha para strings
typedef struct Nodo {
    char str[512];
    struct Nodo *prox;
} Nodo;

// Estrutura da pilha para floats
typedef struct NodoF {
    float val;
    struct NodoF *prox;
} NodoF;

// Funções da pilha para strings
static void push(Nodo **topo, const char* s) {
    Nodo *novo = malloc(sizeof(Nodo));
    strcpy(novo->str, s);
    novo->prox = *topo;
    *topo = novo;
}

static char* pop(Nodo **topo) {
    if (!*topo) return NULL;
    Nodo *t = *topo;
    char *res = malloc(strlen(t->str)+1);
    strcpy(res, t->str);
    *topo = t->prox;
    free(t);
    return res;
}

// Funções da pilha para floats
static void pushF(NodoF **topo, float val) {
    NodoF *novo = malloc(sizeof(NodoF));
    novo->val = val;
    novo->prox = *topo;
    *topo = novo;
}

static float popF(NodoF **topo) {
    if (!*topo) return 0.0f; // Retorna 0 se a pilha estiver vazia
    NodoF *t = *topo;
    float v = t->val;
    *topo = t->prox;
    free(t);
    return v;
}

// Calcula valor da expressão pós-fixa
float getValorPosFixa(char* StrPosFixa) {
    NodoF *pilha = NULL;
    char *token = strtok(StrPosFixa, " ");
    while (token) {
        if (isdigit(token[0]) || (token[0] == '-' && isdigit(token[1]))) {
            pushF(&pilha, atof(token));
        } else if (strcmp(token, "raiz") == 0) {
            float a = popF(&pilha);
            pushF(&pilha, sqrtf(a));
        } else if (strcmp(token, "sen") == 0) {
            float a = popF(&pilha);
            pushF(&pilha, sinf(a * M_PI / 180.0f));
        } else if (strcmp(token, "cos") == 0) {
            float a = popF(&pilha);
            pushF(&pilha, cosf(a * M_PI / 180.0f));
        } else if (strcmp(token, "tg") == 0) {
            float a = popF(&pilha);
            pushF(&pilha, tanf(a * M_PI / 180.0f));
        } else if (strcmp(token, "log") == 0) {
            float a = popF(&pilha);
            pushF(&pilha, log10f(a));
        } else {
            float b = popF(&pilha), a = popF(&pilha), res = 0.0;
            switch (token[0]) {
                case '+': res = a + b; break;
                case '-': res = a - b; break;
                case '*': res = a * b; break;
                case '/': res = a / b; break;
                case '^': res = powf(a, b); break;
            }
            pushF(&pilha, res);
        }
        token = strtok(NULL, " ");
    }
    float resultado = popF(&pilha);
    return resultado;
}

// Converte pós-fixa para infixa (simplificado)
char* getFormaInFixa(char* str) {
    Nodo* pilha = NULL;
    char s[512], *tok = strtok(str, " ");
    while (tok) {
        if(isdigit(tok[0]) || (tok[0]=='-' && isdigit(tok[1]))) {
            push(&pilha, tok);
        } else if (strcmp(tok, "+")==0 || strcmp(tok, "-")==0 ||
                   strcmp(tok, "*")==0 || strcmp(tok, "/")==0 ||
                   strcmp(tok, "^")==0) {
            char *b=pop(&pilha), *a=pop(&pilha);
            if (!a || !b) {
                if (a) free(a);
                if (b) free(b);
                return NULL;
            }
            snprintf(s, sizeof(s), "(%s%s%s)", a, tok, b);
            free(a), free(b);
            push(&pilha,s);
        } else if (strcmp(tok, "log")==0 || strcmp(tok, "sen")==0 ||
                   strcmp(tok, "cos")==0 || strcmp(tok, "tg")==0 ||
                   strcmp(tok, "raiz")==0) {
            char* x = pop(&pilha);
            if(!x) return NULL;
            snprintf(s, sizeof(s), "%s(%s)", tok, x);
            free(x);
            push(&pilha, s);
        } else {
            return NULL;
        }
        tok = strtok(NULL, " ");
    }
    char* ret = pop(&pilha);
    static char result[512];
    if (ret) {
        strncpy(result, ret, 511);
        result[511]=0;
        free(ret);
        return result;
    } else {
        return NULL;
    }
}