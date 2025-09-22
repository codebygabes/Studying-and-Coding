#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include "processo.h" // Inclui as definições de processo.h

// Implementação do ITEM 1: contar_processos
int contar_processos(const char* nome_arquivo) {
    FILE *arquivo = fopen(nome_arquivo, "r");
    if (arquivo == NULL) {
       /* 
            perror é uma função que imprime uma mensagem de erro que combina uma 
            mensagem sua com a mensagem de erro específica do sistema operacional.
       */
        perror("Erro ao Abrir o Arquivo");
        return -1; // Sinaliza um Erro
    }

    int contador = 0;
    char linha_buffer[2048]; // Um espaço para ler cada linha

    // Lê e descarta a primeira linha (cabeçalho)
    fgets(linha_buffer, sizeof(linha_buffer), arquivo);

    // Loop: enquanto conseguir ler uma nova linha, incrementa o contador
    while (fgets(linha_buffer, sizeof(linha_buffer), arquivo))
    {
        contador++;
    }
    fclose(arquivo);
    return contador;
}


// Implementação do ITEM 2: Encontrar o id_processo do processo com dt_recebimento mais antigo
long long encontrar_processo_mais_antigo(const char* nome_arquivo) {
    FILE* arquivo = fopen(nome_arquivo, "r");
    if(!arquivo) {
        perror("Erro ao abrir arquivo em encontrar_processo_mais_antigo");
        return -1;
    }

    char linha[2048];
    fgets(linha, sizeof(linha), arquivo); // Pular o Cabeçalho

    char data_mais_antiga[11] = "";
    long long id_mais_antigo = -1;
    int primeira_linha = 1;

    while (fgets(linha, sizeof(linha), arquivo)) {
        char linha_copia[2048];
        strcpy(linha_copia, linha); // strcpy Copia o conteúdo de uma string de uma origem para um destino.

        char* campos[30];
        int i = 0;
        char* token = strtok(linha_copia, ";"); // strtok Quebra (divide) uma string em pedaços menores (tokens) usando um delimitador.
        while(token != NULL && i < 30) {
            campos[i] = token;
            token = strtok(NULL, ";");
            i++;
        }

        if (i > 9) {
            long long id_atual = atoll(campos[0]);
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
long long obter_id_ultimo_oj(const char* nome_arquivo, long long id_processo_alvo) {
    FILE* arquivo = fopen(nome_arquivo, "r");
    if (!arquivo) {
        perror("Erro ao abrir arquivo em obter_id_ultimo_oj");
        return -1;
    }

    char linha[2048];
    fgets(linha, sizeof(linha), arquivo); // Pular o Cabeçalho

    while (fgets(linha, sizeof(linha), arquivo)) {
        char linha_copia[2048];
        strcpy(linha_copia, linha);

        // Pega o primeiro campo (id_processo)
        char* token = strtok(linha_copia, ";");
        long long id_atual = atoll(token);

        if(id_atual == id_processo_alvo) {
           // Agora pegamos o id_ultimo_oj
           for (int i = 0; i < 8; i++) {
                token = strtok(NULL, ";");
           }
           long long id_oj = atoll(token);
           fclose(arquivo);
           return id_oj; // Retorna assim que encontra
        }
    }

    fclose(arquivo);
    return -1; // Retorna -1 se não encontrou o ID no arquivo todo
}


// ITEM 4: Contar processos de violência doméstica
int contar_casos_violencia_domestica(const char* nome_arquivo) {
    FILE* arquivo = fopen(nome_arquivo, "r");
    if (!arquivo) {
        perror("Erro ao abrir arquivo em contar_casos_violencia_domestica");
        return -1;
    }

    char linha[2048];
    fgets(linha, sizeof(linha), arquivo); // Pular o Cabeçalho

    int contador = 0;
    while (fgets(linha, sizeof(linha), arquivo)) {
        char linha_copia[2048];
        strcpy(linha_copia, linha); // Copiar a linha para não sumir a original com strtok

        // O campo 'flag_violencia_domestica' é a 12ª coluna.
        // Vamos usar strtok para chegar até lá.
        char* token = strtok(linha_copia, ";");
        for (int i = 0; i < 11; i++) {
            token = strtok(NULL, ";");
        }

        if (token != NULL) {
            int flag = atoi(token); // Converte o texto ("0" ou "1") para número
            if (flag == 1) {
                contador++;
            }
        }
    }

    fclose(arquivo);
    return contador;
}


// ITEM 5: Contar processos de feminicídio
int contar_casos_feminicidio(const char* nome_arquivo) {
    FILE* arquivo = fopen(nome_arquivo, "r");
    if (!arquivo) {
        perror("Erro ao abrir arquivo em contar_casos_feminicidio");
        return -1;
    }

    char linha[2048];
    fgets(linha, sizeof(linha), arquivo); // Pular o Cabeçalho

    int contador = 0;
    while (fgets(linha, sizeof(linha), arquivo)) {
        char linha_copia[2048];
        strcpy(linha_copia, linha); // Copiar a linha para não sumir a original com strtok

        // O campo 'flag_feminicidio' é a 13ª coluna.
        // Vamos usar strtok para chegar até lá.
        char* token = strtok(linha_copia, ";");
        for (int i = 0; i < 12; i++) {
            token = strtok(NULL, ";");
        }

        if (token != NULL) {
            int flag = atoi(token); // Converte o texto ("0" ou "1") para número
            if (flag == 1) {
                contador++;
            }
        }
    }

    fclose(arquivo);
    return contador;
}


// ITEM 6: Contar processos ambientais
int contar_casos_ambientais(const char* nome_arquivo) {
    FILE* arquivo = fopen(nome_arquivo, "r");
    if (!arquivo) {
        perror("Erro ao abrir arquivo em contar_casos_ambientais");
        return -1;
    }

    char linha[2048];
    fgets(linha, sizeof(linha), arquivo); // Pular o Cabeçalho

    int contador = 0;
    while (fgets(linha, sizeof(linha), arquivo)) {
        char linha_copia[2048];
        strcpy(linha_copia, linha); // Copiar a linha para não sumir a original com strtok

        // O campo 'flag_ambiental' é a 14ª coluna.
        // Vamos usar strtok para chegar até lá.
        char* token = strtok(linha_copia, ";");
        for (int i = 0; i < 13; i++) {
            token = strtok(NULL, ";");
        }

        if (token != NULL) {
            int flag = atoi(token); // Converte o texto ("0" ou "1") para número
            if (flag == 1) {
                contador++;
            }
        }
    }

    fclose(arquivo);
    return contador;
}


// ITEM 7: Contar processos de quilombolas
int contar_casos_quilombolas(const char* nome_arquivo) {
    FILE* arquivo = fopen(nome_arquivo, "r");
    if (!arquivo) {
        perror("Erro ao abrir arquivo em contar_casos_quilombolas");
        return -1;
    }

    char linha[2048];
    fgets(linha, sizeof(linha), arquivo); // Pular o Cabeçalho

    int contador = 0;
    while (fgets(linha, sizeof(linha), arquivo)) {
        char linha_copia[2048];
        strcpy(linha_copia, linha); // Copiar a linha para não sumir a original com strtok

        // O campo 'flag_quilombolas' é a 15ª coluna.
        // Vamos usar strtok para chegar até lá.
        char* token = strtok(linha_copia, ";");
        for (int i = 0; i < 14; i++) {
            token = strtok(NULL, ";");
        }

        if (token != NULL) {
            int flag = atoi(token); // Converte o texto ("0" ou "1") para número
            if (flag == 1) {
                contador++;
            }
        }
    }

    fclose(arquivo);
    return contador;
}


// ITEM 8: Contar processos de indigenas
int contar_casos_indigenas(const char* nome_arquivo) {
    FILE* arquivo = fopen(nome_arquivo, "r");
    if (!arquivo) {
        perror("Erro ao abrir arquivo em contar_casos_indigenas");
        return -1;
    }

    char linha[2048];
    fgets(linha, sizeof(linha), arquivo); // Pular o Cabeçalho

    int contador = 0;
    while (fgets(linha, sizeof(linha), arquivo)) {
        char linha_copia[2048];
        strcpy(linha_copia, linha); // Copiar a linha para não sumir a original com strtok

        // O campo 'flag_indigenas' é a 16ª coluna.
        // Vamos usar strtok para chegar até lá.
        char* token = strtok(linha_copia, ";");
        for (int i = 0; i < 15; i++) {
            token = strtok(NULL, ";");
        }

        if (token != NULL) {
            int flag = atoi(token); // Converte o texto ("0" ou "1") para número
            if (flag == 1) {
                contador++;
            }
        }
    }

    fclose(arquivo);
    return contador;
}


// ITEM 9: Contar processos de infancia
int contar_casos_infancia(const char* nome_arquivo) {
    FILE* arquivo = fopen(nome_arquivo, "r");
    if (!arquivo) {
        perror("Erro ao abrir arquivo em contar_casos_infancia");
        return -1;
    }

    char linha[2048];
    fgets(linha, sizeof(linha), arquivo); // Pular o Cabeçalho

    int contador = 0;
    while (fgets(linha, sizeof(linha), arquivo)) {
        char linha_copia[2048];
        strcpy(linha_copia, linha); // Copiar a linha para não sumir a original com strtok

        // O campo 'flag_infancia' é a 17ª coluna.
        // Vamos usar strtok para chegar até lá.
        char* token = strtok(linha_copia, ";");
        for (int i = 0; i < 16; i++) {
            token = strtok(NULL, ";");
        }

        if (token != NULL) {
            int flag = atoi(token); // Converte o texto ("0" ou "1") para número
            if (flag == 1) {
                contador++;
            }
        }
    }

    fclose(arquivo);
    return contador;
}


// ITEM 10: Calcular o número de dias entre duas datas
int calcular_dias_entre_datas(const char* data_inicial_str, const char* data_final_str)  {
    struct tm data_inicial_tm = {0};
    struct tm data_final_tm = {0};
    time_t t_inicial, t_final;

    // 1. Converte a string da data inicial (ex: "2023-01-15") para a estrutura de data do C
    sscanf(data_inicial_str, "%d-%d-%d", &data_inicial_tm.tm_year, &data_inicial_tm.tm_mon, &data_inicial_tm.tm_mday);

    // 2. Converte a string da data final para a estrutura de data do C
    sscanf(data_final_str, "%d-%d-%d", &data_final_tm.tm_year, &data_final_tm.tm_mon, &data_final_tm.tm_mday);

    // Ajusta o ano e o mês para o formato legado da biblioteca de tempo do C (ano a partir de 1900, mês de 0 a 11).
    data_inicial_tm.tm_year -= 1900;
    data_inicial_tm.tm_mon -= 1;
    data_final_tm.tm_year -= 1900;
    data_final_tm.tm_mon -= 1;

    // 4. Converte as estruturas de data para um formato numérico (segundos desde 1970).
    // mktime Converte a estrutura de data (ano, mês, dia) em um número único de segundos para permitir cálculos.
    t_inicial = mktime(&data_inicial_tm);
    t_final = mktime(&data_final_tm);

    // 5. Calcula a diferença de tempo em segundos e depois converte para dias
    if (t_inicial != -1 && t_final != -1) {
        double diferenca_em_segundos = difftime(t_final, t_inicial); // difftime Calcula a diferença exata em segundos entre dois momentos no tempo.
        int dias = diferenca_em_segundos / (60 * 60 * 24); // (segundos * minutos * horas)
        return dias;
    }

    return -1; // Retorna -1 se houver erro na conversão das datas
}


// ITEM 11: Calcular o percentual de cumprimento da meta 1
double calcular_cumprimento_meta1(const char* nome_arquivo) {
    FILE* arquivo = fopen(nome_arquivo, "r");
    if (!arquivo) {
        perror("Erro ao abrir arquivo em calcular_cumprimento_meta1");
        return -1.0;
    }

    char linha[2048];
    fgets(linha, sizeof(linha), arquivo); // Pular o Cabeçalho

    // Variáveis para somar os totais de cada coluna
    double total_cnm1 = 0, total_julgadom1 = 0, total_desm1 = 0, total_susm1 = 0;

    while (fgets(linha, sizeof(linha), arquivo)) {
        char linha_copia[2048];
        strcpy(linha_copia, linha);

        char* campos[30]; // Array de ponteiros para guardar cada campo
        int i = 0;
        char* token = strtok(linha_copia, ";");
        while (token != NULL && i < 30) {
            campos[i] = token;
            token = strtok(NULL, ";");
            i++;
        }

        // Soma os valores das colunas corretas, convertendo de texto para número (double).
        // cnm1 (coluna 20), julgadom1 (26), desm1 (27), susm1 (28).
        // atof Converte uma string que representa um número para o tipo 'double'.
        total_cnm1 += atof(campos[19]);
        total_julgadom1 += atof(campos[25]);
        total_desm1 += atof(campos[26]);
        total_susm1 += atof(campos[27]);
    }
    fclose(arquivo);

    // Aplica a fórmula da Meta 1
    double denominador = total_cnm1 + total_desm1 - total_susm1;
    if (denominador == 0) {
        return 0.0; // Evita divisão por zero
    }

    double cumprimento = (total_julgadom1 / denominador) * 100.0;
    return cumprimento;
}


// ITEM 12: Gerar um arquivo CSV com todos os processos julgados na Meta 1
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
    // Copia o cabeçalho do arquivo de entrada para o de saída
    fgets(linha, sizeof(linha), entrada);
    fputs(linha, saida);

    while (fgets(linha, sizeof(linha), entrada)) {
        char linha_copia[2048];
        strcpy(linha_copia, linha);

        char* campos[30];
        int i = 0;
        char* token = strtok(linha_copia, ";");
        while (token != NULL && i < 30) {
            campos[i] = token;
            token = strtok(NULL, ";");
            i++;
        }

        // O campo 'julgadom1' é a 26ª coluna (índice 25)
        if (i > 25) { 
            int julgado = atoi(campos[25]);
            if (julgado == 1) {
                fputs(linha, saida);
            }
        }
    }

    printf("[Item 12] Arquivo '%s' gerado com sucesso!\n", arq_saida);
    fclose(entrada);
    fclose(saida);
} 