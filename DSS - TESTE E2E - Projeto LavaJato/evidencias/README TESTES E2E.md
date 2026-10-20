# Testes End-to-End — Sistema de Gerenciamento de Lava-Jato

> Documento de planejamento, descrição dos casos, execução e resultados dos testes E2E do sistema, executados de forma **automatizada com JUnit 5** pela interface CLI (a primeira rodada, manual, foi mantida como evidência complementar).

---

## 1. Identificação

| Campo | Informação |
|---|---|
| Projeto | Sistema de Gerenciamento de Lava-Jato |
| Grupo | João Víctor Cambraia Nogueira, João Gabriel dos Santos Felipe, Maria Fernanda da Silva Nogueira, Luís Pereira Costa e Pedro Rodrigues Teixeira |
| Responsável por esta parte (E2E) | João Gabriel dos Santos Felipe |
| Versão do documento | 2.0 (15/10/2026) |
| Tipo de teste | End-to-End (E2E), dinâmico, caixa-preta com conferência no banco |
| Ferramenta | JUnit 5 (JUnit Jupiter 5.14.0), executado pelo IntelliJ IDEA |
| Interface | CLI — o sistema é executado de verdade (`applications.Main`) e controlado pelo teclado simulado |
| Banco de dados | MySQL |
| Casos de teste | 36 (32 da primeira entrega e 4 novos, marcados com *(novo)*) |
| Execução | Automatizada (36 testes) |
| Execução inicial | Manual, em 15/09/2026, usada para levantar os casos e os defeitos |
| Data da execução automatizada | 30/09/2026 |
| Resultado | **36 testes: 24 aprovados e 12 reprovados** (cada reprovação corresponde a um defeito registrado na seção 9) |
| Código dos testes no repositório do grupo | `src/test/java/e2e/` (Maven) |
| Cópia dos testes neste repositório pessoal | `Projeto_LavaJato_v3/test/e2e/` (estrutura antiga) |
| Repositório oficial do grupo (código) | https://github.com/PedroRodriguesTeixeira/Projeto-Lava-Jato |
| Testes E2E e evidências | https://github.com/codebygabes/Studying-and-Coding/tree/main/DSS%20-%20TESTE%20E2E%20-%20Projeto%20LavaJato |

### 1.1 Histórico de versões

| Versão | Data | Descrição |
|---|---|---|
| 1.0 | 15/09/2026 | Execução manual pela CLI, levantamento dos casos e registro dos defeitos. |
| 1.1 | 30/09/2026 | Automação dos testes com JUnit 5, relatório exportado e novas evidências. |
| 2.0 | 15/10/2026 | Descrição completa dos casos (passos, dados de entrada, saída esperada e critério de sucesso), estratégia de testes, dados-base, papéis, riscos, rastreabilidade, implementado × previsto, lições aprendidas e links do repositório. |

---

## 2. Objetivo e situação do sistema

### 2.1 Objetivo

Validar o funcionamento do sistema a partir da perspectiva do usuário, percorrendo os fluxos completos da aplicação. Os testes verificam a integração entre a CLI, os serviços, os DAOs, a conexão JDBC e o banco de dados MySQL.

O foco não é analisar métodos isolados, mas verificar se o usuário consegue iniciar o sistema, autenticar-se, acessar os menus e realizar operações de cadastro, consulta, atualização e exclusão. Em cada teste, o resultado é conferido de duas formas: pelo que aparece no console e pelo que realmente ficou gravado no banco.

### 2.2 O que está implementado e o que é previsão

O Documento de Visão descreve o sistema completo. Para ficar claro o que existe hoje no código e o que é apenas previsão, e o que estes testes cobrem:

| Item | Situação | Coberto por estes testes E2E? |
|---|---|---|
| Login por usuário e senha na CLI | Implementado | Sim |
| CRUD de clientes, funcionários, serviços e atendimentos pela CLI | Implementado | Sim |
| Persistência em MySQL (DAOs e JDBC) | Implementado | Sim (conferida por consulta direta ao banco) |
| API HTTP em `localhost:8080` com 7 endpoints (login e listar/cadastrar clientes, funcionários e serviços) | Implementado | Não (tem plano próprio, de testes de API) |
| CLI se comunicando com o backend pela API (seções 3.4 e 4.1 da visão) | Previsão: hoje a CLI chama os Services diretamente | Não se aplica (o E2E percorre CLI → Services → DAOs → MySQL) |
| Endpoints de atualizar, excluir e de atendimentos na API | Previsão: ainda não existem | Não |

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

## 6. Planejamento, funcionamento e casos de teste

### 6.1 Estratégia de testes

