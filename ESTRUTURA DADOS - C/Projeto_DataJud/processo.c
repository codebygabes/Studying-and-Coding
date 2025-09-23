#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include "processo.h"

// Implementação do ITEM 1: contar_processos
int contar_processos(const char* nome_arquivo) {
    FILE *arquivo = fopen(nome_arquivo, "r");
    if (arquivo == NULL) {
        perror("Erro ao Abrir o Arquivo");
        return -1;
    }

    int contador = 0;
    char linha_buffer[2048];

    fgets(linha_buffer, sizeof(linha_buffer), arquivo);

    while (fgets(linha_buffer, sizeof(linha_buffer), arquivo)) {
        contador++;
    }
    fclose(arquivo);
    return contador;
}

// Implementação do ITEM 2: Encontrar o id_processo do processo com dt_recebimento mais antigo
int encontrar_processo_mais_antigo(const char* nome_arquivo) {
    FILE* arquivo = fopen(nome_arquivo, "r");
    if(!arquivo) {
        perror("Erro ao abrir arquivo em encontrar_processo_mais_antigo");
        return -1;
    }

    char linha[2048];
    fgets(linha, sizeof(linha), arquivo);

    char data_mais_antiga[11] = "";
    int id_mais_antigo = -1;
    int primeira_linha = 1;

    while (fgets(linha, sizeof(linha), arquivo)) {
        char linha_copia[2048];
        strcpy(linha_copia, linha);

        char* campos[30];
        int i = 0;
        char* token = strtok(linha_copia, ";");
        while(token != NULL && i < 30) {
            campos[i] = token;
            token = strtok(NULL, ";");
            i++;
        }

        if (i > 9) {
            int id_atual = atoi(campos[0]);
            char* data_atual = campos[9];

            if (primeira_linha) {
                strcpy(data_mais_antiga, data_atual);
                id_mais_antigo = id_atual;
                primeira_linha = 0;
            } else {
                if (strcmp(data_atual, data_mais_antiga) < 0) {
                    strcpy(data_mais_antiga, data_atual);
                    id_mais_antigo = id_atual;
                }
            }
        }
    }

    fclose(arquivo);
    return id_mais_antigo;
}

// Implementação dp ITEM 3: Obter o id_ultimo_oj a partir de id_processo
int obter_id_ultimo_oj(const char* nome_arquivo, int id_processo_alvo) {
    FILE* arquivo = fopen(nome_arquivo, "r");
    if (!arquivo) {
        perror("Erro ao abrir arquivo em obter_id_ultimo_oj");
        return -1;
    }

    char linha[2048];
    fgets(linha, sizeof(linha), arquivo);

    while (fgets(linha, sizeof(linha), arquivo)) {
        char linha_copia[2048];
        strcpy(linha_copia, linha);

        char* token = strtok(linha_copia, ";");
        int id_atual = atoi(token);

        if(id_atual == id_processo_alvo) {
            for (int i = 0; i < 8; i++) {
                token = strtok(NULL, ";");
            }
            int id_oj = atoi(token);
            fclose(arquivo);
            return id_oj;
        }
    }

    fclose(arquivo);
    return -1;
}

// FUNÇÃO CORRIGIDA - Processamento manual igual ao código correto
int contar_casos_especifico(const char* nome_arquivo, int indice_flag) {
    FILE* arquivo = fopen(nome_arquivo, "r");
    if (!arquivo) {
        perror("Erro ao abrir arquivo");
        return -1;
    }

    char linha[2048];
    fgets(linha, sizeof(linha), arquivo); // cabeçalho

    long contador_registros = 0;
    int numero_linha = 0;

    while (fgets(linha, sizeof(linha), arquivo)) {
        numero_linha++;
        linha[strcspn(linha, "\r\n")] = '\0';
        
        if (strlen(linha) == 0) continue;

        int campo_atual = 0;
        int dentro_campo = 0;
        char valor_campo[100] = "";
        int indice_campo = 0;
        int flag_encontrada = 0;

        for (int pos = 0; linha[pos] != '\0'; pos++) {
            if (linha[pos] == ';') {
                valor_campo[indice_campo] = '\0';
                
                if (campo_atual == indice_flag) {
                    if (strlen(valor_campo) > 0 && atoi(valor_campo) == 1) {
                        contador_registros++;
                    }
                    flag_encontrada = 1;
                    break;
                }
                
                campo_atual++;
                indice_campo = 0;
                dentro_campo = 0;
            } else {
                valor_campo[indice_campo++] = linha[pos];
                dentro_campo = 1;
            }
        }

        if (!flag_encontrada && dentro_campo && campo_atual == indice_flag) {
            valor_campo[indice_campo] = '\0';
            if (strlen(valor_campo) > 0 && atoi(valor_campo) == 1) {
                contador_registros++;
            }
        }
    }

    fclose(arquivo);
    return (int)contador_registros;
}

