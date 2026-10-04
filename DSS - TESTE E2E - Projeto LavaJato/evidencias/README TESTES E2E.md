# Testes End-to-End — Sistema de Gerenciamento de Lava-Jato

> Documento de registro dos testes E2E do sistema, executados de forma **automatizada com JUnit 5** pela interface CLI (a primeira rodada, manual, foi mantida como evidência complementar).

---

## 1. Identificação

| Campo | Informação |
|---|---|
| Projeto | Sistema de Gerenciamento de Lava-Jato |
| Tipo de teste | End-to-End (E2E) |
| Ferramenta | JUnit 5 (JUnit Jupiter 5.14.0), executado pelo IntelliJ IDEA |
| Interface | CLI — o sistema é executado de verdade (`applications.Main`) e controlado pelo teclado simulado |
| Banco de dados | MySQL |
| Execução | Automatizada (36 testes) |
| Execução inicial | Manual, em 15/09/2026, usada para levantar os casos e os defeitos |
| Data da execução automatizada | 30/09/2026 |
| Resultado | **36 testes: 24 aprovados e 12 reprovados** (cada reprovação corresponde a um defeito registrado na seção 9) |
| Código dos testes | `Projeto_LavaJato_v3/test/e2e/` |

---

## 2. Objetivo

Validar o funcionamento do sistema a partir da perspectiva do usuário, percorrendo os fluxos completos da aplicação. Os testes verificam a integração entre a CLI, os serviços, os DAOs, a conexão JDBC e o banco de dados MySQL.

O foco não é analisar métodos isolados, mas verificar se o usuário consegue iniciar o sistema, autenticar-se, acessar os menus e realizar operações de cadastro, consulta, atualização e exclusão. Em cada teste, o resultado é conferido de duas formas: pelo que aparece no console e pelo que realmente ficou gravado no banco.

---

## 3. Escopo

Foram avaliados:

- inicialização e conexão com o banco;
- login válido, login inválido, login com campos vazios e encerramento do sistema;
- cadastro, consulta, atualização e exclusão de clientes;
- cadastro, consulta, atualização e exclusão de funcionários;
- cadastro, consulta, atualização e exclusão de serviços;
- consulta, cadastro, atualização e exclusão de atendimentos;
- validação de campos obrigatórios;
- validação de placa de veículo;
- validação de valor de serviço, inclusive o formato decimal digitado;
- integridade referencial entre as entidades;
- comportamento diante de IDs inexistentes;
- comportamento diante de entradas inválidas no terminal;
- consistência dos dados quando o cadastro de atendimento falha.

## 4. Fora do escopo

Não foram executados nesta etapa:

- testes unitários;
- testes da API HTTP;
- testes com Postman, Insomnia ou curl;
- ferramentas de teste de interface web (Cypress, Selenium, Playwright), que não se aplicam porque a interface do sistema é CLI;
- testes de carga, estresse ou desempenho;
- testes de segurança aprofundados;
- testes em diferentes sistemas operacionais;
- homologação com usuários reais.

---

## 5. Ambiente de teste

| Item | Configuração |
|---|---|
| Linguagem | Java |
| JDK | OpenJDK 26.0.2.1 |
| IDE | IntelliJ IDEA |
| Ferramenta de teste | JUnit 5 (JUnit Jupiter 5.14.0) |
| Banco | MySQL Server |
| Host | localhost |
| Porta | 3306 |
| Banco | lavajato |
| Driver | MySQL Connector/J 9.7.0 |
| Classe de entrada | `applications.Main` |
| Interface | CLI |
| Usuário de teste | usuário `admin`, criado pelo script `crud_lavajato.sql` |

A aplicação realiza a conexão com o banco antes de apresentar a tela de login. O banco contém tabelas de usuários, clientes, funcionários, serviços, atendimentos e associação entre atendimentos e serviços.

**Observação sobre o idioma:** o sistema lê números com `Scanner`, que usa o formato do idioma do computador (no Windows em português, a vírgula é o separador decimal). Para o resultado não depender da máquina, os testes executam o sistema com o idioma fixado em inglês (ponto decimal). A única exceção é o caso CT-E2E-SER-009, que fixa o português (Brasil) de propósito para reproduzir o defeito DEF-E2E-007.

---

## 6. Como os testes funcionam

As classes de teste ficam em `Projeto_LavaJato_v3/test/e2e/`:

| Classe | Conteúdo |
|---|---|
| `AutenticacaoE2ETest` | Login e encerramento do sistema (5 testes) |
| `ClienteE2ETest` | Fluxos de clientes (7 testes) |
| `FuncionarioE2ETest` | Fluxos de funcionários (7 testes) |
| `ServicoE2ETest` | Fluxos de serviços (9 testes) |
| `AtendimentoE2ETest` | Fluxos de atendimentos (8 testes) |
| `E2EBase`, `AppCli`, `BancoTeste` | Apoio: reset do banco, execução do sistema e consultas ao banco |

Cada teste segue o mesmo ciclo:

1. **Preparar o cenário:** o banco `lavajato` é apagado e recriado com dados conhecidos (3 clientes, 2 funcionários, 2 serviços e 3 atendimentos). Por isso um teste não depende do outro.
2. **Executar o sistema de verdade:** a classe `applications.Main` roda em um processo separado, e o teste "digita" as opções no terminal (login, menus e dados) por meio da entrada padrão.
3. **Ler o console:** tudo o que o sistema imprime é capturado.
4. **Conferir o resultado:** o teste verifica a mensagem exibida, o código de saída do programa e, por consulta direta ao MySQL, o que foi (ou não) gravado.
5. **Registrar a evidência:** cada execução salva em `evidencias/execucoes/<ID-do-caso>.txt` o que foi digitado, o que apareceu no console e o código de saída.

