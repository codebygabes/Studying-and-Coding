# Universidade Católica de Brasília (UCB)
**Disciplina:** Estrutura de Dados
**Avaliação:** Trabalho Prático 01 – Manipulação de Arquivos em C  
**Semestre:** 2º Semestre de 2025
**Desenvolvido Por:** João Gabriel dos Santos Felipe & Emily Souza dos Santos

---

## 1. Visão Geral do Projeto

Este projeto, desenvolvido como requisito avaliativo para a disciplina de Algoritmos e Programação Estruturada, consiste em um software de análise de dados em Linguagem C. O programa é projetado para ler, processar e analisar um grande volume de dados judiciais (365 MB, 250.000 processos) provenientes do sistema DataJud, fornecidos em um arquivo de formato CSV.

A aplicação extrai 12 métricas distintas, abrangendo desde contagens estatísticas simples até cálculos complexos de metas e buscas específicas, demonstrando a manipulação eficiente de arquivos e a aplicação de estruturas de dados em um contexto prático.

## 2. Funcionalidades Implementadas

O software implementa integralmente as 12 funcionalidades de análise especificadas no enunciado do trabalho:

1.  **Contagem Total de Processos:** Calcula o número total de registros de processos na base de dados.
2.  **Identificação do Processo Mais Antigo:** Localiza o `id_processo` correspondente ao registro com a data de recebimento (`dt_recebimento`) mais antiga.
3.  **Busca de Órgão Julgador:** Realiza a busca do `id_ultimo_oj` a partir de um `id_processo` fornecido como entrada.
4.  **Contagem de Casos de Violência Doméstica:** Totaliza os processos marcados com a `flag_violencia_domestica`.
5.  **Contagem de Casos de Feminicídio:** Totaliza os processos marcados com a `flag_feminicidio`.
6.  **Contagem de Casos Ambientais:** Totaliza os processos marcados com a `flag_ambiental`.
7.  **Contagem de Casos de Quilombolas:** Totaliza os processos marcados com a `flag_quilombolas`.
8.  **Contagem de Casos Indígenas:** Totaliza os processos marcados com a `flag_indigenas`.
9.  **Contagem de Casos de Infância e Juventude:** Totaliza os processos marcados com a `flag_infancia`.
10. **Cálculo de Dias Entre Datas:** Calcula o número de dias transcorridos entre duas datas.
11. **Cálculo da Meta 1 (CNJ):** Calcula o percentual de cumprimento da Meta 1, conforme a fórmula especificada.
12. **Geração de Relatório CSV:** Cria um novo arquivo (`julgados_meta1.csv`) contendo um subconjunto dos dados, filtrando apenas os processos julgados (mérito) na Meta 1.

## 3. Arquitetura e Detalhes da Implementação

A arquitetura do software adere estritamente ao paradigma de **Tipo Abstrato de Dados (TAD)** para garantir modularidade, encapsulamento e clareza.

* **`processo.h`**: A interface do TAD. Define o "contrato" através da `struct Processo` e dos protótipos das 12 funções de análise, abstraindo a complexidade da implementação. Foram realizadas correções de nomenclatura e tipos de dados (`int` para `double`) para garantir a consistência e precisão do programa.

* **`processo.c`**: A implementação do TAD. Contém a lógica de manipulação de arquivos e os algoritmos de análise. Detalhes notáveis:
    * **Leitura Robusta:** Todas as funções utilizam um buffer de `2048` caracteres para a leitura de linhas (`fgets`), garantindo que linhas longas do arquivo de 365 MB sejam lidas integralmente, prevenindo a fragmentação de dados que corrompia os resultados.
    * **Parsing Seguro:** A análise de cada linha (parsing) é feita com `strtok` sobre uma **cópia** da linha original, preservando a integridade dos dados para operações subsequentes.
    * **Tratamento de Erros:** Foram implementadas verificações de segurança ("safety checks") em todas as funções antes de acessar os dados de uma coluna. Isso previne falhas de segmentação ("crashes") ao processar linhas malformadas ou incompletas, tornando o programa resiliente.
    * **Correção Lógica Crítica:** Na função `obter_id_ultimo_oj`, o operador de atribuição (`=`) foi corrigido para o operador de comparação (`==`), eliminando um erro lógico grave que produzia resultados incorretos.

* **`main.c`**: O programa "cliente" ou "driver". Orquestra a execução, chama as funções do TAD em sequência e apresenta o relatório final de forma organizada. Implementa um teste dinâmico para o Item 3, utilizando o resultado do Item 2 como entrada, aumentando a robustez da demonstração.

## 4. Guia de Compilação e Execução

### 4.1. Pré-requisitos
Um ambiente de desenvolvimento C com o compilador **GCC (GNU Compiler Collection)** ou compatível.

### 4.2. Processo de Execução

   1. Assegure-se de que o arquivo de dados (TJDFT_filtrado.csv) resida no mesmo diretório do executável.
   2. A partir do diretório raiz do projeto, execute o seguinte comando no terminal:
 ```bash
    gcc main.c processo.c -o meu_programa
    ./meu_programa

