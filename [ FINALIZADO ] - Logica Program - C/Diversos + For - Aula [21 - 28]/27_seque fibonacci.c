#include <stdio.h>
#include <stdlib.h>

int main() {

    int n, i;
    int t1 = 0, t2 = 1, proximo;
    int primeiro = 0;

    printf("QUANTIDADE TERMOS PARA A SEQUENCIA: ");
    scanf(" %d", &n);

    // Verifica se o número é válido
    if (n <= 0)
    {
        printf("DIGITE UM NUMERO MAIOR QUE ZERO!");
        return 1;
    }

    // Exibe os primeiros N termos da sequência de Fibonacci
    printf("SEQUENCIA DE FIBONACCI:\n");

    for (i = 0; i < n; i++)
    {
        if (primeiro) {
            printf(", ");
        }
        printf("%d", t1);
        primeiro = 1; // Depois do primeiro número, ativa a flag

        proximo = t1 + t2; // Próximo termo é a soma dos dois anteriores
        t1 = t2;           // Atualiza t1 para o próximo cálculo
        t2 = proximo;      // Atualiza t2 para o próximo cálculo
    }
    
    return 0;
    


}