- **Nível e método:** teste End-to-End, dinâmico e caixa-preta: o teste enxerga apenas o que o usuário enxerga (o console). Além da tela, o estado do banco também é conferido por consulta direta ao MySQL.
- **Técnicas:** partição de equivalência (dado válido, inválido e vazio), valor limite (ID inexistente, valor zero, texto no lugar de número) e casos de integridade referencial (excluir registro com e sem dependência).
- **Tipos de caso:** positivos (fluxo normal), negativos (entrada inválida deve ser rejeitada), de integridade (relacionamentos entre as tabelas) e de robustez (entradas fora do esperado).
- **Resultado esperado:** vem do Documento de Visão (RF, HU e regras de negócio da seção 4.5) e, para os textos das mensagens de validação, do comportamento dos Services, porque a visão não define as mensagens. Não vem do que o sistema faz hoje; por isso há testes que reprovam.
- **Isolamento e repetibilidade:** o banco é restaurado a um estado conhecido antes de cada teste (seção 6.2) e o idioma do sistema é fixado em inglês, exceto no caso SER-009. Assim, uma falha não contamina os demais testes e o resultado não depende do computador.
- **Critério de entrada:** MySQL ligado, banco `lavajato` criado pelo script `crud_lavajato.sql`, projeto compilando e JUnit 5 no classpath.
- **Critério de aceitação de cada caso:** aprovado quando todas as verificações do caso são verdadeiras (mensagem esperada, código de saída e estado do banco); reprovado quando alguma falha ou quando ocorre exceção não tratada (ver também a seção 10).
- **Critério de saída:** os 36 casos executados, relatório exportado, evidências salvas e todos os defeitos registrados.

### 6.2 Dados-base

Antes de cada teste, o banco `lavajato` é apagado e recriado com estes dados. Os casos que precisam de um registro sem dependência (CLI-005, FUN-006, SER-006 e ATE-005) criam esse registro direto no banco antes de executar o sistema.

| Tabela | Registros |
|---|---|
| usuario | `admin` (senha do script SQL) |
| cliente | 1 `Joao` (placa `ABC1234`, Civic, Honda); 2 `Maria` (placa `DFG5678`, Celta, Chevrolet); 3 `Pedro` (placa `HIJ9012`, HB20, Hyundai) |
| funcionario | 1 `Carlos` (Lavador); 2 `Jose` (Lavador) |
| servico | 1 `Lavagem Completa` (R$ 50,00); 2 `Lavagem Parcial` (R$ 35,00) |
| atendimento | 1 (cliente 1, funcionário 1, serviço 1); 2 (cliente 2, funcionário 2, serviço 2); 3 (cliente 3, funcionário 1, serviço 2); observações `Primeiro`, `Segundo` e `Terceiro atendimento teste` |

### 6.3 Como os testes funcionam

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

### 6.4 Procedimento de execução

1. Iniciar o MySQL Server com o banco `lavajato` criado a partir do script `crud_lavajato.sql`. **Atenção:** os testes apagam e recriam os dados desse banco.
2. Abrir o projeto no IntelliJ IDEA com a pasta `test` marcada como *Test Sources Root* e o JUnit 5 no classpath.
3. Clicar com o botão direito na pasta `test` e escolher *Run 'All Tests'*.
4. Aguardar a execução (cerca de 28 segundos) e conferir a árvore de resultados.
5. Para cada teste reprovado, selecionar o teste na árvore e capturar a mensagem de falha; abrir o arquivo correspondente em `evidencias/execucoes/` e capturar o console.
6. Exportar o relatório pelo menu *Export Test Results* (formato HTML).
7. Salvar os prints nas subpastas de `evidencias`, uma por seção.

> **Atenção:** prints tirados do computador pessoal do autor.

### 6.5 Papéis e responsabilidades

| Papel | Responsabilidades | Responsável |
|---|---|---|
| Líder de testes | Define escopo e critérios, acompanha prazos e consolida os resultados. | João Víctor Cambraia Nogueira |
| Projetista de casos | Deriva os casos dos requisitos e define dados de entrada, saída esperada e critério de sucesso. | João Gabriel dos Santos Felipe |
| Executor | Implementa e executa os testes automatizados e mantém o ambiente (MySQL e projeto). | João Gabriel dos Santos Felipe |
| Evidências e defeitos | Guarda prints, logs e relatório, e registra os defeitos (DEF-E2E). | João Gabriel dos Santos Felipe |
| Revisor | Confere os casos, os resultados e a coerência com os planos de testes unitários e de API. | Maria Fernanda da Silva Nogueira |

### 6.6 Riscos