Os testes verificam o **comportamento esperado** pelo Documento de Visão. Por isso, quando o sistema não se comporta como esperado, o teste falha (fica vermelho) e esse resultado é o registro do defeito. Os 12 testes que reprovaram têm a marca `@Tag("defeito-conhecido")` no código.

## 6.1 Procedimento de execução

1. Iniciar o MySQL Server com o banco `lavajato` criado a partir do script `crud_lavajato.sql`. **Atenção:** os testes apagam e recriam os dados desse banco.
2. Abrir o projeto no IntelliJ IDEA com a pasta `test` marcada como *Test Sources Root* e o JUnit 5 no classpath.
3. Clicar com o botão direito na pasta `test` e escolher *Run 'All Tests'*.
4. Aguardar a execução (cerca de 28 segundos) e conferir a árvore de resultados.
5. Para cada teste reprovado, selecionar o teste na árvore e capturar a mensagem de falha; abrir o arquivo correspondente em `evidencias/execucoes/` e capturar o console.
6. Exportar o relatório pelo menu *Export Test Results* (formato HTML).
7. Salvar os prints nas subpastas de `evidencias`, uma por seção.

> **Atenção:** prints tirados do computador pessoal do autor.

---

## 7. Evidências das execuções

### 7.0 Visão geral da execução automatizada

- **Relatório exportado do IntelliJ:** [relatorio-junit.html](relatorio-junit.html) (36 testes, 12 reprovados e 24 aprovados, em cerca de 28 s). Abra o arquivo no navegador.
- **Árvore de resultados:** [parte 1](JUNIT-arvore-completa-1.png) e [parte 2](JUNIT-arvore-completa-2.png).
- **Registro em texto de todos os casos:** pasta [`evidencias/execucoes/`](execucoes/), com um arquivo por caso.

As evidências estão salvas em subpastas dentro de `evidencias`, uma por seção (7.1 a 7.5). Os arquivos que começam com `E2E-` são prints da execução manual de 15/09/2026; os que começam com `JUNIT-` são da execução automatizada de 30/09/2026.


### 7.1 Sistema e autenticação

#### Figura 1 — Conexão com o banco e tela de login (execução manual)

Demonstra que o sistema conseguiu conectar ao MySQL e apresentou a tela de autenticação.

![Figura 1 — Conexão com o banco e tela de login](7.1%20Sistema%20e%20autentica%C3%A7%C3%A3o/E2E-001-conexao-login.png)

#### Figura 2 — Login válido e menu principal (execução manual)

Demonstra que o usuário conseguiu autenticar-se e acessar as funcionalidades do sistema.

![Figura 2 — Login válido e menu principal](7.1%20Sistema%20e%20autentica%C3%A7%C3%A3o/E2E-002-menu-principal.png)

#### Execução automatizada

| Caso | Registro em texto | Prints da execução automatizada |
|---|---|---|
| CT-E2E-001 | [CT-E2E-001.txt](execucoes/CT-E2E-001.txt) | — |
| CT-E2E-002 | [CT-E2E-002.txt](execucoes/CT-E2E-002.txt) | — |
| CT-E2E-003 | [CT-E2E-003.txt](execucoes/CT-E2E-003.txt) | — |
| CT-E2E-004 | [CT-E2E-004.txt](execucoes/CT-E2E-004.txt) | — |
| CT-E2E-005 | [CT-E2E-005.txt](execucoes/CT-E2E-005.txt) | — |

---


### 7.2 Clientes

#### Figura 3 — Cadastro e consulta de cliente (execução manual)

Demonstra o cadastro de um cliente válido e sua posterior exibição na listagem.

![Figura 3 — Cadastro e consulta de cliente](7.2%20Clientes/E2E-CLI-001-cadastro.png)

#### Figura 4 — Validação de placa inválida (execução manual)

Demonstra que o sistema rejeitou uma placa fora dos formatos aceitos.

![Figura 4 — Validação de placa inválida](7.2%20Clientes/E2E-CLI-002-placa-invalida.png)

#### Figura 5 — Atualização de cliente (execução manual)

Demonstra que os dados de um cliente foram alterados e apareceram atualizados na consulta.

![Figura 5 — Atualização de cliente](7.2%20Clientes/E2E-CLI-004-atualizacao.png)

#### Figura 6 — Restrição ao excluir cliente relacionado (execução manual)

Demonstra que o banco impediu a exclusão de um cliente associado a atendimentos. A mensagem exibida, entretanto, é técnica e poderia ser tratada pela aplicação.

![Figura 6 — Restrição ao excluir cliente relacionado](7.2%20Clientes/E2E-CLI-006-restricao-exclusao.png)

#### Figura 7 — Exclusão de ID inexistente (execução manual)

Demonstra que o sistema informou sucesso mesmo quando o ID informado não existia. Esse comportamento foi registrado como defeito.

![Figura 7 — Exclusão de ID inexistente](7.2%20Clientes/E2E-CLI-007-exclusao-id-inexistente.png)

#### Execução automatizada

