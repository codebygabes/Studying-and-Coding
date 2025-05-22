#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <string.h>

// Criar uma struct chamada retângulo
// Fora Da Main = Pode Usar em Todo o Codigo

struct Retangulo
{
    float base;
    float altura;
};

// Prototipo das Funções

float calcularArea(struct Retangulo X);  //  Calcular Área Retangulo 'X'
float calcularPerimetro(struct Retangulo X);  //  Calcular Perimetro Retangulo 'X'
float calcularDiagonal(struct Retangulo X);  //  Calcular Diagonal Retangulo 'X'
char * verificarQuadrado(struct Retangulo Z);  //  Verificar Retangulo [ Quadrado Ou Não ] 
/* int verificarQuadrado_v2(struct Retangulo Z);  //  Verificar Retangulo [ Quadrado Ou Não ] */

int main() {

    struct Retangulo ABCD = {5, 3};      //  Declarando um Retangulo de "Nome" ABCD com Base 6 e Altura 3
    struct Retangulo EFGH = {1, 7};      //  A Base = 1 | A Altura = 7
    struct Retangulo PQRS = {10, 10};    //  A Base & A Altura = 10
    

    printf("----  INFORMACOES PRINCIPAIS !  ----\n");
    printf("Base : %.2f\n", ABCD.base);
    printf("Altura : %.2f\n", ABCD.altura);

    printf("\n");

    printf("----  AREA DOS RETANGULOS !  ----\n");
    printf("Area Retangulo ABCD : %.2f\n", calcularArea(ABCD));
    printf("Area Retangulo EFGH : %.2f\n", calcularArea(EFGH));
    printf("Area Retangulo PQRS : %.2f\n", calcularArea(PQRS));
    
    printf("\n");

    printf("----  PERIMETRO DOS RETANGULOS !  ----\n");
    printf("Perimetro Retangulo ABCD : %.2f\n", calcularPerimetro(ABCD));
    printf("Perimetro Retangulo EFGH : %.2f\n", calcularPerimetro(EFGH));
    printf("Perimetro Retangulo PQRS : %.2f\n", calcularPerimetro(PQRS));
    
    printf("\n");

    printf("----  DIAGONAL DOS RETANGULOS !  ----\n");
    printf("Diagonal Retangulo ABCD : %.2f\n", calcularDiagonal(ABCD));
    printf("Diagonal Retangulo EFGH : %.2f\n", calcularDiagonal(EFGH));
    printf("Diagonal Retangulo PQRS : %.2f\n", calcularDiagonal(PQRS));

    printf("\n");

    printf("----  VERIFICAR OS RETANGULOS !  ----\n");
    printf("ABCD e Quadrado ? %s\n", verificarQuadrado(ABCD));
    printf("EFGH e Quadrado ? %s\n", verificarQuadrado(EFGH));
    printf("PQRS e Quadrado ? %s\n", verificarQuadrado(PQRS));
    

    return 0;
}

// Implementação das Funções

float calcularArea(struct Retangulo X) {
    float area = X.base * X.altura;
    return area;

}

float calcularPerimetro(struct Retangulo X) {
    float perimetro = 2 * (X.base + X.altura);
    return perimetro;

}

char * verificarQuadrado(struct Retangulo Z) {  //  char * = string qualquer tamanho
    static resposta[4];

    if(Z.base == Z.altura)
    {
        strcpy(resposta, "Sim");
    } else {
        strcpy(resposta, "Nao");
    }

    return resposta;

}

/* // Segunda Forma de Verificar :
int verificarQuadrado_v2(struct Retangulo Z) {  //  char * = string qualquer tamanho
    
    if (Z.base == Z.altura) return 1;  //  1 = "SIM"
    else return 0;  //  0 = "NÃO"
} */

float calcularDiagonal(struct Retangulo X) {
    return sqrt(X.base * X.base + X.altura * X.altura);

}