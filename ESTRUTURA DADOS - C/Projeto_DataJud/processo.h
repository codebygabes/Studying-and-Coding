#ifndef PROCESSO_H
#define PROCESSO_H

typedef struct {

    int id_processo;                    // Identificador unico do processo
    int id_tribunal;                    // ID numerico do tribunal
    int recurso;                        // Indica se eh recurso
    int id_ultimo_oj;                   // ID do ultimo orgao julgador
    int id_ultima_classe;               // ID da classe processual
    int flag_violencia_domestica;       // Flag para violencia domestica
    int flag_feminicidio;               // Flag para feminicidio
    int flag_ambiental;                 // Flag para casos ambientais
    int flag_quilombolas;               // Flag para comunidades quilombolas
    int flag_indigenas;                 // Flag para povos indigenas
    int flag_infancia;                  // Flag para infancia e juventude


    char numero_sigilo[50];             // Numero do processo com sigilo
    char sigla_grau[5];                 // Grau de jurisdicao (1º ou 2º grau)
    char procedimento[100];             // Tipo de procedimento
    char ramo_justica[5];               // Ramo da justica (Estadual, Federal)
    char sigla_tribunal[10];            // Sigla do tribunal 
    char dt_recebimento[11];            // Data de recebimento (YYYY-MM-DD)
    char decisao[11];                   // Data da decisao
    char dt_resolvido[11];              // Data de resolucao


    double cnm1;                        // Campo numerico para meta 1
    double primeirasentm1;              // Primeiras sentencas meta 1
    double baixm1;                      // Baixas meta 1
    double decm1;                       // Decisoes meta 1
    double mpum1;                       // MPU meta 1
    double julgadom1;                   // Julgados meta 1
    double desm1;                       // Desistencias meta 1
    double susm1;                       // Suspensoes meta 1

} Processo;

// Protótipos
int contar_processos(const char* nome_arquivo); // Item 01
int encontrar_processo_mais_antigo(const char* nome_arquivo);   // Item 02
int obter_id_ultimo_oj(const char* nome_arquivo, int id_processo_alvo); // Item 03
int contar_casos_especifico(const char* nome_arquivo, int indice_flag); // Item Auxiliar para Contar Flags
int contar_casos_violencia_domestica(const char* nome_arquivo); // Item 04
int contar_casos_feminicidio(const char* nome_arquivo); // Item 05
int contar_casos_ambientais(const char* nome_arquivo);  // Item 06
int contar_casos_quilombolas(const char* nome_arquivo); // Item 07
int contar_casos_indigenas(const char* nome_arquivo);   // Item 08
int contar_casos_infancia(const char* nome_arquivo);    // Item 09
int calcular_dias_entre_datas(const char* nome_arquivo, int id_processo);   // Item 10
double calcular_cumprimento_meta1(const char* nome_arquivo);    // Item 11
void gerar_csv_julgados_meta1(const char* nome_arquivo_entrada, const char* nome_arquivo_saida);    // Item 12

#endif