// ITEM 4-9: Funções de contagem 
int contar_casos_violencia_domestica(const char* nome_arquivo) { 
    return contar_casos_especifico(nome_arquivo, 11);
}

int contar_casos_feminicidio(const char* nome_arquivo) { 
    return contar_casos_especifico(nome_arquivo, 12);
}

int contar_casos_ambientais(const char* nome_arquivo) { 
    return contar_casos_especifico(nome_arquivo, 13);
}

int contar_casos_quilombolas(const char* nome_arquivo) { 
    return contar_casos_especifico(nome_arquivo, 14);
}

int contar_casos_indigenas(const char* nome_arquivo) { 
    return contar_casos_especifico(nome_arquivo, 15);
}

int contar_casos_infancia(const char* nome_arquivo) { 
    return contar_casos_especifico(nome_arquivo, 16);
}

// ITEM 10: Calcular o número de dias entre duas datas
int calcular_dias_entre_datas(const char* nome_arquivo, int id_processo) {
    FILE* arquivo = fopen(nome_arquivo, "r");
    if (!arquivo) return -1;

    char linha[2048];
    fgets(linha, sizeof(linha), arquivo);

    char data_recebimento[11] = "";
    char data_resolvido[11] = "";

    while (fgets(linha, sizeof(linha), arquivo)) {
        char linha_copia[2048];
        strcpy(linha_copia, linha);

        char* campos[30];
        int i = 0;
        char* token = strtok(linha_copia, ";");
        while(token != NULL && i < 30) {
            campos[i] = token;
            token = strtok(NULL, ";");
            i++;
        }

        if (i > 18) {
        int id_atual = atoi(campos[0]);
        if (id_atual == id_processo) {
            strcpy(data_recebimento, campos[9]);
            strcpy(data_resolvido, campos[18]);
            break;
        }
    }

    }
    fclose(arquivo);

    if (strlen(data_recebimento) == 0 || strlen(data_resolvido) == 0) {
        return -1;
    }

    struct tm data_inicial_tm = {0};
    struct tm data_final_tm = {0};
    
    sscanf(data_recebimento, "%d-%d-%d", 
           &data_inicial_tm.tm_year, 
           &data_inicial_tm.tm_mon, 
           &data_inicial_tm.tm_mday);
    
    sscanf(data_resolvido, "%d-%d-%d", 
           &data_final_tm.tm_year, 
           &data_final_tm.tm_mon, 
           &data_final_tm.tm_mday);

    data_inicial_tm.tm_year -= 1900;
    data_inicial_tm.tm_mon -= 1;
    data_final_tm.tm_year -= 1900;
    data_final_tm.tm_mon -= 1;
    
    data_inicial_tm.tm_hour = 12;
    data_final_tm.tm_hour = 12;

    time_t t_inicial = mktime(&data_inicial_tm);
    time_t t_final = mktime(&data_final_tm);

    if (t_inicial == -1 || t_final == -1) {
        return -1;
    }

    double diferenca = difftime(t_final, t_inicial);
    return (int)(diferenca / (60 * 60 * 24));
}

