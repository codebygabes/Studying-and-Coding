# Universidade Católica de Brasília (UCB)
**Disciplina:** Estrutura de Dados  
**Avaliação:** Trabalho Prático 01 – Manipulação de Arquivos em C  
**Semestre:** 2º Semestre de 2025  
**Desenvolvido Por:** João Gabriel dos Santos Felipe & Emily Souza dos Santos  

---

## 1. Visão Geral do Projeto

Este trabalho foi desenvolvido como requisito avaliativo para a disciplina de Estrutura de Dados, consistindo em um sistema de análise de dados processuais em Linguagem C. O programa é especializado na leitura, processamento e análise de dados judiciais em larga escala, provenientes do sistema DataJud do Tribunal de Justiça do Distrito Federal e Territórios (TJDFT).

A aplicação processa um arquivo CSV de aproximadamente 365 MB contendo cerca de 250.000 processos judiciais, implementando 12 funcionalidades analíticas que abrangem desde contagens estatísticas básicas até cálculos complexos de metas do Conselho Nacional de Justiça (CNJ). O sistema demonstra na prática conceitos fundamentais de manipulação de arquivos, estruturas de dados e algoritmos de processamento eficiente.

## 2. Funcionalidades Implementadas

### 2.1. Análise Estatística Básica
- **Item 01**: Contagem total de processos no arquivo CSV
- **Item 02**: Identificação do processo com data de recebimento mais antiga
- **Item 03**: Busca do último órgão julgador por ID de processo

### 2.2. Contagem por Categorias Temáticas
- **Item 04**: Processos de violência doméstica (`flag_violencia_domestica = 1`)
- **Item 05**: Processos de feminicídio (`flag_feminicidio = 1`)
- **Item 06**: Processos ambientais (`flag_ambiental = 1`)
- **Item 07**: Processos relacionados a comunidades quilombolas (`flag_quilombolas = 1`)
- **Item 08**: Processos envolvendo povos indígenas (`flag_indigenas = 1`)
- **Item 09**: Processos de infância e juventude (`flag_infancia = 1`)

### 2.3. Cálculos Avançados e Geração de Relatórios
- **Item 10**: Cálculo de diferença em dias entre data de recebimento e resolução
- **Item 11**: Cálculo do percentual de cumprimento da Meta 1 do CNJ
- **Item 12**: Geração de arquivo CSV filtrado com processos julgados na Meta 1

## 3. Arquitetura do Sistema

### 3.1. Estrutura de Arquivos
```
Projeto_DataJud/
├── processo.h          # Interface do Tipo Abstrato de Dados (TAD)
├── processo.c          # Implementação das funções do TAD
├── main.c             # Programa principal e orquestração
├── TJDFT_filtrado.csv # Arquivo de dados de entrada (365MB)
└── julgados_meta1.csv # Arquivo gerado pela análise (Item 12)
```

### 3.2. Estrutura de Dados Principal

A estrutura `Processo` definida em `processo.h` organiza os dados em três grupos lógicos:

```c
typedef struct {
    // Campos numéricos (IDs e flags)
    int id_processo, id_tribunal, recurso, id_ultimo_oj, id_ultima_classe;
    int flag_violencia_domestica, flag_feminicidio, flag_ambiental;
    int flag_quilombolas, flag_indigenas, flag_infancia;
    
    // Campos textuais (strings)
    char numero_sigilo[50], sigla_grau[5], procedimento[100];
    char ramo_justica[5], sigla_tribunal[10], dt_recebimento[11];
    char decisao[11], dt_resolvido[11];
    
    // Campos de métricas da Meta 1 (valores decimais)
    double cnm1, primeirasentm1, baixm1, decm1, mpum1, julgadom1, desm1, susm1;
} Processo;
```

## 4. Detalhes de Implementação Técnica

### 4.1. Estratégia de Processamento de Arquivos

**Buffer Otimizado**: Utilização de buffer de 2048 caracteres para garantir leitura completa de linhas longas:
```c
char linha[2048];  // Buffer robusto para arquivos de grande porte
```

**Parsing Seguro**: Cópia da linha original antes do processamento com `strtok`:
```c
char linha_copia[2048];
strcpy(linha_copia, linha);  // Preserva dados originais
```

**Tratamento de Quebras de Linha**: Compatibilidade cross-platform:
```c
linha[strcspn(linha, "\r\n")] = '\0';  // Remove caracteres de quebra de linha | \0 = caractere nulo (fim da string)
```

### 4.2. Algoritmos Específicos por Item

#### **Item 2 - Processo Mais Antigo**
- Implementa algoritmo de comparação sequencial de datas no formato ISO (YYYY-MM-DD)
- Utiliza `strcmp()` para comparação lexicográfica que funciona com datas ISO

#### **Item 10 - Cálculo de Diferença de Datas**
- Conversão de strings para estruturas `struct tm` da biblioteca `time.h`
- Cálculo preciso considerando fusos horários:
```c
data_inicial_tm.tm_hour = 12;  // Evita problemas de horário de verão
```

#### **Item 11 - Meta 1 do CNJ**
- Fórmula implementada: `(julgadom1 / (cnm1 + desm1 - susm1)) * 100`
- Processamento manual de campos para garantir índices corretos (19, 24, 25, 26)

#### **Item 12 - Filtragem Eficiente**
- Leitura sequencial com filtro em tempo real
- Geração de arquivo de saída com cabeçalho preservado

### 4.3. Tratamento de Erros e Validações

- Verificação de abertura de arquivos em todas as funções
- Validação de existência de dados antes do processamento
- Prevenção de divisão por zero em cálculos percentuais
- Verificação de limites de arrays e buffers

## 5. Compilação e Execução

### 5.1. Pré-requisitos
- Compilador GCC (GNU Compiler Collection)
- Arquivo `TJDFT_filtrado.csv` no diretório de execução
- Sistema operacional: Windows/Linux/macOS

### 5.2. Comandos de Compilação

```bash
gcc main.c processo.c -o analisador_processos
```

### 5.3. Execução do Programa

```bash
./analisador_processos
```

## 6. Desafios e Soluções Técnicas

### 6.1. Desafio: Processamento de Arquivo de 365MB
**Solução**: Leitura sequencial linha por linha com buffer otimizado, evitando carregamento completo na memória.

### 6.2. Desafio: Campos com Indices Fixos
**Solução**: Implementação de parser manual que conta delimitadores, garantindo acesso correto aos campos.

### 6.3. Desafio: Cálculos com Datas
**Solução**: Uso da biblioteca `time.h` para conversões precisas e cálculo de diferenças temporais.

## 7. Resultados e Aplicações Práticas

O sistema desenvolvido permite:
- **Análise estatística** do acervo processual do TJDFT
- **Monitoramento de metas** do CNJ em tempo real
- **Identificação de processos** por temas específicos
- **Geração de relatórios** personalizados para gestão judiciária

## 8. Conclusão

Este trabalho demonstrou a aplicação prática de conceitos de estrutura de dados no desenvolvimento de um sistema robusto para análise de dados judiciais. A arquitetura modular, o processamento eficiente de grandes volumes de dados e a implementação cuidadosa dos algoritmos resultaram em uma ferramenta funcional e escalável.

O código-fonte está organizado de forma clara e documentada, facilitando futuras manutenções e expansões de funcionalidades.

---

**Nota**: Os dados utilizados são fictícios para fins educacionais. O sistema está preparado para processar o arquivo real `TJDFT_filtrado.csv` quando disponível.