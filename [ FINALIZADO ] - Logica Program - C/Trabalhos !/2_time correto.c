#include <stdio.h>
#include <string.h>   // Necessário para usar a função strcmp
#include <ctype.h>   // Para usar tolower
#include <locale.h>

int main() {

    setlocale(LC_ALL, "Portuguese");

    char Certo;
    char Errado;
    char resposta[50];

    Certo = 'C';
    Errado = 'E';

    printf("O valor da primeira variavel e: %c!\n", Certo);
    printf("E o da segunda e: %c!\n", Errado);
    

    while (1)
    {
        // Perguntar ao usuario
    printf("Qual melhor time do Brasil? ");
    fgets(resposta, sizeof(resposta), stdin);

    // Remova o caractere da nova linha
    resposta[strcspn(resposta, "\n")] = '\0';

    // Converter para minusculas
    for (int i = 0; resposta[i]; i++) {
        resposta[i] = tolower(resposta[i]);
    }

    // Verifique se esta correto
    if (strcmp(resposta, "palmeiras") == 0) {
        printf("Resposta Correta!\n");
        break;  // Sai do loop quando a resposta estiver correta
    } else {
        printf("Resposta Incorreta. Tente De Novo!\n");
    }
    }
    

    return 0;
}