| Risco | Impacto | Mitigação |
|---|---|---|
| O banco `lavajato` é compartilhado com a CLI e com a API, e o E2E o apaga e recria | Dados e resultados de outros testes podem ser perdidos | Executar o E2E em horário combinado, sem outros testes usando o banco; fazer backup se necessário |
| O `Scanner` depende do idioma do computador (separador decimal) | O mesmo valor numérico pode funcionar em uma máquina e falhar em outra | Fixar o idioma nos testes e manter um caso próprio que reproduz o defeito (SER-009) |
| A visão não define as mensagens de validação nem o formato da placa | Resultado esperado ambíguo em alguns casos negativos | Basear a expectativa nos Services e registrar divergências como defeito |
| Defeitos do código podem mascarar casos seguintes | Falhas em cadeia | Cada teste restaura o banco e executa isolado |
| O grupo pode alterar o código para corrigir defeitos | Resultados e evidências ficam desatualizados | Reexecutar a suíte e atualizar relatório, evidências e este documento |

### 6.7 Rastreabilidade

Todos os requisitos funcionais da visão (RF01 a RF13) têm pelo menos um caso de teste. Os casos aparecem sem o prefixo `CT-E2E-` (por exemplo, `CLI-001`; os de autenticação são `001` a `005`).

| Requisito (visão) | Descrição | Casos |
|---|---|---|
| RF01 | Autenticação por login e senha | 002, 004, 005 |
| RF02 | Registro de clientes | CLI-001, CLI-002 |
| RF03 | Consulta de clientes | CLI-001 |
| RF04 | Atualização de clientes | CLI-003, CLI-004 |
| RF05 | Deleção de clientes | CLI-005, CLI-006, CLI-007 |
| RF06 | Registro de serviços | SER-001, SER-002, SER-003, SER-004, SER-009 |
| RF07 | Consulta de serviços | SER-001 |
| RF08 | Atualização de serviços | SER-005 |
| RF09 | Deleção de serviços | SER-006, SER-007, SER-008 |
| RF10 | Registro de atendimentos | FUN-001, FUN-002, FUN-003, FUN-004, FUN-005, FUN-006, ATE-002, ATE-008 |
| RF11 | Consulta de atendimentos | ATE-001 |
| RF12 | Atualização de atendimentos | ATE-003, ATE-004 |
| RF13 | Deleção de atendimentos | ATE-005, ATE-006, ATE-007 |
| RNF01 | Banco de dados para persistência | 001 |
| RNF02 | Interface inicial por linha de comando (CLI) | 001, 003 |

### 6.8 Descrição dos casos de teste

São 36 casos: os 32 da primeira entrega e 4 novos (CT-E2E-004, CT-E2E-005, CT-E2E-ATE-008 e CT-E2E-SER-009, marcados com *(novo)*). Cada caso tem identificador, descrição, passos com os dados de entrada, saída esperada e critério de sucesso. A linha em itálico abaixo do ID indica o requisito da visão que o caso verifica; quando o caso reprova, aparece o defeito correspondente (seção 9).

**Convenções dos passos:** todos os casos partem do sistema iniciado (`applications.Main`) com o banco nos dados-base (6.2); os números entre crases são as opções digitadas nos menus; ao final de cada caso, o teste volta ao menu principal e sai (`0`).

#### 6.8.1 Sistema e autenticação

| ID | Descrição | Passos e dados de entrada | Saída esperada | Critério de sucesso |
|---|---|---|---|---|
| **CT-E2E-001**<br>*RNF01; RNF02* | Iniciar o sistema com o MySQL disponível: a conexão é feita antes da tela de login. | 1. Iniciar `applications.Main` com o MySQL ligado<br>2. Login: `admin` / `1234`<br>3. Menu principal → `0` (Sair) | Mensagem `Banco de dados conectado com sucesso!` e tela `LOGIN - LAVA-JATO`. | Passa se as duas mensagens aparecem no console. |
| **CT-E2E-002**<br>*RF01; HU01 (cenário 1)* | Login com credenciais válidas libera o menu principal. | 1. Iniciar o sistema<br>2. Login: `admin` / `1234`<br>3. Menu principal → `0` (Sair) | `Login realizado com sucesso!` seguido do menu `SISTEMA LAVA-JATO - MENU`. | Passa se a mensagem de sucesso e o menu principal aparecem. |
| **CT-E2E-003**<br>*RNF02* | Encerrar o sistema pelo menu principal. | 1. Iniciar o sistema<br>2. Login: `admin` / `1234`<br>3. Menu principal → `0` (Sair) | Mensagem de despedida (`Saindo do sistema`) e encerramento normal do programa. | Passa se a mensagem aparece e o código de saída do processo é 0. |
| **CT-E2E-004**<br>*RF01; HU01 (cenário 2)* | Login com senha incorreta nega o acesso. *(novo)* | 1. Iniciar o sistema<br>2. Informar login `admin` e senha `senha-errada` | Mensagem `Login ou senha incorretos.`; o menu principal não é exibido; o programa encerra normalmente. | Passa se a mensagem aparece, o menu principal não aparece e o código de saída é 0. |
| **CT-E2E-005**<br>*RF01; HU01* | Login e senha vazios são rejeitados. *(novo)* | 1. Iniciar o sistema<br>2. Informar login vazio e senha vazia (apenas Enter) | Mensagem `O login é obrigatório.`; o menu principal não é exibido; o programa encerra normalmente. | Passa se a mensagem aparece, o menu principal não aparece e o código de saída é 0. |

