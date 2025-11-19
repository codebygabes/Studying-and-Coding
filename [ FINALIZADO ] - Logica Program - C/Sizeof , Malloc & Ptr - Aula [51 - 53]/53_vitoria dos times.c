#include <stdio.h>
#include <stdlib.h>

typedef struct {

    char Nome[30];
    int P;
    int V;
    int E;
    int D;

} Time;

Time * lerTime();
void atualizarTime(Time * T, int V, int E, int D);

int main() {

    Time * X = lerTime();
    printf("O time %s, tem : %d pontos.\n", X ->Nome, X->P);
    atualizarTime(X, 13, 4, 7);
    printf("O time %s tem %d pontos. \n", X ->Nome, X ->P);
    return 0;
}

Time * lerTime()
{

    Time * T = (Time *) malloc(sizeof(Time));
    if (T == NULL)
    {
        printf("ERRO : nao foi possivel alocar memoria para o time!\n");
        return NULL;
    }
    
    printf("DADOS : ");
    scanf("%[^,], %d, %d, %d", T ->Nome, &T ->V, &T ->E, &T ->D);
    T->P = 3 * T->V + 1 * T ->E;
    return T;

    // Atualizar vitorias, empates e derrotas

    void atualizarTime (Time * T, int V, int E, int D){
        T->V = V;
        T->E = E;
        T->D = D;
        T->P = 3 * V + 1 * E;
    }

}