| Caso | Registro em texto | Prints da execução automatizada |
|---|---|---|
| CT-E2E-CLI-001 | [CT-E2E-CLI-001.txt](execucoes/CT-E2E-CLI-001.txt) | — |
| CT-E2E-CLI-002 | [CT-E2E-CLI-002.txt](execucoes/CT-E2E-CLI-002.txt) | — |
| CT-E2E-CLI-003 | [CT-E2E-CLI-003.txt](execucoes/CT-E2E-CLI-003.txt) | — |
| CT-E2E-CLI-004 | [CT-E2E-CLI-004.txt](execucoes/CT-E2E-CLI-004.txt) | — |
| CT-E2E-CLI-005 | [CT-E2E-CLI-005.txt](execucoes/CT-E2E-CLI-005.txt) | — |
| CT-E2E-CLI-006 | [CT-E2E-CLI-006.txt](execucoes/CT-E2E-CLI-006.txt) | [falha 1](7.2%20Clientes/JUNIT-CLI-006-falha-1.png) · [falha 2](7.2%20Clientes/JUNIT-CLI-006-falha-2.png) · [console 1](7.2%20Clientes/JUNIT-CLI-006-console-1.png) · [console 2](7.2%20Clientes/JUNIT-CLI-006-console-2.png) |
| CT-E2E-CLI-007 | [CT-E2E-CLI-007.txt](execucoes/CT-E2E-CLI-007.txt) | [falha 1](7.2%20Clientes/JUNIT-CLI-007-falha-1.png) · [falha 2](7.2%20Clientes/JUNIT-CLI-007-falha-2.png) · [falha 3](7.2%20Clientes/JUNIT-CLI-007-falha-3.png) · [console 1](7.2%20Clientes/JUNIT-CLI-007-console-1.png) · [console 2](7.2%20Clientes/JUNIT-CLI-007-console-2.png) |

---


### 7.3 Funcionários

#### Figura 8 — Atualização de funcionário (execução manual)

Demonstra a atualização de dados de um funcionário e a confirmação apresentada pelo sistema.

![Figura 8 — Atualização de funcionário](7.3%20Funcion%C3%A1rios/E2E-FUN-005-atualizacao.png)

#### Figura 9 — Validação de campos obrigatórios (execução manual)

Demonstra as mensagens apresentadas quando nome, cargo ou telefone não são preenchidos.

![Figura 9a — Validação de nome obrigatório](7.3%20Funcion%C3%A1rios/E2E-FUN-002-nome-vazio.png)

![Figura 9b — Validação de cargo obrigatório](7.3%20Funcion%C3%A1rios/E2E-FUN-003-cargo-vazio.png)

![Figura 9c — Validação de telefone obrigatório](7.3%20Funcion%C3%A1rios/E2E-FUN-004-telefone-vazio.png)

#### Figura 10 — Restrição ao excluir funcionário relacionado (execução manual)

Demonstra o bloqueio da exclusão de funcionário associado a atendimento.

![Figura 10 — Restrição ao excluir funcionário relacionado](7.3%20Funcion%C3%A1rios/E2E-FUN-007-restricao-exclusao.png)

#### Execução automatizada

| Caso | Registro em texto | Prints da execução automatizada |
|---|---|---|
| CT-E2E-FUN-001 | [CT-E2E-FUN-001.txt](execucoes/CT-E2E-FUN-001.txt) | — |
| CT-E2E-FUN-002 | [CT-E2E-FUN-002.txt](execucoes/CT-E2E-FUN-002.txt) | — |
| CT-E2E-FUN-003 | [CT-E2E-FUN-003.txt](execucoes/CT-E2E-FUN-003.txt) | — |
| CT-E2E-FUN-004 | [CT-E2E-FUN-004.txt](execucoes/CT-E2E-FUN-004.txt) | — |
| CT-E2E-FUN-005 | [CT-E2E-FUN-005.txt](execucoes/CT-E2E-FUN-005.txt) | — |
| CT-E2E-FUN-006 | [CT-E2E-FUN-006.txt](execucoes/CT-E2E-FUN-006.txt) | — |
| CT-E2E-FUN-007 | [CT-E2E-FUN-007.txt](execucoes/CT-E2E-FUN-007.txt) | [falha 1](7.3%20Funcion%C3%A1rios/JUNIT-FUN-007-falha-1.png) · [falha 2](7.3%20Funcion%C3%A1rios/JUNIT-FUN-007-falha-2.png) · [console 1](7.3%20Funcion%C3%A1rios/JUNIT-FUN-007-console-1.png) · [console 2](7.3%20Funcion%C3%A1rios/JUNIT-FUN-007-console-2.png) |

---


### 7.4 Serviços

#### Figura 11 — Cadastro e consulta de serviço (execução manual)

Demonstra o cadastro de um serviço válido e sua exibição na listagem.

![Figura 11 — Cadastro e consulta de serviço](7.4%20Servi%C3%A7os/E2E-SER-001-cadastro.png)

#### Figura 12 — Valor inválido ou entrada não numérica (execução manual)

Demonstra a validação de valor igual a zero ou o encerramento do programa por `InputMismatchException` quando foi informado um valor incompatível.

![Figura 12 — Valor inválido ou entrada não numérica](7.4%20Servi%C3%A7os/E2E-SER-004-valor-invalido.png)

#### Figura 13 — Restrição ao excluir serviço relacionado (execução manual)

Demonstra o bloqueio da exclusão de serviço associado a atendimento.

![Figura 13 — Restrição ao excluir serviço relacionado](7.4%20Servi%C3%A7os/E2E-SER-007-restricao-exclusao.png)