#### 6.8.2 Clientes

| ID | Descrição | Passos e dados de entrada | Saída esperada | Critério de sucesso |
|---|---|---|---|---|
| **CT-E2E-CLI-001**<br>*RF02; RF03; HU02 (cenário 1); HU03 (cenário 1)* | Cadastrar um cliente válido e consultá-lo na listagem. | 1. Login: `admin` / `1234`<br>2. Menu principal → `1` (Clientes); submenu → `1` (Cadastrar)<br>3. Informar: nome `Ana Souza`, telefone `61999990000`, placa `QWE1R23`, modelo `Onix`, marca `Chevrolet`<br>4. Submenu → `2` (Listar) | `Cliente cadastrado!`; `Ana Souza` aparece na listagem; o registro existe no banco. | Passa se a mensagem e a listagem aparecem e há exatamente 1 cliente com a placa `QWE1R23` no banco. |
| **CT-E2E-CLI-002**<br>*RF02; HU02* | Placa fora dos formatos aceitos é rejeitada. | 1. Login: `admin` / `1234`<br>2. Menu principal → `1` (Clientes); submenu → `1` (Cadastrar)<br>3. Informar: nome `Ana Souza`, telefone `61999990000`, placa `123`, modelo `Onix`, marca `Chevrolet` | Mensagem `Placa inválida` (formatos `ABC1234` ou `ABC1D23`); cadastro não concluído. | Passa se a mensagem aparece, `Cliente cadastrado!` não aparece e nenhum cliente `Ana Souza` é gravado. |
| **CT-E2E-CLI-003**<br>*RF04; HU04* | Atualização com campos obrigatórios vazios é bloqueada. | 1. Login: `admin` / `1234`<br>2. Menu principal → `1` (Clientes); submenu → `3` (Atualizar)<br>3. Informar o ID `2`<br>4. Deixar vazios nome, telefone, placa, modelo e marca (Enter em cada campo) | Mensagem `O nome do cliente é obrigatório.`; o cliente não é alterado. | Passa se a mensagem aparece, `Cliente atualizado!` não aparece e o cliente 2 continua com o nome `Maria`. |
| **CT-E2E-CLI-004**<br>*RF04; HU04 (cenário 1)* | Atualizar os dados de um cliente existente e conferir na consulta. | 1. Login: `admin` / `1234`<br>2. Menu principal → `1` (Clientes); submenu → `3` (Atualizar)<br>3. ID `2`; novos dados: nome `Maria Silva`, telefone `61911112222`, placa `DFG5678`, modelo `Celta`, marca `Chevrolet`<br>4. Submenu → `2` (Listar) | `Cliente atualizado!`; a listagem mostra `Maria Silva`. | Passa se a mensagem aparece, `Maria Silva` aparece na listagem e o banco guarda o novo nome. |
| **CT-E2E-CLI-005**<br>*RF05; HU05 (cenário 1)* | Excluir um cliente que não possui atendimento. | 1. Preparação: cliente `Sem Atendimento` (placa `ZZZ9999`) inserido direto no banco<br>2. Login: `admin` / `1234`<br>3. Menu principal → `1` (Clientes); submenu → `4` (Excluir)<br>4. Informar o ID desse cliente | `Cliente removido!`; o cliente deixa de existir. | Passa se a mensagem aparece e o cliente não existe mais no banco. |
| **CT-E2E-CLI-006**<br>*RF05; HU05; regra 4.5 (cliente com atendimentos)*<br>→ DEF-E2E-001 | Excluir um cliente que já tem atendimento (restrição de integridade): o cliente deve ser preservado e o usuário deve receber mensagem compreensível. | 1. Login: `admin` / `1234`<br>2. Menu principal → `1` (Clientes); submenu → `4` (Excluir)<br>3. Informar o ID `1` (cliente com atendimento) | O cliente continua no banco e o sistema explica, em linguagem simples, que ele possui atendimentos (sem texto técnico do MySQL). | Passa se o cliente 1 continua no banco e a saída não contém mensagem técnica (`Cannot delete or update`, `foreign key`, `constraint`). |
| **CT-E2E-CLI-007**<br>*RF05; HU05*<br>→ DEF-E2E-002 | Excluir um ID que não existe. | 1. Login: `admin` / `1234`<br>2. Menu principal → `1` (Clientes); submenu → `4` (Excluir)<br>3. Informar o ID `9999` | Mensagem informando que nenhum registro foi encontrado; não pode aparecer `Cliente removido!`. | Passa se `Cliente removido!` não aparece e a saída informa que o registro não foi encontrado. |

