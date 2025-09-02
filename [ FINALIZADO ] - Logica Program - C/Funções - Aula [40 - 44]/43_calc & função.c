#include <stdio.h>
#include <stdlib.h>

#define ERRO -1

void exibirMenu () {  // VOID : APENAS EXIBIR UM CONTEUDO

    
    printf("\n---- ---- ---- ---- ---- ---- ---- ---- ---- ----\n");
    printf("Modulo de um calculo aritimetico simples!\n");
    printf("[ 1 ] -- Soma.\n");
    printf("[ 2 ] -- Subtracao.\n");
    printf("[ 3 ] -- Multiplicacao.\n");
    printf("[ 4 ] -- Divisao.\n");
    printf("[ 5 ] -- Resto da Divisao.\n");
    printf("[ 6 ] -- Encerrar a execucao deste aplicativo.\n");
    printf("\nESCOLHA UMA OPCAO: ");
   

}

int realizarOperacao (int Opcao, int Op1, int Op2) {

    int Resposta;

    switch (Opcao)
    {
    case 1:
        Resposta = Op1 + Op2;
        break;
    case 2:
        Resposta = Op1 - Op2;
        break;
    case 3:
        Resposta = Op1 * Op2;
        break;
    case 4:
        Resposta = Op1 / Op2;
        break;
    case 5:
        Resposta = Op1 % Op2;
        break;
    case 6: 
        printf("Encerrando . . .\n");
        exit(0);  // Terminar a Execução!
    
    default:
        Resposta = ERRO;
        printf("Digite um opcao valida!");
        break;
    }

    return Resposta;

}

int main () {

    int O;  // Operacao
    int A;  // Primeiro Numero
    int B;  // Segundo Numero
    int Res;  // Resposta, resultado

    while (1)  // Para repetir o processo infinitas vezes
    {
        exibirMenu();     

        scanf("%d", &O);  // Lendo a Operação!

        if (O != 6)
        {
            printf("Digite dois numeros: ");
            scanf("%d %d", &A, &B);
        }

        Res = realizarOperacao(O, A, B);
        printf("O Resultado e: %d", Res);
    }

    return 0;

}