#### Figura 14 — Valor com ponto (49.90) encerra o programa (execução manual)

Demonstra que, ao digitar o valor no formato do exemplo mostrado na própria tela (`49.90`), o programa encerra com `InputMismatchException`.

![Figura 14 — Valor com ponto encerra o programa](7.4%20Servi%C3%A7os/E2E-SER-009-valor-com-ponto.png)

#### Figura 15 — Valor com vírgula (49,90) é aceito (execução manual)

Demonstra que o mesmo cadastro funciona quando o valor é digitado com vírgula (`49,90`).

![Figura 15 — Valor com vírgula é aceito](7.4%20Servi%C3%A7os/E2E-SER-009-valor-com-virgula.png)

#### Execução automatizada

| Caso | Registro em texto | Prints da execução automatizada |
|---|---|---|
| CT-E2E-SER-001 | [CT-E2E-SER-001.txt](execucoes/CT-E2E-SER-001.txt) | — |
| CT-E2E-SER-002 | [CT-E2E-SER-002.txt](execucoes/CT-E2E-SER-002.txt) | — |
| CT-E2E-SER-003 | [CT-E2E-SER-003.txt](execucoes/CT-E2E-SER-003.txt) | — |
| CT-E2E-SER-004 | [CT-E2E-SER-004.txt](execucoes/CT-E2E-SER-004.txt) | [falha](7.4%20Servi%C3%A7os/JUNIT-SER-004-falha.png) · [console 1](7.4%20Servi%C3%A7os/JUNIT-SER-004-console-1.png) · [console 2](7.4%20Servi%C3%A7os/JUNIT-SER-004-console-2.png) |
| CT-E2E-SER-005 | [CT-E2E-SER-005.txt](execucoes/CT-E2E-SER-005.txt) | — |
| CT-E2E-SER-006 | [CT-E2E-SER-006.txt](execucoes/CT-E2E-SER-006.txt) | — |
| CT-E2E-SER-007 | [CT-E2E-SER-007.txt](execucoes/CT-E2E-SER-007.txt) | [falha 1](7.4%20Servi%C3%A7os/JUNIT-SER-007-falha-1.png) · [falha 2](7.4%20Servi%C3%A7os/JUNIT-SER-007-falha-2.png) · [console 1](7.4%20Servi%C3%A7os/JUNIT-SER-007-console-1.png) · [console 2](7.4%20Servi%C3%A7os/JUNIT-SER-007-console-2.png) |
| CT-E2E-SER-008 | [CT-E2E-SER-008.txt](execucoes/CT-E2E-SER-008.txt) | [falha 1](7.4%20Servi%C3%A7os/JUNIT-SER-008-falha-1.png) · [falha 2](7.4%20Servi%C3%A7os/JUNIT-SER-008-falha-2.png) · [falha 3](7.4%20Servi%C3%A7os/JUNIT-SER-008-falha-3.png) · [console 1](7.4%20Servi%C3%A7os/JUNIT-SER-008-console-1.png) · [console 2](7.4%20Servi%C3%A7os/JUNIT-SER-008-console-2.png) |
| CT-E2E-SER-009 | [CT-E2E-SER-009.txt](execucoes/CT-E2E-SER-009.txt) | [falha 1](7.4%20Servi%C3%A7os/JUNIT-SER-009-falha-1.png) · [falha 2](7.4%20Servi%C3%A7os/JUNIT-SER-009-falha-2.png) · [falha 3](7.4%20Servi%C3%A7os/JUNIT-SER-009-falha-3.png) · [console 1](7.4%20Servi%C3%A7os/JUNIT-SER-009-console-1.png) · [console 2](7.4%20Servi%C3%A7os/JUNIT-SER-009-console-2.png) |

---


### 7.5 Atendimentos

#### Figura 16 — Listagem de atendimentos (execução manual)

Demonstra os atendimentos existentes, com seus IDs, datas e observações.

![Figura 16 — Listagem de atendimentos](7.5%20Atendimentos/E2E-ATE-001-listagem.png)

#### Figura 17 — Erro no cadastro de atendimento (execução manual)

Demonstra a ocorrência de `NullPointerException` durante a associação dos serviços ao atendimento e o encerramento da aplicação com `exit code 1`.

![Figura 17 — Erro no cadastro de atendimento](7.5%20Atendimentos/E2E-ATE-002-atendimento-excecao.png)

#### Figura 18 — Atualização de atendimento (execução manual)

Demonstra que cliente, funcionário e observação de um atendimento foram atualizados.

![Figura 18 — Atualização de atendimento](7.5%20Atendimentos/E2E-ATE-003-atualizacao.png)

#### Figura 19 — Erro de relacionamento no atendimento (execução manual)

Demonstra o bloqueio provocado por uma chave estrangeira quando foi informado um relacionamento inválido.

![Figura 19 — Erro de relacionamento no atendimento](7.5%20Atendimentos/E2E-ATE-004-erro-relacionamento.png)

#### Execução automatizada

