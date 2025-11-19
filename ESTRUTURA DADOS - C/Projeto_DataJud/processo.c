#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include "processo.h"

// ITEM 1: Conta o numero total de processos no arquivo CSV
int contar_processos(const char* nome_arquivo) {
    FILE *arquivo = fopen(nome_arquivo, "r");
    if (arquivo == NULL) {
        printf("Erro ao abrir arquivo: %s\n", nome_arquivo);
        return -1;
    }

    char linha[2048];  // Buffer para armazenar cada linha do arquivo
    int contador = 0;

    fgets(linha, sizeof(linha), arquivo);  // Pula o cabecalho

    // Conta cada linha (cada linha = 1 processo)
    while (fgets(linha, sizeof(linha), arquivo)) {
        contador++;
    }
    
    fclose(arquivo);
    return contador;
}

// ITEM 2: Encontra o processo com data de recebimento mais antiga
int encontrar_processo_mais_antigo(const char* nome_arquivo) {
    FILE* arquivo = fopen(nome_arquivo, "r");
    if(!arquivo) {
        printf("Erro ao abrir arquivo\n");
        return -1;
    }

    char linha[2048];
    fgets(linha, sizeof(linha), arquivo);  // Cabecalho

    char data_mais_antiga[11] = "";  // String para data no formato YYYY-MM-DD
    int id_mais_antigo = -1;
    int primeira_linha = 1;  // Flag para primeira linha de dados

    // Processa cada linha do arquivo
    while (fgets(linha, sizeof(linha), arquivo)) {
        char linha_copia[2048];
        strcpy(linha_copia, linha);  // Copia a linha para nao alterar o original

        // Divide a linha em campos usando ponto e virgula como delimitador
        char* campos[30];
        int i = 0;
        char* token = strtok(linha_copia, ";");  // Primeiro token
        
        // Extrai todos os campos da linha
        while(token != NULL && i < 30) {
            campos[i] = token;
            token = strtok(NULL, ";");  // Proximo token
            i++;
        }

        // Verifica se a linha tem campos suficientes
        if (i > 9) {
            int id_atual = atoi(campos[0]);  // Converte string para inteiro (ID)
            char* data_atual = campos[9];    // Data de recebimento (campo 10)

            // Primeira linha inicializa as variaveis
            if (primeira_linha) {
                strcpy(data_mais_antiga, data_atual);
                id_mais_antigo = id_atual;
                primeira_linha = 0;
            } else {
                // Compara datas para encontrar a mais antiga
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

// ITEM 3: Obtem o ID do ultimo orgao julgador para um processo especifico
int obter_id_ultimo_oj(const char* nome_arquivo, int id_processo_alvo) {
    FILE* arquivo = fopen(nome_arquivo, "r");
    if (!arquivo) {
        printf("Erro ao abrir arquivo\n");
        return -1;
    }

    char linha[2048];
    fgets(linha, sizeof(linha), arquivo);  // Cabecalho

    // Procura pelo processo com ID especificado
    while (fgets(linha, sizeof(linha), arquivo)) {
        char linha_copia[2048];
        strcpy(linha_copia, linha);

        // Extrai o ID do Processo (primeiro campo)
        char* token = strtok(linha_copia, ";");
        int id_atual = atoi(token);

        // Se encontrou o Processo Procurado
        if(id_atual == id_processo_alvo) {
            // Pula os proximos 8 campos para chegar no campo do orgao julgador
            for (int i = 0; i < 8; i++) {
                token = strtok(NULL, ";");
            }
            int id_oj = atoi(token);  // ID do orgao julgador
            fclose(arquivo);
            return id_oj;
        }
    }

    fclose(arquivo);
    return -1;  // Processo nao encontrado
}

// Funcao auxiliar para contar casos baseado em flag especifica
int contar_casos_especifico(const char* nome_arquivo, int indice_flag) {
    FILE* arquivo = fopen(nome_arquivo, "r");
    if (!arquivo) {
        printf("Erro ao abrir arquivo\n");
        return -1;
    }

    char linha[2048];
    fgets(linha, sizeof(linha), arquivo);  // Cabecalho

    int contador = 0;

    // Processa cada linha do arquivo
    while (fgets(linha, sizeof(linha), arquivo)) {
        // Remove caracteres de quebra de linha (\r\n) - importante para Windows
        linha[strcspn(linha, "\r\n")] = '\0';  // \0 = caractere nulo (fim da string)
        
        if (strlen(linha) == 0) continue;  // Pula linhas vazias

        int campo_atual = 0;
        char valor_campo[100] = "";
        int indice_campo = 0;
        int flag_encontrada = 0;

        // Processa cada caractere da linha para extrair campos
        for (int pos = 0; linha[pos] != '\0'; pos++) {
            if (linha[pos] == ';') {
                valor_campo[indice_campo] = '\0';  // Finaliza o campo atual
                
                // Verifica se este eh o campo da flag que procuramos
                if (campo_atual == indice_flag) {
                    // Se o campo tem valor 1, incrementa contador
                    if (strlen(valor_campo) > 0 && atoi(valor_campo) == 1) {
                        contador++;
                    }
                    flag_encontrada = 1;
                    break;  // Sai do loop pois ja encontrou a flag
                }
                
                campo_atual++;
                indice_campo = 0;  // Reseta para o proximo campo
            } else {
                valor_campo[indice_campo++] = linha[pos];  // Adiciona caractere ao campo
            }
        }

        // Processa o ultimo campo da linha (apos o ultimo ponto e virgula)
        if (!flag_encontrada && indice_campo > 0 && campo_atual == indice_flag) {
            valor_campo[indice_campo] = '\0';
            if (strlen(valor_campo) > 0 && atoi(valor_campo) == 1) {
                contador++;
            }
        }
    }

    fclose(arquivo);
    return contador;
}

// ITEM 4: Conta casos de Violência Doméstica (flag na posicao 11)
int contar_casos_violencia_domestica(const char* nome_arquivo) { 
    return contar_casos_especifico(nome_arquivo, 11);
}

// ITEM 5: Conta casos de Feminicídio (flag na posicao 12)
int contar_casos_feminicidio(const char* nome_arquivo) { 
    return contar_casos_especifico(nome_arquivo, 12);
}

// ITEM 6: Conta casos Ambientais (flag na posicao 13)
int contar_casos_ambientais(const char* nome_arquivo) { 
    return contar_casos_especifico(nome_arquivo, 13);
}

// ITEM 7: Conta casos Quilombolas (flag na posicao 14)
int contar_casos_quilombolas(const char* nome_arquivo) { 
    return contar_casos_especifico(nome_arquivo, 14);
}

// ITEM 8: Conta casos Indígenas (flag na posicao 15)
int contar_casos_indigenas(const char* nome_arquivo) { 
    return contar_casos_especifico(nome_arquivo, 15);
}

// ITEM 9: Conta casos de Infância (flag na posicao 16)
int contar_casos_infancia(const char* nome_arquivo) { 
    return contar_casos_especifico(nome_arquivo, 16);
}

// ITEM 10: Calcula diferenca em dias entre data de recebimento e resolucao
int calcular_dias_entre_datas(const char* nome_arquivo, int id_processo) {
    FILE* arquivo = fopen(nome_arquivo, "r");
    if (!arquivo) return -1;

    char linha[2048];
    fgets(linha, sizeof(linha), arquivo);  // Cabecalho

    char data_recebimento[11] = "";  // Data de recebimento
    char data_resolvido[11] = "";    // Data de resolucao

    // Procura pelo processo especifico
    while (fgets(linha, sizeof(linha), arquivo)) {
        char linha_copia[2048];
        strcpy(linha_copia, linha);

        // Extrai campos da linha
        char* campos[30];
        int i = 0;
        char* token = strtok(linha_copia, ";");
        
        while(token != NULL && i < 30) {
            campos[i] = token;
            token = strtok(NULL, ";");
            i++;
        }

        // Verifica se encontrou o processo procurado
        if (i > 18) {
            int id_atual = atoi(campos[0]);
            if (id_atual == id_processo) {
                strcpy(data_recebimento, campos[9]);   // Campo 10: data recebimento
                strcpy(data_resolvido, campos[18]);    // Campo 19: data resolucao
                break;
            }
        }
    }

    fclose(arquivo);

    // Verifica se as datas foram encontradas
    if (strlen(data_recebimento) == 0 || strlen(data_resolvido) == 0) {
        return -1;
    }

    // Estruturas para armazenar datas convertidas
    struct tm data_inicial_tm = {0};
    struct tm data_final_tm = {0};
    
    // Converte strings de data para estrutura tm
    sscanf(data_recebimento, "%d-%d-%d", 
           &data_inicial_tm.tm_year, 
           &data_inicial_tm.tm_mon, 
           &data_inicial_tm.tm_mday);
    
    sscanf(data_resolvido, "%d-%d-%d", 
           &data_final_tm.tm_year, 
           &data_final_tm.tm_mon, 
           &data_final_tm.tm_mday);

    // Ajusta valores para formato da biblioteca time
    data_inicial_tm.tm_year -= 1900;  // Ano comecava em 1900
    data_inicial_tm.tm_mon -= 1;      // Meses comecam em 0
    data_final_tm.tm_year -= 1900;
    data_final_tm.tm_mon -= 1;
    
    data_inicial_tm.tm_hour = 12;     // Define hora para evitar problemas de fuso
    data_final_tm.tm_hour = 12;

    // Converte para time_t (segundos desde 1970)
    time_t t_inicial = mktime(&data_inicial_tm);
    time_t t_final = mktime(&data_final_tm);

    if (t_inicial == -1 || t_final == -1) {
        return -1;
    }

    // Calcula diferenca em segundos e converte para dias
    double diferenca_segundos = difftime(t_final, t_inicial);
    return (int)(diferenca_segundos / (60 * 60 * 24));  // Segundos -> minutos -> horas -> dias
}

// ITEM 11: Calcula percentual de cumprimento da Meta 1
double calcular_cumprimento_meta1(const char* nome_arquivo) {
    FILE* arquivo = fopen(nome_arquivo, "r");
    if (!arquivo) {
        printf("Erro ao abrir arquivo\n");
        return -1.0;
    }

    char linha[2048];
    fgets(linha, sizeof(linha), arquivo);  // Cabecalho

    // Variaveis para acumular totais dos campos relevantes
    long total_cnm1 = 0, total_julgadom1 = 0, total_desm1 = 0, total_susm1 = 0;

    while (fgets(linha, sizeof(linha), arquivo)) {
        linha[strcspn(linha, "\r\n")] = '\0';  // Remove Quebra de Linha
        if (strlen(linha) == 0) continue;      // Pula Linhas Vazias
        
        char linha_copia[2048];
        strcpy(linha_copia, linha);

        int campo_atual = 0;
        char valor_campo[100] = "";
        int indice_campo = 0;
        double cnm1_val = 0, julgado_val = 0, desm1_val = 0, susm1_val = 0;

        // Processa cada campo da Linha
        for (int pos = 0; linha[pos] != '\0'; pos++) {
            if (linha[pos] == ';') {
                valor_campo[indice_campo] = '\0';
                
                // Campos da Meta 1: cnm1(19), julgadom1(24), desm1(25), susm1(26)
                if (campo_atual == 19) cnm1_val = atof(valor_campo);        // Converte para double
                if (campo_atual == 24) julgado_val = atof(valor_campo);
                if (campo_atual == 25) desm1_val = atof(valor_campo);
                if (campo_atual == 26) susm1_val = atof(valor_campo);
                
                campo_atual++;
                indice_campo = 0;
            } else {
                valor_campo[indice_campo++] = linha[pos];
            }
        }

        // Processa Último campo da Linha
        if (indice_campo > 0) {
            valor_campo[indice_campo] = '\0';
            if (campo_atual == 19) cnm1_val = atof(valor_campo);
            if (campo_atual == 24) julgado_val = atof(valor_campo);
            if (campo_atual == 25) desm1_val = atof(valor_campo);
            if (campo_atual == 26) susm1_val = atof(valor_campo);
        }

        // Acumula totais
        total_cnm1 += cnm1_val;
        total_julgadom1 += julgado_val;
        total_desm1 += desm1_val;
        total_susm1 += susm1_val;
    }
    
    fclose(arquivo);

    // Formula da Meta 1: julgadom1 / (cnm1 + desm1 - susm1) * 100
    long denominador = total_cnm1 + total_desm1 - total_susm1;
    if (denominador <= 0) {
        return 0.0;  // Evita divisao por zero
    }

    double cumprimento_meta = (double)total_julgadom1 / denominador * 100.0;
    return cumprimento_meta;
}

// ITEM 12: Gera arquivo CSV com processos julgados (julgadom1 >= 1)
void gerar_csv_julgados_meta1(const char* arq_entrada, const char* arq_saida) {
    FILE* entrada = fopen(arq_entrada, "r");
    if (!entrada) {
        printf("Erro ao abrir arquivo de entrada\n");
        return;
    }

    FILE* saida = fopen(arq_saida, "w");
    if (!saida) {
        printf("Erro ao criar arquivo de saida\n");
        fclose(entrada);
        return;
    }

    char linha[2048];
    fgets(linha, sizeof(linha), entrada);  // Cabecalho
    fputs(linha, saida);  // Copia cabecalho para saida

    int contador_julgados = 0;

    // Processa Cada Linha de Dados
    while (fgets(linha, sizeof(linha), entrada)) {
        linha[strcspn(linha, "\r\n")] = '\0'; // Remove Quebra de Linhas
        if (strlen(linha) == 0) continue;
        
        char linha_copia[2048];
        strcpy(linha_copia, linha);

        int campo_atual = 0;
        char valor_campo[100] = "";
        int indice_campo = 0;
        int julgado = 0;

        // Procura pelo campo julgadom1 (indice 24)
        for (int pos = 0; linha_copia[pos] != '\0'; pos++) {
            if (linha_copia[pos] == ';') {
                valor_campo[indice_campo] = '\0';
                
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

        // Se julgadom1 >= 1, inclui no arquivo de saida
        if (julgado >= 1) {
            fputs(linha, saida);
            fputs("\n", saida);
            contador_julgados++;
        }
    }

    printf("[ITEM 12] - Arquivo '%s' Gerado com ' %ld ' Processos Julgados!\n", arq_saida, contador_julgados);
    fclose(entrada);
    fclose(saida);
}