#### 6.8.3 Funcionários

| ID | Descrição | Passos e dados de entrada | Saída esperada | Critério de sucesso |
|---|---|---|---|---|
| **CT-E2E-FUN-001**<br>*Sem RF específico (base do RF10)* | Cadastrar um funcionário válido e consultá-lo na listagem. | 1. Login: `admin` / `1234`<br>2. Menu principal → `2` (Funcionários); submenu → `1` (Cadastrar)<br>3. Informar: nome `Marcos Lima`, cargo `Lavador`, telefone `61988880000`<br>4. Submenu → `2` (Listar) | `Funcionário cadastrado!`; `Marcos Lima` aparece na listagem; o registro existe no banco. | Passa se a mensagem e a listagem aparecem e há exatamente 1 funcionário `Marcos Lima` no banco. |
| **CT-E2E-FUN-002**<br>*Sem RF específico (base do RF10)* | Cadastro sem nome é bloqueado. | 1. Login: `admin` / `1234`<br>2. Menu principal → `2` (Funcionários); submenu → `1` (Cadastrar)<br>3. Informar: nome vazio, cargo `Lavador`, telefone `61988880000` | Mensagem `O nome do funcionário é obrigatório.` | Passa se a mensagem aparece e o banco continua com 2 funcionários. |
| **CT-E2E-FUN-003**<br>*Sem RF específico (base do RF10)* | Cadastro sem cargo é bloqueado. | 1. Login: `admin` / `1234`<br>2. Menu principal → `2` (Funcionários); submenu → `1` (Cadastrar)<br>3. Informar: nome `Marcos Lima`, cargo vazio, telefone `61988880000` | Mensagem `O cargo do funcionário é obrigatório.` | Passa se a mensagem aparece e o banco continua com 2 funcionários. |
| **CT-E2E-FUN-004**<br>*Sem RF específico (base do RF10)* | Cadastro sem telefone é bloqueado. | 1. Login: `admin` / `1234`<br>2. Menu principal → `2` (Funcionários); submenu → `1` (Cadastrar)<br>3. Informar: nome `Marcos Lima`, cargo `Lavador`, telefone vazio | Mensagem `O telefone do funcionário é obrigatório.` | Passa se a mensagem aparece e o banco continua com 2 funcionários. |
| **CT-E2E-FUN-005**<br>*Sem RF específico (base do RF10)* | Atualizar os dados de um funcionário existente. | 1. Login: `admin` / `1234`<br>2. Menu principal → `2` (Funcionários); submenu → `3` (Atualizar)<br>3. ID `2`; novos dados: nome `Jose Silva`, cargo `Supervisor`, telefone `61977770000` | `Funcionário atualizado!` | Passa se a mensagem aparece e o cargo do funcionário 2 no banco é `Supervisor`. |
| **CT-E2E-FUN-006**<br>*Sem RF específico (base do RF10)* | Excluir um funcionário que não possui atendimento. | 1. Preparação: funcionário `Sem Atendimento` inserido direto no banco<br>2. Login: `admin` / `1234`<br>3. Menu principal → `2` (Funcionários); submenu → `4` (Excluir)<br>4. Informar o ID desse funcionário | `Funcionário removido!`; o funcionário deixa de existir. | Passa se a mensagem aparece e o funcionário não existe mais no banco. |
| **CT-E2E-FUN-007**<br>*Regra 4.5 (atendimento tem um funcionário responsável)*<br>→ DEF-E2E-001 | Excluir um funcionário que já tem atendimento: ele deve ser preservado e o usuário deve receber mensagem compreensível. | 1. Login: `admin` / `1234`<br>2. Menu principal → `2` (Funcionários); submenu → `4` (Excluir)<br>3. Informar o ID `1` (funcionário com atendimentos) | O funcionário continua no banco e o sistema explica, em linguagem simples, que ele possui atendimentos (sem texto técnico do MySQL). | Passa se o funcionário 1 continua no banco e a saída não contém mensagem técnica (`Cannot delete or update`, `foreign key`, `constraint`). |

#### 6.8.4 Serviços