| Caso | Registro em texto | Prints da execução automatizada |
|---|---|---|
| CT-E2E-ATE-001 | [CT-E2E-ATE-001.txt](execucoes/CT-E2E-ATE-001.txt) | — |
| CT-E2E-ATE-002 | [CT-E2E-ATE-002.txt](execucoes/CT-E2E-ATE-002.txt) | [falha 1](7.5%20Atendimentos/JUNIT-ATE-002-falha-1.png) · [falha 2](7.5%20Atendimentos/JUNIT-ATE-002-falha-2.png) · [falha 3](7.5%20Atendimentos/JUNIT-ATE-002-falha-3.png) · [console 1](7.5%20Atendimentos/JUNIT-ATE-002-console-1.png) · [console 2](7.5%20Atendimentos/JUNIT-ATE-002-console-2.png) |
| CT-E2E-ATE-003 | [CT-E2E-ATE-003.txt](execucoes/CT-E2E-ATE-003.txt) | — |
| CT-E2E-ATE-004 | [CT-E2E-ATE-004.txt](execucoes/CT-E2E-ATE-004.txt) | [falha 1](7.5%20Atendimentos/JUNIT-ATE-004-falha-1.png) · [falha 2](7.5%20Atendimentos/JUNIT-ATE-004-falha-2.png) · [console 1](7.5%20Atendimentos/JUNIT-ATE-004-console-1.png) · [console 2](7.5%20Atendimentos/JUNIT-ATE-004-console-2.png) |
| CT-E2E-ATE-005 | [CT-E2E-ATE-005.txt](execucoes/CT-E2E-ATE-005.txt) | — |
| CT-E2E-ATE-006 | [CT-E2E-ATE-006.txt](execucoes/CT-E2E-ATE-006.txt) | [falha 1](7.5%20Atendimentos/JUNIT-ATE-006-falha-1.png) · [falha 2](7.5%20Atendimentos/JUNIT-ATE-006-falha-2.png) · [console 1](7.5%20Atendimentos/JUNIT-ATE-006-console-1.png) · [console 2](7.5%20Atendimentos/JUNIT-ATE-006-console-2.png) |
| CT-E2E-ATE-007 | [CT-E2E-ATE-007.txt](execucoes/CT-E2E-ATE-007.txt) | [falha](7.5%20Atendimentos/JUNIT-ATE-007-falha.png) · [console 1](7.5%20Atendimentos/JUNIT-ATE-007-console-1.png) · [console 2](7.5%20Atendimentos/JUNIT-ATE-007-console-2.png) |
| CT-E2E-ATE-008 | [CT-E2E-ATE-008.txt](execucoes/CT-E2E-ATE-008.txt) | [falha](7.5%20Atendimentos/JUNIT-ATE-008-falha.png) · [console 1](7.5%20Atendimentos/JUNIT-ATE-008-console-1.png) · [console 2](7.5%20Atendimentos/JUNIT-ATE-008-console-2.png) |

---


## 8. Resultados dos testes

Resumo: **36 testes — 24 aprovados e 12 reprovados.** Reprovado significa que o sistema não se comportou como esperado (ver critérios na seção 10); cada reprovação está ligada a um defeito da seção 9.

### 8.1 Sistema e autenticação

| ID | Caso | Resultado observado | Status |
|---|---|---|---|
| CT-E2E-001 | Inicializar com banco disponível | Banco conectado e tela de login exibida | Aprovado |
| CT-E2E-002 | Login válido | Login realizado e menu principal exibido | Aprovado |
| CT-E2E-003 | Encerrar o sistema | Mensagem de saída e `exit code 0` | Aprovado |
| CT-E2E-004 | Login com senha inválida *(novo)* | Acesso negado ("Login ou senha incorretos."), sem exibir o menu | Aprovado |
| CT-E2E-005 | Login com campos vazios *(novo)* | Acesso negado ("O login é obrigatório."), sem exibir o menu | Aprovado |

### 8.2 Clientes

| ID | Caso | Resultado observado | Status |
|---|---|---|---|
| CT-E2E-CLI-001 | Cadastrar cliente válido | Cliente cadastrado, listado e gravado no banco | Aprovado |
| CT-E2E-CLI-002 | Cadastrar placa inválida | Cadastro bloqueado; nada gravado no banco | Aprovado |
| CT-E2E-CLI-003 | Atualizar com campos vazios | Atualização bloqueada; dados mantidos no banco | Aprovado |
| CT-E2E-CLI-004 | Atualizar cliente válido | Dados atualizados no banco e na consulta | Aprovado |
| CT-E2E-CLI-005 | Excluir cliente sem atendimento | Cliente removido do banco | Aprovado |
| CT-E2E-CLI-006 | Excluir cliente com atendimento | Banco bloqueou a exclusão, mas o usuário viu a mensagem técnica do MySQL | Reprovado quanto ao tratamento |
| CT-E2E-CLI-007 | Excluir ID inexistente | Sistema exibiu "Cliente removido!" para um ID que não existe | Reprovado |

### 8.3 Funcionários

| ID | Caso | Resultado observado | Status |
|---|---|---|---|
| CT-E2E-FUN-001 | Cadastrar funcionário válido | Funcionário cadastrado, listado e gravado no banco | Aprovado |
| CT-E2E-FUN-002 | Cadastro sem nome | Cadastro bloqueado; nada gravado no banco | Aprovado |
| CT-E2E-FUN-003 | Cadastro sem cargo | Cadastro bloqueado; nada gravado no banco | Aprovado |
| CT-E2E-FUN-004 | Cadastro sem telefone | Cadastro bloqueado; nada gravado no banco | Aprovado |
| CT-E2E-FUN-005 | Atualizar funcionário válido | Dados atualizados no banco | Aprovado |
| CT-E2E-FUN-006 | Excluir sem atendimento relacionado | Funcionário removido do banco | Aprovado |
| CT-E2E-FUN-007 | Excluir com atendimento relacionado | Banco bloqueou a exclusão, mas o usuário viu a mensagem técnica do MySQL | Reprovado quanto ao tratamento |