// ITEM 11: Calcular Meta 1 - ÍNDICES CORRETOS
double calcular_cumprimento_meta1(const char* nome_arquivo) {
    FILE* arquivo = fopen(nome_arquivo, "r");
    if (!arquivo) {
        perror("Erro ao abrir arquivo em calcular_cumprimento_meta1");
        return -1.0;
    }

    char linha[2048];
    fgets(linha, sizeof(linha), arquivo);

    long total_cnm1 = 0, total_julgadom1 = 0, total_desm1 = 0, total_susm1 = 0;

    while (fgets(linha, sizeof(linha), arquivo)) {
        linha[strcspn(linha, "\r\n")] = '\0';
        if (strlen(linha) == 0) continue;
        
        char linha_copia[2048];
        strcpy(linha_copia, linha);

        // PROCESSAMENTO MANUAL PARA GARANTIR ÍNDICES CORRETOS
        int campo_atual = 0;
        char valor_campo[100] = "";
        int indice_campo = 0;
        double cnm1_val = 0, julgado_val = 0, desm1_val = 0, susm1_val = 0;

        for (int pos = 0; linha[pos] != '\0'; pos++) {
            if (linha[pos] == ';') {
                valor_campo[indice_campo] = '\0';
                
                // ÍNDICES CORRETOS baseados na estrutura do CSV:
                // cnm1 (índice 19), julgadom1 (índice 24), desm1 (índice 25), susm1 (índice 26)
                if (campo_atual == 19) cnm1_val = atof(valor_campo);
                if (campo_atual == 24) julgado_val = atof(valor_campo);
                if (campo_atual == 25) desm1_val = atof(valor_campo);
                if (campo_atual == 26) susm1_val = atof(valor_campo);
                
                campo_atual++;
                indice_campo = 0;
            } else {
                valor_campo[indice_campo++] = linha[pos];
            }
        }

        // Processar último campo
        if (indice_campo > 0) {
            valor_campo[indice_campo] = '\0';
            if (campo_atual == 19) cnm1_val = atof(valor_campo);
            if (campo_atual == 24) julgado_val = atof(valor_campo);
            if (campo_atual == 25) desm1_val = atof(valor_campo);
            if (campo_atual == 26) susm1_val = atof(valor_campo);
        }

        total_cnm1 += cnm1_val;
        total_julgadom1 += julgado_val;
        total_desm1 += desm1_val;
        total_susm1 += susm1_val;
    }
    fclose(arquivo);

    printf("DEBUG Meta1: cnm1=%ld, julgadom1=%ld, desm1=%ld, susm1=%ld\n", 
           total_cnm1, total_julgadom1, total_desm1, total_susm1);

    long denominador = total_cnm1 + total_desm1 - total_susm1;
    if (denominador <= 0) {
        return 0.0;
    }

    double cumprimento_meta = (double)total_julgadom1 / denominador * 100.0;
    return cumprimento_meta;
}

// ITEM 12: Gerar CSV com processos julgados - CRITÉRIO CORRETO
void gerar_csv_julgados_meta1(const char* arq_entrada, const char* arq_saida) {
    FILE* entrada = fopen(arq_entrada, "r");
    if (!entrada) {
        perror("Erro ao abrir arquivo de entrada");
        return;
    }

    FILE* saida = fopen(arq_saida, "w");
    if (!saida) {
        perror("Erro ao criar arquivo de saida");
        fclose(entrada);
        return;
    }

    char linha[2048];
    fgets(linha, sizeof(linha), entrada); // cabeçalho
    fputs(linha, saida);

    long contador_julgados = 0;

    while (fgets(linha, sizeof(linha), entrada)) {
        linha[strcspn(linha, "\r\n")] = '\0';
        if (strlen(linha) == 0) continue;
        
        char linha_copia[2048];
        strcpy(linha_copia, linha);

        // PROCESSAMENTO MANUAL PARA IDENTIFICAR julgadom1
        int campo_atual = 0;
        char valor_campo[100] = "";
        int indice_campo = 0;
        int julgado = 0;

        for (int pos = 0; linha_copia[pos] != '\0'; pos++) {
            if (linha_copia[pos] == ';') {
                valor_campo[indice_campo] = '\0';
                
                // julgadom1 está no índice 24
                if (campo_atual == 24) {
                    julgado = atoi(valor_campo);
                    break;
                }
                
                campo_atual++;
                indice_campo = 0;
            } else {
                valor_campo[indice_campo++] = linha_copia[pos];
            }
        }

        // CRITÉRIO CORRETO: julgadom1 >= 1
        if (julgado >= 1) {
            fputs(linha, saida);
            fputs("\n", saida);
            contador_julgados++;
        }
    }

    printf("[Item 12] Arquivo '%s' gerado com %ld processos julgados!\n", arq_saida, contador_julgados);
    fclose(entrada);
    fclose(saida);
}

