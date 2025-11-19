#include <stdio.h>
#include <stdlib.h>
#include "fracao.h"

int main () {

    Fracao F1 = criarFracao(21, 27);
    Fracao F2 = criarFracao(20, 30);

    F1 = simplificarFracao(F1);
    F2 = simplificarFracao(F2);

    Fracao F3 = somarFracoes(F1, F2);
    exibirFracao(F3);

}