### 8.4 Serviços

| ID | Caso | Resultado observado | Status |
|---|---|---|---|
| CT-E2E-SER-001 | Cadastrar serviço válido | Serviço cadastrado, listado e gravado com o valor informado | Aprovado |
| CT-E2E-SER-002 | Cadastro sem nome | Cadastro bloqueado; nada gravado no banco | Aprovado |
| CT-E2E-SER-003 | Cadastro com valor zero | Cadastro bloqueado; nada gravado no banco | Aprovado |
| CT-E2E-SER-004 | Entrada não numérica no valor | `InputMismatchException` encerrou o programa (`exit code 1`) | Reprovado |
| CT-E2E-SER-005 | Atualizar serviço válido | Dados atualizados no banco | Aprovado |
| CT-E2E-SER-006 | Excluir serviço sem vínculo | Serviço removido do banco | Aprovado |
| CT-E2E-SER-007 | Excluir serviço com vínculo | Banco bloqueou a exclusão, mas o usuário viu a mensagem técnica do MySQL | Reprovado quanto ao tratamento |
| CT-E2E-SER-008 | Excluir ID inexistente | Sistema exibiu "Serviço removido!" para um ID que não existe | Reprovado |
| CT-E2E-SER-009 | Valor no formato do exemplo da tela (49.90) em computador pt-BR *(novo)* | `InputMismatchException` encerrou o programa (`exit code 1`); nada gravado | Reprovado |

### 8.5 Atendimentos

| ID | Caso | Resultado observado | Status |
|---|---|---|---|
| CT-E2E-ATE-001 | Listar atendimentos | Registros exibidos | Aprovado |
| CT-E2E-ATE-002 | Cadastrar atendimento | `NullPointerException` ao vincular o serviço; programa encerrou (`exit code 1`) e o serviço não foi vinculado | Reprovado |
| CT-E2E-ATE-003 | Atualizar atendimento válido | Cliente, funcionário e observação atualizados no banco | Aprovado |
| CT-E2E-ATE-004 | Atualizar relacionamento inválido | Banco bloqueou a operação, mas o usuário viu a mensagem técnica do MySQL | Reprovado quanto ao tratamento |
| CT-E2E-ATE-005 | Excluir atendimento sem dependência | Atendimento removido do banco | Aprovado |
| CT-E2E-ATE-006 | Excluir com serviço associado | Banco bloqueou a exclusão, mas o usuário viu a mensagem técnica do MySQL | Reprovado quanto ao tratamento |
| CT-E2E-ATE-007 | Entrada inválida (texto no lugar do ID) | `InputMismatchException` encerrou o programa | Reprovado |
| CT-E2E-ATE-008 | Cadastro não deixa registro parcial *(novo)* | Atendimento gravado sem nenhum serviço vinculado (1 atendimento, 0 vínculos) | Reprovado |

---

## 9. Defeitos encontrados

### DEF-E2E-001 — Mensagens técnicas do banco

Ao excluir ou atualizar registros relacionados, o usuário recebe mensagens do MySQL, como `Cannot delete or update a parent row` ou `Cannot add or update a child row`. O banco protege os dados corretamente (o registro permanece), mas o sistema repassa o texto técnico ao usuário.

- Impacto: dificulta o entendimento do usuário.
- Severidade: média.
- Prioridade: média.
- Comportamento esperado: apresentar mensagem amigável explicando o relacionamento existente.
- Casos de teste reprovados: CT-E2E-CLI-006, CT-E2E-FUN-007, CT-E2E-SER-007, CT-E2E-ATE-004, CT-E2E-ATE-006.

![DEF-E2E-001 — Mensagem técnica do banco ao excluir cliente relacionado](7.2%20Clientes/E2E-CLI-006-restricao-exclusao.png)

Prints da execução automatizada:

- CT-E2E-CLI-006: [falha 1](7.2%20Clientes/JUNIT-CLI-006-falha-1.png) · [falha 2](7.2%20Clientes/JUNIT-CLI-006-falha-2.png) · [console 1](7.2%20Clientes/JUNIT-CLI-006-console-1.png) · [console 2](7.2%20Clientes/JUNIT-CLI-006-console-2.png)
- CT-E2E-FUN-007: [falha 1](7.3%20Funcion%C3%A1rios/JUNIT-FUN-007-falha-1.png) · [falha 2](7.3%20Funcion%C3%A1rios/JUNIT-FUN-007-falha-2.png) · [console 1](7.3%20Funcion%C3%A1rios/JUNIT-FUN-007-console-1.png) · [console 2](7.3%20Funcion%C3%A1rios/JUNIT-FUN-007-console-2.png)
- CT-E2E-SER-007: [falha 1](7.4%20Servi%C3%A7os/JUNIT-SER-007-falha-1.png) · [falha 2](7.4%20Servi%C3%A7os/JUNIT-SER-007-falha-2.png) · [console 1](7.4%20Servi%C3%A7os/JUNIT-SER-007-console-1.png) · [console 2](7.4%20Servi%C3%A7os/JUNIT-SER-007-console-2.png)
- CT-E2E-ATE-004: [falha 1](7.5%20Atendimentos/JUNIT-ATE-004-falha-1.png) · [falha 2](7.5%20Atendimentos/JUNIT-ATE-004-falha-2.png) · [console 1](7.5%20Atendimentos/JUNIT-ATE-004-console-1.png) · [console 2](7.5%20Atendimentos/JUNIT-ATE-004-console-2.png)
- CT-E2E-ATE-006: [falha 1](7.5%20Atendimentos/JUNIT-ATE-006-falha-1.png) · [falha 2](7.5%20Atendimentos/JUNIT-ATE-006-falha-2.png) · [console 1](7.5%20Atendimentos/JUNIT-ATE-006-console-1.png) · [console 2](7.5%20Atendimentos/JUNIT-ATE-006-console-2.png)

