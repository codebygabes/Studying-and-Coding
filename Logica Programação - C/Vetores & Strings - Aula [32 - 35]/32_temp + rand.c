#include <stdio.h>
#include <stdlib.h>
#include <time.h> // Necessário para time()

int main() {

    float temp[10];
    float soma = 0.0;
    float media;
    int i;

    // Inicializa a semente para o gerador de números aleatórios
    srand(time(0));

    for ( i = 0; i < 10; i++)
    {
        // Gera uma temperatura aleatória entre 10 e 30 graus
        temp[i] = (rand() % 21) + 10; // rand() % 21 gera um número entre 0 e 20, somando 10 faz o intervalo ser entre 10 e 30

        // Exibe a temperatura gerada
        printf("Temperatura %d: %.2f\n", i + 1, temp[i]);
        soma = soma + temp[i];
    }

    media = soma / 10.0;
    printf("Media = Temperatura : %.2f\n", media);

    // Mostrar os dias em que a temperatura foi maior do que a media

    for ( i = 0; i < 10; i++)
    {
        if (temp[i] > media)
        {
            printf("%d ", i + 1);
        }
        
    }
        
    return 0;
    
}