| ID | Descrição | Passos e dados de entrada | Saída esperada | Critério de sucesso |
|---|---|---|---|---|
| **CT-E2E-SER-001**<br>*RF06; RF07; HU02 (cenário 2); HU03 (cenário 2)* | Cadastrar um serviço válido e consultá-lo na listagem. | 1. Login: `admin` / `1234`<br>2. Menu principal → `3` (Serviços); submenu → `1` (Cadastrar)<br>3. Informar: nome `Polimento`, descrição `Polimento completo`, valor `99.90`<br>4. Submenu → `2` (Listar) | `Serviço cadastrado!`; `Polimento` aparece na listagem; o serviço é gravado com o valor informado. | Passa se a mensagem e a listagem aparecem e o banco guarda `Polimento` com valor 99.90. |
| **CT-E2E-SER-002**<br>*RF06; HU02* | Cadastro sem nome é bloqueado. | 1. Login: `admin` / `1234`<br>2. Menu principal → `3` (Serviços); submenu → `1` (Cadastrar)<br>3. Informar: nome vazio, descrição `Descricao`, valor `50.00` | Mensagem `O nome do serviço é obrigatório.` | Passa se a mensagem aparece e o banco continua com 2 serviços. |
| **CT-E2E-SER-003**<br>*RF06; HU02* | Cadastro com valor zero é bloqueado. | 1. Login: `admin` / `1234`<br>2. Menu principal → `3` (Serviços); submenu → `1` (Cadastrar)<br>3. Informar: nome `Teste`, descrição `Descricao`, valor `0` | Mensagem `O valor do serviço deve ser maior que zero.` | Passa se a mensagem aparece e nenhum serviço `Teste` é gravado. |
| **CT-E2E-SER-004**<br>*RF06; HU02 (robustez da entrada)*<br>→ DEF-E2E-003 | Texto digitado no lugar de um número (valor do serviço). | 1. Login: `admin` / `1234`<br>2. Menu principal → `3` (Serviços); submenu → `1` (Cadastrar)<br>3. Informar: nome `Teste`, descrição `Descricao`, valor `abc` | O sistema avisa que o valor é inválido e continua funcionando, sem encerrar por exceção. | Passa se não há `Exception` na saída e o programa encerra normalmente (código de saída 0). |
| **CT-E2E-SER-005**<br>*RF08; HU04 (cenário 2)* | Atualizar os dados de um serviço existente. | 1. Login: `admin` / `1234`<br>2. Menu principal → `3` (Serviços); submenu → `3` (Atualizar)<br>3. ID `2`; novos dados: nome `Lavagem Parcial Plus`, descrição `Lavagem externa e cera`, valor `40.00` | `Serviço atualizado!` | Passa se a mensagem aparece e o banco guarda o valor 40.00 para o serviço 2. |
| **CT-E2E-SER-006**<br>*RF09; HU05 (cenário 2)* | Excluir um serviço que não está vinculado a nenhum atendimento. | 1. Preparação: serviço `Sem Vinculo` (R$ 10,00) inserido direto no banco<br>2. Login: `admin` / `1234`<br>3. Menu principal → `3` (Serviços); submenu → `4` (Excluir)<br>4. Informar o ID desse serviço | `Serviço removido!`; o serviço deixa de existir. | Passa se a mensagem aparece e o serviço não existe mais no banco. |
| **CT-E2E-SER-007**<br>*RF09; HU05; regra 4.5 (serviço em atendimentos)*<br>→ DEF-E2E-001 | Excluir um serviço vinculado a atendimento: ele deve ser preservado e o usuário deve receber mensagem compreensível. | 1. Login: `admin` / `1234`<br>2. Menu principal → `3` (Serviços); submenu → `4` (Excluir)<br>3. Informar o ID `1` (serviço vinculado a atendimento) | O serviço continua no banco e o sistema explica, em linguagem simples, que ele está em uso (sem texto técnico do MySQL). | Passa se o serviço 1 continua no banco e a saída não contém mensagem técnica (`Cannot delete or update`, `foreign key`, `constraint`). |
| **CT-E2E-SER-008**<br>*RF09; HU05*<br>→ DEF-E2E-002 | Excluir um ID que não existe. | 1. Login: `admin` / `1234`<br>2. Menu principal → `3` (Serviços); submenu → `4` (Excluir)<br>3. Informar o ID `9999` | Mensagem informando que nenhum registro foi encontrado; não pode aparecer `Serviço removido!`. | Passa se `Serviço removido!` não aparece e a saída informa que o registro não foi encontrado. |
| **CT-E2E-SER-009**<br>*RF06; HU02 (cenário 2)*<br>→ DEF-E2E-007 | Digitar o valor no formato do exemplo da própria tela (`49.90`) em computador com idioma português (Brasil). *(novo)* | 1. Iniciar o sistema com idioma pt-BR (`-Duser.language=pt -Duser.country=BR`)<br>2. Login: `admin` / `1234`<br>3. Menu principal → `3` (Serviços); submenu → `1` (Cadastrar)<br>4. Informar: nome `Encerado`, descrição `Cera`, valor `49.90` | O sistema aceita o valor no formato mostrado como exemplo (ou a tela orienta o formato correto) e não encerra por exceção. | Passa se não há exceção, o código de saída é 0 e o serviço `Encerado` é gravado (1 registro). |

