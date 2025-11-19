#include <stdio.h>
#include <string.h>
#include <stdlib.h>

void gerarTrianguloPascal(int n) 
{
    int triangulo[30][30];
    int i, j;

    // construindo o triângulo linha por linha
    for (i = 0; i <= n; i++)  // percorre as linhas do triângulo
    {
        for (j = 0; j <= i; j++)  // percorre as colunas de cada linha
        {
            if (j == 0 || j == i)  // verifica : primeira linha ou ultima coluna da linha
            {
                triangulo[i][j] = 1;  // as bordas sempre 1
            } else {
                // Calcula o valor da célula como a soma dos dois números acima dela
                triangulo[i][j] = triangulo[i - 1][j - 1] + triangulo[i - 1][j];
            }
            
            printf("%d   ", triangulo[i][j]); // espaço entre os números

        }
        
        printf("\n");  // quebra de linha para cada linha do triangulo

    } 
}

// Função para exibir o enunciado de boas-vindas
void gerarEnunciado()
{
    printf("----  ----  ----  ----  ----  ----  ----  ----\n");
    printf("   BEM VINDO/A AO -- TRIANGULO DE PASCAL !\n");
    printf("----  ----  ----  ----  ----  ----  ----  ----\n");
}

int main() {

    int linhas;
    char resposta;

    do  // Looping : fazer varios triangulos de pascal
    {
        system("cls");
        gerarEnunciado();

        do  // looping : garantir um numero entre 1 ate 30
        {
            printf("INSIRA UM NUMERO [1 -- 30]: ");
            scanf("%d", &linhas);

            if (linhas < 1 || linhas > 30)
            {
                printf("OPA! NUMERO INSERIDO ERRADO [1 -- 30]!\n");
            }
        } while (linhas < 1 || linhas > 30); {
            system("cls");
        }

        gerarEnunciado();
        gerarTrianguloPascal(linhas);
    
        
        printf("----  ----  ----  ----  ----  ----  ----  ----\n");
        printf("DESEJA VER OUTRO TRIANGULO DE PASCAL?\n");
        printf("INSIRA SUA RESPOSTA [S / N]: ");
        scanf(" %c", &resposta);

    } while (resposta == 's' || resposta == 'S'); 

    return 0;
}