### DEF-E2E-002 — Sucesso informado para ID inexistente

Ao tentar excluir um ID que não existia, o sistema exibiu mensagem de remoção bem-sucedida.

- Impacto: o usuário recebe informação incorreta.
- Severidade: média.
- Prioridade: média.
- Comportamento esperado: informar que nenhum registro foi encontrado.
- Casos de teste reprovados: CT-E2E-CLI-007, CT-E2E-SER-008.

![DEF-E2E-002 — Sucesso informado para ID de cliente inexistente](7.2%20Clientes/E2E-CLI-007-exclusao-id-inexistente.png)

Prints da execução automatizada:

- CT-E2E-CLI-007: [falha 1](7.2%20Clientes/JUNIT-CLI-007-falha-1.png) · [falha 2](7.2%20Clientes/JUNIT-CLI-007-falha-2.png) · [falha 3](7.2%20Clientes/JUNIT-CLI-007-falha-3.png) · [console 1](7.2%20Clientes/JUNIT-CLI-007-console-1.png) · [console 2](7.2%20Clientes/JUNIT-CLI-007-console-2.png)
- CT-E2E-SER-008: [falha 1](7.4%20Servi%C3%A7os/JUNIT-SER-008-falha-1.png) · [falha 2](7.4%20Servi%C3%A7os/JUNIT-SER-008-falha-2.png) · [falha 3](7.4%20Servi%C3%A7os/JUNIT-SER-008-falha-3.png) · [console 1](7.4%20Servi%C3%A7os/JUNIT-SER-008-console-1.png) · [console 2](7.4%20Servi%C3%A7os/JUNIT-SER-008-console-2.png)

### DEF-E2E-003 — Entrada numérica inválida encerra o programa

Ao informar texto em campo numérico, ocorre `InputMismatchException` e o processo termina com `exit code 1`. Foi reproduzido no valor do serviço e no ID do atendimento.

- Impacto: interrompe o uso do sistema.
- Severidade: alta.
- Prioridade: alta.
- Comportamento esperado: solicitar nova entrada sem encerrar a aplicação.
- Casos de teste reprovados: CT-E2E-SER-004, CT-E2E-ATE-007.

![DEF-E2E-003 — InputMismatchException ao cadastrar serviço](7.4%20Servi%C3%A7os/E2E-SER-004-valor-invalido.png)

Prints da execução automatizada:

- CT-E2E-SER-004: [falha](7.4%20Servi%C3%A7os/JUNIT-SER-004-falha.png) · [console 1](7.4%20Servi%C3%A7os/JUNIT-SER-004-console-1.png) · [console 2](7.4%20Servi%C3%A7os/JUNIT-SER-004-console-2.png)
- CT-E2E-ATE-007: [falha](7.5%20Atendimentos/JUNIT-ATE-007-falha.png) · [console 1](7.5%20Atendimentos/JUNIT-ATE-007-console-1.png) · [console 2](7.5%20Atendimentos/JUNIT-ATE-007-console-2.png)

### DEF-E2E-004 — Cadastro parcial de atendimento

O atendimento principal é salvo antes da falha na associação dos serviços, ficando gravado sem nenhum serviço vinculado (no teste: 1 atendimento e 0 vínculos).

- Impacto: pode deixar dados incompletos no banco.
- Severidade: alta.
- Prioridade: alta.
- Comportamento esperado: desfazer a operação quando uma etapa falhar.
- Casos de teste reprovados: CT-E2E-ATE-008.

![DEF-E2E-004 — Persistência parcial do atendimento](7.5%20Atendimentos/E2E-ATE-002-atendimento-excecao.png)

Prints da execução automatizada:

- CT-E2E-ATE-008: [falha](7.5%20Atendimentos/JUNIT-ATE-008-falha.png) · [console 1](7.5%20Atendimentos/JUNIT-ATE-008-console-1.png) · [console 2](7.5%20Atendimentos/JUNIT-ATE-008-console-2.png)

### DEF-E2E-005 — NullPointerException no cadastro de atendimento

O cadastro dos serviços associados gera `NullPointerException` porque o atendimento associado ao objeto `AtendimentoServico` está nulo.

- Impacto: impede a conclusão do fluxo.
- Severidade: alta.
- Prioridade: alta.
- Comportamento esperado: preencher e validar o relacionamento antes da inserção.
- Casos de teste reprovados: CT-E2E-ATE-002.

![DEF-E2E-005 — NullPointerException na associação de serviços](7.5%20Atendimentos/E2E-ATE-002-atendimento-excecao.png)

Prints da execução automatizada:

- CT-E2E-ATE-002: [falha 1](7.5%20Atendimentos/JUNIT-ATE-002-falha-1.png) · [falha 2](7.5%20Atendimentos/JUNIT-ATE-002-falha-2.png) · [falha 3](7.5%20Atendimentos/JUNIT-ATE-002-falha-3.png) · [console 1](7.5%20Atendimentos/JUNIT-ATE-002-console-1.png) · [console 2](7.5%20Atendimentos/JUNIT-ATE-002-console-2.png)