#### 6.8.5 Atendimentos

| ID | Descrição | Passos e dados de entrada | Saída esperada | Critério de sucesso |
|---|---|---|---|---|
| **CT-E2E-ATE-001**<br>*RF11; HU03 (cenário 3)* | Listar os atendimentos existentes. | 1. Login: `admin` / `1234`<br>2. Menu principal → `4` (Atendimentos); submenu → `2` (Listar) | Os 3 atendimentos dos dados-base são exibidos (`Primeiro`, `Segundo` e `Terceiro atendimento teste`). | Passa se as três observações aparecem e o programa encerra normalmente (código de saída 0). |
| **CT-E2E-ATE-002**<br>*RF10; HU02 (cenário 3); regra 4.5*<br>→ DEF-E2E-005 | Cadastrar um atendimento com um serviço. | 1. Login: `admin` / `1234`<br>2. Menu principal → `4` (Atendimentos); submenu → `1` (Cadastrar)<br>3. Informar: ID do cliente `2`, ID do funcionário `1`, observação `Lavagem rapida`<br>4. Serviço: ID `1`, quantidade `1`; "Adicionar outro serviço?" → `N` | O atendimento e o serviço vinculado são gravados; o sistema não encerra por exceção. | Passa se não há exceção, o código de saída é 0, há 1 atendimento com a observação `Lavagem rapida` e 1 vínculo de serviço para ele. |
| **CT-E2E-ATE-003**<br>*RF12; HU04 (cenário 3)* | Atualizar um atendimento existente. | 1. Login: `admin` / `1234`<br>2. Menu principal → `4` (Atendimentos); submenu → `3` (Atualizar)<br>3. ID do atendimento `2`; novo cliente `1`; novo funcionário `2`; nova observação `Obs atualizada` | `Atendimento atualizado!` | Passa se a mensagem aparece e o banco guarda cliente 1, funcionário 2 e a observação `Obs atualizada` no atendimento 2. |
| **CT-E2E-ATE-004**<br>*RF12; HU04; regra 4.5 (todo atendimento tem cliente e funcionário)*<br>→ DEF-E2E-001 | Atualizar um atendimento com cliente que não existe. | 1. Login: `admin` / `1234`<br>2. Menu principal → `4` (Atendimentos); submenu → `3` (Atualizar)<br>3. ID `2`; cliente `999` (inexistente); funcionário `1`; observação `Obs invalida` | O atendimento não é alterado e o sistema informa, em linguagem simples, que o cliente não existe. | Passa se o atendimento 2 mantém a observação `Segundo atendimento teste` e a saída não contém mensagem técnica do banco. |
| **CT-E2E-ATE-005**<br>*RF13; HU05 (cenário 3)* | Excluir um atendimento que não possui serviços vinculados. | 1. Preparação: atendimento (cliente 1, funcionário 1, observação `Sem servicos`) inserido direto no banco, sem serviços<br>2. Login: `admin` / `1234`<br>3. Menu principal → `4` (Atendimentos); submenu → `4` (Excluir)<br>4. Informar o ID desse atendimento | `Atendimento removido!`; o atendimento deixa de existir. | Passa se a mensagem aparece e o atendimento não existe mais no banco. |
| **CT-E2E-ATE-006**<br>*RF13; HU05; regra 4.5 (atendimento com serviços)*<br>→ DEF-E2E-001 | Excluir um atendimento que tem serviço associado: ele deve ser preservado e o usuário deve receber mensagem compreensível. | 1. Login: `admin` / `1234`<br>2. Menu principal → `4` (Atendimentos); submenu → `4` (Excluir)<br>3. Informar o ID `1` (atendimento com serviço vinculado) | O atendimento continua no banco e o sistema explica, em linguagem simples, que ele possui serviços vinculados (sem texto técnico do MySQL). | Passa se o atendimento 1 continua no banco e a saída não contém mensagem técnica (`Cannot delete or update`, `foreign key`, `constraint`). |
| **CT-E2E-ATE-007**<br>*RF13; HU05 (robustez da entrada)*<br>→ DEF-E2E-003 | Texto digitado no lugar do ID do atendimento. | 1. Login: `admin` / `1234`<br>2. Menu principal → `4` (Atendimentos); submenu → `4` (Excluir)<br>3. Digitar `abc` no lugar do ID | O sistema avisa que o ID é inválido e continua funcionando, sem encerrar por exceção. | Passa se não há `Exception` na saída e o programa encerra normalmente (código de saída 0). |
| **CT-E2E-ATE-008**<br>*RF10; regra 4.5 (um atendimento pode ter vários serviços); integridade*<br>→ DEF-E2E-004 | O cadastro de atendimento não pode deixar registro parcial no banco. *(novo)* | 1. Login: `admin` / `1234`<br>2. Menu principal → `4` (Atendimentos); submenu → `1` (Cadastrar)<br>3. Informar: ID do cliente `2`, ID do funcionário `1`, observação `Lavagem rapida`<br>4. Serviço: ID `1`, quantidade `1`; "Adicionar outro serviço?" → `N` | Ou o atendimento é gravado junto com seus serviços, ou nada é gravado. | Passa se o número de atendimentos com a observação `Lavagem rapida` for igual ao número de vínculos de serviço desses atendimentos. |

