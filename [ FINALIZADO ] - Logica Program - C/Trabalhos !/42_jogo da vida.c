#include <stdio.h>
#include <string.h>

#define tam 8 // Define o tamanho do tabuleiro (8x8)

// Função que conta vizinhos vivos em volta de (x, y)
int contarVizinhos(int tab[tam][tam], int x, int y) {

    // Vetores para verificar as 8 direções ao redor da célula
    int dx[] = {-1,-1,-1,0,0,1,1,1};
    int dy[] = {-1,0,1,-1,1,-1,0,1};
    int count = 0;

    for(int i = 0; i < 8; i++) {

        int nx = x + dx[i];
        int ny = y + dy[i];

         // Verifica se a posição vizinha está dentro dos limites do tabuleiro
        if (nx >= 0 && nx < tam && ny >= 0 && ny < tam) {
            if(tab[nx][ny] == 1) {
                count++;
            }
        }
    }
    return count;
}

int main () {

    int tab[tam][tam] = {0};      // Tabuleiro principal, tudo começa vazio (0)
    int novoTab[tam][tam];        // Tabuleiro temporário
    int N, lances;

    // Lê o número de peças iniciais
    printf("Digite o numero de pecas iniciais : ");
    scanf("%d", &N);

    // Lê a posição de cada peça inicial
    for(int i = 0; i < N; i++) {
        int x, y;
        printf("Digite a posicao da peca %d (linha e coluna): ", i+1);
        scanf("%d %d", &x, &y);
        tab[x][y] = 1;  // Marca a célula como viva
    }

    // Lê o número de lances (gerações a simular)
    printf("Digite o numero de lances : ");
    scanf("%d", &lances);

    // Para cada lance (rodada)
    for(int l = 0; l < lances; l++)
    {
        // Calcula a nova geração
        for(int i = 0; i < tam; i++)
        {
            for(int j = 0; j < tam; j++)
            {
                int vizinhos = contarVizinhos(tab, i, j); // Conta os vizinhos vivos

                if(tab[i][j] == 1)
                {
                    // Morre por solidão ou superpopulação
                    if(vizinhos < 2 || vizinhos > 3)
                    novoTab[i][j] = 0;      
                else
                    novoTab[i][j] = 1; // Sobrevive
                } else
                    {
                        // Casa vazia ganha peça se tiver exatamente 3 vizinhos
                        if(vizinhos == 3)
                        novoTab[i][j] = 1;
                    else
                        novoTab[i][j] = 0; // Continua vazia    
                    }
            }
        }

        // Atualiza o tabuleiro para a próxima rodada
        for(int i = 0; i < tam; i++)
        {
            for(int j = 0; j < tam; j++)
            {
                tab[i][j] = novoTab[i][j];
            }
        }
    }


    // Imprime o resultado final do tabuleiro
    printf("\nTABULEIRO FINAL :\n");

    for(int i = 0; i < tam; i++) 
    {
        for(int j = 0; j < tam; j++)
        {
            if(tab[i][j] == 1)
                printf("X "); // célula com peça viva
            else
                printf("- "); // célula vazia
        }
        printf("\n");
    }

    return 0;
}