### DEF-E2E-006 — Listagem usa IDs em vez de nomes

A listagem de atendimentos apresenta apenas `ID Cliente` e `ID Func.`. Observado na execução manual; o teste automatizado CT-E2E-ATE-001 (listagem) foi aprovado porque confere os dados exibidos, e não exige nomes na tela.

- Impacto: exige consultas adicionais para identificar as pessoas.
- Severidade: baixa.
- Prioridade: baixa.
- Comportamento esperado: exibir também os nomes do cliente e do funcionário.

![DEF-E2E-006 — Listagem de atendimentos exibindo apenas IDs](7.5%20Atendimentos/E2E-ATE-001-listagem.png)

### DEF-E2E-007 — Valor com ponto decimal encerra o programa (novo)

A tela pede `Valor (ex: 49.90)`, mas o `Scanner` usa o separador decimal do idioma do computador. Em um computador com idioma português (Brasil), digitar `49.90` gera `InputMismatchException` e o programa termina com `exit code 1`, enquanto `49,90` é aceito. A atualização de serviço usa a mesma leitura numérica.

- Impacto: quem segue o exemplo mostrado na própria tela derruba o sistema e perde o que estava fazendo.
- Severidade: alta.
- Prioridade: alta.
- Comportamento esperado: aceitar os dois formatos ou ajustar o texto da tela para o formato realmente aceito, sem encerrar o programa.
- Casos de teste reprovados: CT-E2E-SER-009.

![DEF-E2E-007 — Valor com ponto encerra o programa](7.4%20Servi%C3%A7os/E2E-SER-009-valor-com-ponto.png)

Prints da execução automatizada:

- CT-E2E-SER-009: [falha 1](7.4%20Servi%C3%A7os/JUNIT-SER-009-falha-1.png) · [falha 2](7.4%20Servi%C3%A7os/JUNIT-SER-009-falha-2.png) · [falha 3](7.4%20Servi%C3%A7os/JUNIT-SER-009-falha-3.png) · [console 1](7.4%20Servi%C3%A7os/JUNIT-SER-009-console-1.png) · [console 2](7.4%20Servi%C3%A7os/JUNIT-SER-009-console-2.png)

Prova de que o valor com vírgula funciona (execução manual):

![DEF-E2E-007 — Valor com vírgula é aceito](7.4%20Servi%C3%A7os/E2E-SER-009-valor-com-virgula.png)

---


## 10. Critérios de avaliação

Um caso foi considerado aprovado quando o resultado observado correspondeu ao esperado, incluindo a rejeição correta de entradas inválidas e a conferência do que ficou (ou não) gravado no banco.

Um caso foi considerado reprovado quando ocorreu exceção não tratada, o sistema informou sucesso sem realizar a operação, houve persistência parcial, a mensagem foi insuficiente ou o fluxo não pôde ser concluído.

---

## 11. Conclusão

Os testes E2E, agora automatizados com JUnit 5, permitiram avaliar o sistema de ponta a ponta pela CLI, verificando a integração entre a interação do usuário, as regras de negócio, a persistência no MySQL e os relacionamentos entre as entidades. Dos 36 testes executados em 30/09/2026, 24 foram aprovados e 12 reprovados.

Os fluxos principais de autenticação, clientes, funcionários e serviços apresentaram funcionamento satisfatório em diversas situações positivas e negativas, e as validações de campos obrigatórios, de placa e de valor funcionaram como esperado. Foram identificadas falhas relevantes no tratamento de entradas inválidas (incluindo o formato decimal), na comunicação de erros do banco, na resposta a IDs inexistentes e no cadastro de atendimentos, que está quebrado e deixa dados parciais no banco.

Como a atividade solicitou o registro das falhas, os problemas encontrados foram documentados sem alteração do código durante esta etapa. Como os testes ficaram automatizados, eles podem ser executados novamente após as correções, servindo como testes de regressão: quando um defeito for corrigido, o teste correspondente deve passar a aprovar.

---

## 12. Organização das evidências

```
evidencias/
├── relatorio-junit.html
├── JUNIT-arvore-completa-1.png
├── JUNIT-arvore-completa-2.png
├── execucoes/                 (36 arquivos .txt, um por caso: CT-E2E-xxx.txt)
├── 7.1 Sistema e autenticação/
├── 7.2 Clientes/
├── 7.3 Funcionários/
├── 7.4 Serviços/
└── 7.5 Atendimentos/
```

Padrão dos nomes dos prints:

- `E2E-<ID>-<descrição>.png`: execução manual de 15/09/2026.
- `JUNIT-<ID>-falha[-n].png`: teste reprovado selecionado na árvore do IntelliJ, com a mensagem de falha.
- `JUNIT-<ID>-console[-n].png`: arquivo de `execucoes/` mostrando o que foi digitado e o que apareceu no console (quando o arquivo não cabe em um print, há uma parte 1 e uma parte 2).

O `<ID>` é o identificador do caso sem o prefixo `CT-E2E-` (por exemplo, `CLI-006`). A pasta de cada print segue o prefixo do caso: `CLI` em 7.2, `FUN` em 7.3, `SER` em 7.4 e `ATE` em 7.5; os casos sem prefixo ficam em 7.1.
