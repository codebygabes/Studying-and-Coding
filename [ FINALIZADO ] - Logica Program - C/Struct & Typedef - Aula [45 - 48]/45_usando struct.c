#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <string.h>

// Struct para Musica

struct musica
{
    char titulo[50];
    char autor[50];
    int ano;
};

int buscarAntiga(PlayList);  //  Buscar a Musica + Antiga

int main () {

    struct musica PlayList[5];

    struct musica A = {"Velha Infancia", "Nando Reis", 2002};
    struct musica B = {"Aquarela", "Toquinho", 1983};
    struct musica C = {"Eu nasci ha 10 mil anos atras", "Raul Seixas", 1978};
    struct musica D = {"Tempo Perdido", "Renato Russo", 1986};
    struct musica E = {"E", "Gonzaguinha", 1986};

    PlayList[0] = A;
    PlayList[1] = B;
    PlayList[2] = C;
    PlayList[3] = D;
    PlayList[4] = E;

    for (int i = 0; i < 5; i++)
    {
        printf("AUTORE : %s\n", PlayList[i].autor);
        printf("MUSICA : %s\n", PlayList[i].titulo);
        printf("ANO : %d\n", PlayList[i].ano);
        printf("\n");
    } 
    
    return 0;
}
