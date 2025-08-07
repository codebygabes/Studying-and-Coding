#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdbool.h> // Para usar o tipo bool (true/false)

// Função para obter o valor da letra conforme a regra:
// a=1, ..., z=26 e A=27, ..., Z=52
int melhorValor(char c) {
    if (c >= 'a' && c <= 'z') {
        return c - 'a' + 1;
    } else if (c >= 'A' && c <= 'Z') {
        return c - 'A' + 27;
    }
    return 0; // Caso caractere não seja uma letra
}

// Função para verificar se um número é primo
// Regra 1: Os exemplos 1, 2, 3, 5, 17, 101, 1007 são primos.
// Regra 2: Para outros números : "possui somente dois divisores: ele mesmo e o número 1".
bool ePrimo(int n) {

    // Verifica os exemplos explícitos fornecidos no problema
    if (n == 1 || n == 2 || n == 3 || n == 5 || n == 17 || n == 101 || n == 1007) {
        return true;
    }

    // Para números não listados nos exemplos, aplicamos a definição de primo.
    if (n <= 1) { // Números menores ou iguais a ' 1 ' não são primos.
        return false;
    }

    // Se n é par e não é ' 2 ' não é primo.
    if (n % 2 == 0) {
        return false;
    }

    // (long long) : evitar "integer overflow" (estouro de inteiro);
    // (long long) : armazene números maiores do que um int.
    for (int i = 3; (long long)i * i <= n; i += 2) {
        if (n % i == 0) {
            return false; // Encontrou um divisor, não é primo
        }
    }

    return true; // Se não encontrou divisores, é primo

}

void gerarEnunciado() {

    printf("----------------------------------------------------\n");
    printf("        Verificador de Palavras Primas\n");
    printf("----------------------------------------------------\n");
    printf("Bem-vindo! Este programa verifica se uma palavra eh 'prima'.\n\n");
    printf("Regras para o valor das letras:\n");
    printf("  a = 1, b = 2, ..., z = 26\n");
    printf("  A = 27, B = 28, ..., Z = 52\n\n");
    printf("Definicao de Palavra Prima:\n");
    printf("  A soma dos valores das letras da palavra deve ser um numero primo.\n");
    printf("  Exemplos de numeros primos considerados: 1, 2, 3, 5, 17, 101, 1007.\n");
    printf("  Outros numeros sao primos se possuirem somente dois divisores: ele mesmo e 1.\n\n");
    printf("Como usar:\n");
    printf("  - Digite uma palavra e pressione Enter.\n");
    printf("  - Maximo de 20 letras por palavra.\n");
    printf("  - Para finalizar, digite EOF e pressione Enter.\n");
    printf("----------------------------------------------------\n\n");

}

int main() {
    char palavra[25];
    int numero = 1;
    int somaPalavra = 0;

    // Tabela/Instruções iniciais
    gerarEnunciado();
    
    // Loop para ler e processar palavras
    while (1) {
        printf("Digite a palavra %d (ou EOF para sair): ", numero);
        
        if (scanf("%s", palavra) != 1) { 
            printf("\nEntrada invalida ou fim de arquivo detectado. Finalizando.\n");
            break; 
        }

        if (strcmp(palavra, "EOF") == 0) {
            printf("Entrada 'EOF' recebida. Finalizando o programa.\n");
            break;
        }

        bool palavraValida = true;
        for (int i = 0; palavra[i] != '\0'; i++) {
            if (!((palavra[i] >= 'a' && palavra[i] <= 'z') || (palavra[i] >= 'A' && palavra[i] <= 'Z'))) {
                palavraValida = false;
                break;
            }
            somaPalavra += melhorValor(palavra[i]);
        }

        if (!palavraValida) {
            printf("A palavra '%s' contem caracteres invalidos. Use apenas letras de a-z ou A-Z.\n\n", palavra);
        } else {
             // Imprime o resultado no formato especificado no problema original
            if (ePrimo(somaPalavra)) {
                printf("%d) It is a prime word.\n", numero);
            } else {
                printf("%d) It is not a prime word.\n", numero);
            }
        }
        numero++;
    }

    system("cls");
    printf("----------------------------------------------------\n");
    printf("                Programa encerrado.\n");
    printf("----------------------------------------------------\n");

    return 0;
}