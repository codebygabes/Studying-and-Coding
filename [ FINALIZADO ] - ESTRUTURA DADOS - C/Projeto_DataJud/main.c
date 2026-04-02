#include <stdio.h>
#include <stdlib.h>
#include "processo.h"

int main() {
    // const char* arquivo_csv = "TJDFT_amostra.csv";   --  Para Fazer Testes usando CSV de Amostra
    const char* arquivo_csv = "TJDFT_filtrado.csv";

    printf("=======================================================\n");
    printf("\t   ANALISE DE DADOS PROCESSUAIS - TJDFT\n");
    printf("=======================================================\n");
    printf("Arquivo sendo analisado: %s\n\n", arquivo_csv);

    printf("----- DADOS GERAIS -----\n\n");

    // Item 1: Contagem total de processos
    int total_processos = contar_processos(arquivo_csv);
    printf("[Item 01] - Numero total de processos: %d\n", total_processos);

    // Item 2: Processo com data de recebimento mais antiga
    int id_antigo = encontrar_processo_mais_antigo(arquivo_csv); 
    printf("[Item 02] - ID do processo mais antigo: %d\n", id_antigo); 

    // Item 3: Busca de Órgão Julgador por ID de Processo
    int id_alvo = id_antigo;
    int id_oj = obter_id_ultimo_oj(arquivo_csv, id_alvo);  
    printf("[Item 03] - Ultimo orgao julgador do processo %d: %d\n", id_alvo, id_oj);  

    printf("\n----- CONTAGEM PRO TEMA -----\n\n");

    // Itens 4-9: Funções de contagem 
    int total_violencia = contar_casos_violencia_domestica(arquivo_csv);
    printf("[Item 04] - Processos de violencia domestica: %d\n", total_violencia);
    int total_feminicidio = contar_casos_feminicidio(arquivo_csv);
    printf("[Item 05] - Processos de feminicidio: %d\n", total_feminicidio);
    int total_ambiental = contar_casos_ambientais(arquivo_csv);
    printf("[Item 06] - Processos ambientais: %d\n", total_ambiental);
    int total_quilombolas = contar_casos_quilombolas(arquivo_csv);
    printf("[Item 07] - Processos de quilombolas: %d\n", total_quilombolas);
    int total_indigenas = contar_casos_indigenas(arquivo_csv);
    printf("[Item 08] - Processos de indigenas: %d\n", total_indigenas);
    int total_infancia = contar_casos_infancia(arquivo_csv);
    printf("[Item 09] - Processos de infancia: %d\n", total_infancia);

    printf("\n----- CALCULOS / GERAR RQUIVOS -----\n\n");

    // Item 10: Cálculo de dias entre datas
    int dias = calcular_dias_entre_datas(arquivo_csv, 323961063);
    printf("[Item 10] - Diferenca entre as datas do processo 323961063: %d dias\n", dias);

    // Item 11: Cálculo da Meta 1
    double meta = calcular_cumprimento_meta1(arquivo_csv);
    printf("[Item 11] - Cumprimento da Meta 1: %.2f%%\n", meta);

    // Item 12: Geração do arquivo CSV
    const char* arquivo_csv_saida = "julgados_meta1.csv";
    gerar_csv_julgados_meta1(arquivo_csv, arquivo_csv_saida);

    printf("\n=======================================================\n");
    printf("      ANALISE DE DADOS PROCESSUAIS - CONCLUIDA\n");
    printf("=======================================================\n");

    return 0;
}