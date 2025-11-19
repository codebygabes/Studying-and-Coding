    //  Exercicio = Criar & Abrir & Fechar um Arquivo.
#include <stdio.h>
#include <stdlib.h>

int main () {

    FILE * Arquivo;
    Arquivo = fopen("../teste/arquivo.txt", "w");

    if (Arquivo == NULL)
    {
        printf("ERRO : Nao foi Possivel abrir o arquivo.\n");
        exit(1);
    }

    fprintf(Arquivo, "O Flamengo foi eliminado da Copa do Brasil.");
    fclose(Arquivo);

    return 0;
}