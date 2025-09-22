#include <stdio.h>
#include <stdlib.h>
#include "processo.h"

int main() {
    
    // const char* arquivo_csv = "TJDFT_amostra.csv";
    const char* arquivo_csv = "TJDFT_filtrado.csv";

    printf("=======================================================\n");
    printf("\tANALISE DE DADOS PROCESSUAIS - TJDFT\n");
    printf("=======================================================\n");
    printf("Arquivo sendo analisado: %s\n\n", arquivo_csv);

    // --- EXECUÇÃO DAS FUNÇÕES DO TRABALHO ---

    // Item 1: Contagem total de processos
    int total_processos = contar_processos(arquivo_csv);
    printf("[Item 01] Numero total de processos: %d\n", total_processos);

    // Item 2: Processo com data de recebimento mais antiga
    long long id_antigo = encontrar_processo_mais_antigo(arquivo_csv);
    printf("[Item 02] ID do processo mais antigo: %lld\n", id_antigo);

    // Item 3: Busca de Órgão Julgador por ID de Processo
    // (Usando um ID que existe no arquivo de amostra como exemplo)
    long long id_alvo = id_antigo; 
    long long id_oj = obter_id_ultimo_oj(arquivo_csv, id_alvo);
    printf("[Item 03] Ultimo orgao julgador do processo %lld: %lld\n", id_alvo, id_oj);

    printf("\n--- Contagem por Tema ---\n");

    // Item 4: Contagem de processos de Violência Doméstica
    int total_violencia = contar_casos_violencia_domestica(arquivo_csv);
    printf("[Item 04] Processos de violencia domestica: %d\n", total_violencia);

    // Item 5: Contagem de processos de Feminicídio
    int total_feminicidio = contar_casos_feminicidio(arquivo_csv);
    printf("[Item 05] Processos de feminicidio: %d\n", total_feminicidio);

    // Item 6: Contagem de processos Ambientais
    int total_ambiental = contar_casos_ambientais(arquivo_csv);
    printf("[Item 06] Processos ambientais: %d\n", total_ambiental);

    // Item 7: Contagem de processos de Quilombolas
    int total_quilombolas = contar_casos_quilombolas(arquivo_csv);
    printf("[Item 07] Processos de quilombolas: %d\n", total_quilombolas);

    // Item 8: Contagem de processos de Indigenas
    int total_indigenas = contar_casos_indigenas(arquivo_csv);
    printf("[Item 08] Processos de indigenas: %d\n", total_indigenas);

    // Item 9: Contagem de processos de Infancia
    int total_infancia = contar_casos_infancia(arquivo_csv);
    printf("[Item 09] Processos de infancia: %d\n", total_infancia);

    printf("\n--- Calculos e Geracao de Arquivos ---\n");

    // Item 10: Exemplo de cálculo de dias entre duas datas
    int dias = calcular_dias_entre_datas("2023-01-15", "2023-06-20");
    printf("[Item 10] Diferenca entre as Datas (2023-01-15 a 2023-06-20): %d dias\n", dias);

    // Item 11: Cálculo do cumprimento da Meta 1
    double meta = calcular_cumprimento_meta1(arquivo_csv);
    printf("[Item 11] Cumprimento da Meta 1: %.2f%%\n", meta);

    // Item 12: Geração de arquivo CSV com processos julgados da Meta 1
    const char* arquivo_csv_saida = "julgados_meta1.csv";
    gerar_csv_julgados_meta1(arquivo_csv, arquivo_csv_saida);
    // (A própria função já imprime uma mensagem de sucesso)

    printf("\n=======================================================\n");
    printf("\t\tAANALISE CONCLUIDA.\n");
    printf("=======================================================\n");
    
    return 0;
}