---

## 7. Evidências das execuções

### 7.0 Visão geral da execução automatizada

- **Relatório exportado do IntelliJ:** [relatorio-junit.html](relatorio-junit.html) (36 testes, 12 reprovados e 24 aprovados, em cerca de 28 s). Abra o arquivo no navegador.
- **Resultado completo em imagens** (capturas do relatório, do primeiro ao último teste, em 4 partes): [parte 1](JUNIT-arvore-completa-1.png), [parte 2](JUNIT-arvore-completa-2.png), [parte 3](JUNIT-arvore-completa-3.png) e [parte 4](JUNIT-arvore-completa-4.png).
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

Resumo: **36 testes — 24 aprovados e 12 reprovados.** Os passos, os dados de entrada, a saída esperada e o critério de sucesso de cada caso estão na seção 6.8. Reprovado significa que o sistema não se comportou como esperado (ver critérios na seção 10); cada reprovação está ligada a um defeito da seção 9.

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

## 12. Lições aprendidas

1. **JUnit também serve para E2E de uma aplicação de linha de comando.** Executar o sistema em um processo separado e alimentar o teclado pela entrada padrão deu um teste de ponta a ponta de verdade (CLI, Services, DAOs e MySQL), sem precisar de uma ferramenta de interface web.
2. **Dados conhecidos tornam o teste confiável.** Restaurar o banco antes de cada teste deixou os resultados repetíveis e independentes entre si. O custo é que o banco não pode ser usado por outros testes ao mesmo tempo.
3. **É preciso conferir o banco, e não só a tela.** O sistema informou "removido" para um ID que não existe (DEF-E2E-002) e gravou um atendimento sem serviços (DEF-E2E-004). Esses problemas só aparecem quando se consulta o que foi realmente gravado.
4. **O ambiente de execução influencia o resultado.** O separador decimal do idioma do computador (DEF-E2E-007) só apareceu ao rodar o sistema na máquina real. Por isso os testes fixam o idioma e há um caso próprio que reproduz o defeito.
5. **Teste reprovado é resultado, não erro do teste.** 12 dos 36 casos reprovaram por defeitos reais. Manter esses testes vermelhos documenta o problema e os transforma em teste de regressão: quando o defeito for corrigido, o teste passa a aprovar.
6. **Um caso de teste precisa poder ser repetido por outra pessoa.** A primeira versão trazia só título e resultado. Com passos, dados de entrada, saída esperada e critério de sucesso, qualquer integrante consegue reproduzir e conferir o resultado.
7. **Definir o resultado esperado antes de executar evita aceitar o comportamento errado.** Os resultados esperados vieram da visão e das regras de negócio, e não do que o sistema fazia, e foi isso que permitiu enxergar os defeitos.

---

## 13. Repositório e organização das evidências

| Conteúdo | Link |
|---|---|
| Repositório oficial do grupo (código do sistema) | https://github.com/PedroRodriguesTeixeira/Projeto-Lava-Jato |
| Caminho dos testes no projeto Maven do grupo | `src/test/java/e2e/` |
| Cópia pessoal dos testes e evidências | https://github.com/codebygabes/Studying-and-Coding/tree/main/DSS%20-%20TESTE%20E2E%20-%20Projeto%20LavaJato |

```
evidencias/
├── relatorio-junit.html
├── JUNIT-arvore-completa-1.png ... JUNIT-arvore-completa-4.png
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
