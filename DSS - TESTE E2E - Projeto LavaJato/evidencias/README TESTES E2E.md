# Testes End-to-End — Sistema de Gerenciamento de Lava-Jato

> Documento de registro dos testes E2E executados manualmente pela interface CLI.

---

## 1. Identificação

| Campo | Informação |
|---|---|
| Projeto | Sistema de Gerenciamento de Lava-Jato |
| Tipo de teste | End-to-End (E2E) |
| Interface | CLI — terminal/console do IntelliJ IDEA |
| Banco de dados | MySQL |
| Execução | Manual |
| Versão avaliada | Versão final disponível durante a execução |
| Data das execuções | 15/09/2026 |

---

## 2. Objetivo

Validar o funcionamento do sistema a partir da perspectiva do usuário, percorrendo os fluxos completos da aplicação. Os testes verificam a integração entre a CLI, os serviços, os DAOs, a conexão JDBC e o banco de dados MySQL.

O foco não foi analisar métodos isolados, mas verificar se o usuário consegue iniciar o sistema, autenticar-se, acessar os menus e realizar operações de cadastro, consulta, atualização e exclusão.

---

## 3. Escopo

Foram avaliados:

- inicialização e conexão com o banco;
- login válido e encerramento do sistema;
- cadastro, consulta, atualização e exclusão de clientes;
- cadastro, consulta, atualização e exclusão de funcionários;
- cadastro, consulta, atualização e exclusão de serviços;
- consulta, cadastro e atualização de atendimentos;
- validação de campos obrigatórios;
- validação de placa de veículo;
- validação de valor de serviço;
- integridade referencial entre as entidades;
- comportamento diante de IDs inexistentes;
- comportamento diante de entradas inválidas no terminal.

## 4. Fora do escopo

Não foram executados nesta etapa:

- testes unitários;
- testes da API HTTP;
- testes com Postman, Insomnia ou curl;
- testes automatizados com Cypress, Selenium ou Playwright;
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
| Banco | MySQL Server |
| Host | localhost |
| Porta | 3306 |
| Banco | lavajato |
| Driver | MySQL Connector/J |
| Classe de entrada | `applications.Main` |
| Interface | CLI |

A aplicação realiza a conexão com o banco antes de apresentar a tela de login. O banco contém tabelas de usuários, clientes, funcionários, serviços, atendimentos e associação entre atendimentos e serviços.

---

## 6. Procedimento de execução

1. Iniciar o MySQL Server.
2. Abrir o projeto no IntelliJ IDEA.
3. Executar `applications.Main`.
4. Confirmar a mensagem de conexão bem-sucedida.
5. Realizar login com um usuário válido.
6. Acessar os menus do sistema.
7. Executar os fluxos planejados manualmente.
8. Comparar o resultado obtido com o resultado esperado.
9. Registrar o status do caso.
10. Salvar prints do console como evidência.

---

## 7. Evidências das execuções

As imagens estão salvas em subpastas dentro de `evidencias`, no mesmo diretório deste README (uma subpasta por seção: 7.1, 7.2, 7.3, 7.4 e 7.5).

> **Atenção:** prints tirada do meu computador pessoal.

### 7.1 Sistema e autenticação

#### Figura 1 — Conexão com o banco e tela de login

Demonstra que o sistema conseguiu conectar ao MySQL e apresentou a tela de autenticação.

![Figura 1 — Conexão com o banco e tela de login](evidencias/7.1 Sistema e autenticação/E2E-001-conexao-login.png)

#### Figura 2 — Login válido e menu principal

Demonstra que o usuário conseguiu autenticar-se e acessar as funcionalidades do sistema.

![Figura 2 — Login válido e menu principal](evidencias/7.1 Sistema e autenticação/E2E-002-menu-principal.png)

---

### 7.2 Clientes

#### Figura 3 — Cadastro e consulta de cliente

Demonstra o cadastro de um cliente válido e sua posterior exibição na listagem.

![Figura 3 — Cadastro e consulta de cliente](evidencias/7.2 Clientes/E2E-CLI-001-cadastro.png)

#### Figura 4 — Validação de placa inválida

Demonstra que o sistema rejeitou uma placa fora dos formatos aceitos.

![Figura 4 — Validação de placa inválida](evidencias/7.2 Clientes/E2E-CLI-002-placa-invalida.png)

#### Figura 5 — Atualização de cliente

Demonstra que os dados de um cliente foram alterados e apareceram atualizados na consulta.

![Figura 5 — Atualização de cliente](evidencias/7.2 Clientes/E2E-CLI-004-atualizacao.png)

#### Figura 6 — Restrição ao excluir cliente relacionado

Demonstra que o banco impediu a exclusão de um cliente associado a atendimentos. A mensagem exibida, entretanto, é técnica e poderia ser tratada pela aplicação.

![Figura 6 — Restrição ao excluir cliente relacionado](evidencias/7.2 Clientes/E2E-CLI-006-restricao-exclusao.png)

#### Figura 7 — Exclusão de ID inexistente

Demonstra que o sistema informou sucesso mesmo quando o ID informado não existia. Esse comportamento foi registrado como defeito.

![Figura 7 — Exclusão de ID inexistente](evidencias/7.2 Clientes/E2E-CLI-007-exclusao-id-inexistente.png)

---

### 7.3 Funcionários

#### Figura 8 — Atualização de funcionário

Demonstra a atualização de dados de um funcionário e a confirmação apresentada pelo sistema.

![Figura 8 — Atualização de funcionário](evidencias/7.3 Funcionários/E2E-FUN-005-atualizacao.png)

#### Figura 9 — Validação de campos obrigatórios

Demonstra as mensagens apresentadas quando nome, cargo ou telefone não são preenchidos.

![Figura 9a — Validação de nome obrigatório](evidencias/7.3 Funcionários/E2E-FUN-002-nome-vazio.png)

![Figura 9b — Validação de cargo obrigatório](evidencias/7.3 Funcionários/E2E-FUN-003-cargo-vazio.png)

![Figura 9c — Validação de telefone obrigatório](evidencias/7.3 Funcionários/E2E-FUN-004-telefone-vazio.png)

#### Figura 10 — Restrição ao excluir funcionário relacionado

Demonstra o bloqueio da exclusão de funcionário associado a atendimento.

![Figura 10 — Restrição ao excluir funcionário relacionado](evidencias/7.3 Funcionários/E2E-FUN-007-restricao-exclusao.png)

---

### 7.4 Serviços

#### Figura 11 — Cadastro e consulta de serviço

Demonstra o cadastro de um serviço válido e sua exibição na listagem.

![Figura 11 — Cadastro e consulta de serviço](evidencias/7.4 Serviços/E2E-SER-001-cadastro.png)

#### Figura 12 — Valor inválido ou entrada não numérica

Demonstra a validação de valor igual a zero ou o encerramento do programa por `InputMismatchException` quando foi informado um valor incompatível.

![Figura 12 — Valor inválido ou entrada não numérica](evidencias/7.4 Serviços/E2E-SER-004-valor-invalido.png)

#### Figura 13 — Restrição ao excluir serviço relacionado

Demonstra o bloqueio da exclusão de serviço associado a atendimento.

![Figura 13 — Restrição ao excluir serviço relacionado](evidencias/7.4 Serviços/E2E-SER-007-restricao-exclusao.png)

---

### 7.5 Atendimentos

#### Figura 14 — Listagem de atendimentos

Demonstra os atendimentos existentes, com seus IDs, datas e observações.

![Figura 14 — Listagem de atendimentos](evidencias/7.5 Atendimentos/E2E-ATE-001-listagem.png)

#### Figura 15 — Erro no cadastro de atendimento

Demonstra a ocorrência de `NullPointerException` durante a associação dos serviços ao atendimento e o encerramento da aplicação com `exit code 1`.

![Figura 15 — Erro no cadastro de atendimento](evidencias/7.5 Atendimentos/E2E-ATE-002-atendimento-excecao.png)

#### Figura 16 — Atualização de atendimento

Demonstra que cliente, funcionário e observação de um atendimento foram atualizados.

![Figura 16 — Atualização de atendimento](evidencias/7.5 Atendimentos/E2E-ATE-003-atualizacao.png)

#### Figura 17 — Erro de relacionamento no atendimento

Demonstra o bloqueio provocado por uma chave estrangeira quando foi informado um relacionamento inválido.

![Figura 17 — Erro de relacionamento no atendimento](evidencias/7.5 Atendimentos/E2E-ATE-004-erro-relacionamento.png)

---

## 8. Resultados dos testes

### 8.1 Sistema e autenticação

| ID | Caso | Resultado | Status |
|---|---|---|---|
| CT-E2E-001 | Inicializar com banco disponível | Banco conectado e login exibido | Aprovado |
| CT-E2E-002 | Login válido | Menu principal exibido | Aprovado |
| CT-E2E-003 | Encerrar o sistema | Saída normal com `exit code 0` | Aprovado |

### 8.2 Clientes

| ID | Caso | Resultado | Status |
|---|---|---|---|
| CT-E2E-CLI-001 | Cadastrar cliente válido | Cliente cadastrado e listado | Aprovado |
| CT-E2E-CLI-002 | Cadastrar placa inválida | Cadastro bloqueado | Aprovado |
| CT-E2E-CLI-003 | Atualizar com campos vazios | Atualização bloqueada | Aprovado |
| CT-E2E-CLI-004 | Atualizar cliente válido | Dados atualizados | Aprovado |
| CT-E2E-CLI-005 | Excluir cliente sem atendimento | Cliente removido | Aprovado |
| CT-E2E-CLI-006 | Excluir cliente com atendimento | Banco bloqueou; mensagem técnica exibida | Reprovado quanto ao tratamento |
| CT-E2E-CLI-007 | Excluir ID inexistente | Mensagem incorreta de sucesso | Reprovado |

### 8.3 Funcionários

| ID | Caso | Resultado | Status |
|---|---|---|---|
| CT-E2E-FUN-001 | Cadastrar funcionário válido | Registro criado e consultado | Aprovado |
| CT-E2E-FUN-002 | Cadastro sem nome | Cadastro bloqueado | Aprovado |
| CT-E2E-FUN-003 | Cadastro sem cargo | Cadastro bloqueado | Aprovado |
| CT-E2E-FUN-004 | Cadastro sem telefone | Cadastro bloqueado | Aprovado |
| CT-E2E-FUN-005 | Atualizar funcionário válido | Dados alterados | Aprovado |
| CT-E2E-FUN-006 | Excluir sem atendimento relacionado | Funcionário removido | Aprovado |
| CT-E2E-FUN-007 | Excluir com atendimento relacionado | Banco bloqueou; mensagem técnica exibida | Reprovado quanto ao tratamento |

### 8.4 Serviços

| ID | Caso | Resultado | Status |
|---|---|---|---|
| CT-E2E-SER-001 | Cadastrar serviço válido | Serviço cadastrado e listado | Aprovado |
| CT-E2E-SER-002 | Cadastro sem nome | Cadastro bloqueado | Aprovado |
| CT-E2E-SER-003 | Cadastro com valor zero | Cadastro bloqueado | Aprovado |
| CT-E2E-SER-004 | Entrada não numérica | `InputMismatchException` encerrou o programa | Reprovado |
| CT-E2E-SER-005 | Atualizar serviço válido | Dados atualizados | Aprovado |
| CT-E2E-SER-006 | Excluir sem vínculo | Serviço removido | Aprovado |
| CT-E2E-SER-007 | Excluir com vínculo | Banco bloqueou; mensagem técnica exibida | Reprovado quanto ao tratamento |
| CT-E2E-SER-008 | Excluir ID inexistente | Mensagem incorreta de sucesso | Reprovado |

### 8.5 Atendimentos

| ID | Caso | Resultado | Status |
|---|---|---|---|
| CT-E2E-ATE-001 | Listar atendimentos | Registros exibidos | Aprovado |
| CT-E2E-ATE-002 | Cadastrar atendimento | Persistência parcial e `NullPointerException` | Reprovado |
| CT-E2E-ATE-003 | Atualizar atendimento válido | Dados alterados | Aprovado |
| CT-E2E-ATE-004 | Atualizar relacionamento inválido | Banco bloqueou a operação | Reprovado quanto ao tratamento |
| CT-E2E-ATE-005 | Excluir sem dependência | Atendimento removido | Aprovado |
| CT-E2E-ATE-006 | Excluir com serviço associado | Banco bloqueou a operação | Reprovado quanto ao tratamento |
| CT-E2E-ATE-007 | Entrada vazia ou inválida | Fluxo não tratado adequadamente | Reprovado |

---

## 9. Defeitos encontrados

### DEF-E2E-001 — Mensagens técnicas do banco

Ao excluir ou atualizar registros relacionados, o usuário recebe mensagens do MySQL, como `Cannot delete or update a parent row` ou `Cannot add or update a child row`.

- Impacto: dificulta o entendimento do usuário.
- Severidade: média.
- Prioridade: média.
- Comportamento esperado: apresentar mensagem amigável explicando o relacionamento existente.

![DEF-E2E-001 — Mensagem técnica do banco ao excluir cliente relacionado](evidencias/7.2 Clientes/E2E-CLI-006-restricao-exclusao.png)

### DEF-E2E-002 — Sucesso informado para ID inexistente

Ao tentar excluir um ID que não existia, o sistema exibiu mensagem de remoção bem-sucedida.

- Impacto: o usuário recebe informação incorreta.
- Severidade: média.
- Prioridade: média.
- Comportamento esperado: informar que nenhum registro foi encontrado.

![DEF-E2E-002 — Sucesso informado para ID de cliente inexistente](evidencias/7.2 Clientes/E2E-CLI-007-exclusao-id-inexistente.png)

### DEF-E2E-003 — Entrada numérica inválida encerra o programa

Ao informar texto em campo numérico, ocorreu `InputMismatchException` e o processo terminou com `exit code 1`.

- Impacto: interrompe o uso do sistema.
- Severidade: alta.
- Prioridade: alta.
- Comportamento esperado: solicitar nova entrada sem encerrar a aplicação.

![DEF-E2E-003 — InputMismatchException ao cadastrar serviço](evidencias/7.4 Serviços/E2E-SER-004-valor-invalido.png)

### DEF-E2E-004 — Cadastro parcial de atendimento

O atendimento principal foi salvo antes da falha na associação dos serviços.

- Impacto: pode deixar dados incompletos no banco.
- Severidade: alta.
- Prioridade: alta.
- Comportamento esperado: desfazer a operação quando uma etapa falhar.

![DEF-E2E-004 — Persistência parcial do atendimento](evidencias/7.5 Atendimentos/E2E-ATE-002-atendimento-excecao.png)

### DEF-E2E-005 — NullPointerException no atendimento

O cadastro dos serviços associados gerou `NullPointerException` porque o atendimento associado ao objeto `AtendimentoServico` estava nulo.

- Impacto: impede a conclusão do fluxo.
- Severidade: alta.
- Prioridade: alta.
- Comportamento esperado: preencher e validar o relacionamento antes da inserção.

![DEF-E2E-005 — NullPointerException na associação de serviços](evidencias/7.5 Atendimentos/E2E-ATE-002-atendimento-excecao.png)

### DEF-E2E-006 — Listagem usa IDs em vez de nomes

A listagem de atendimentos apresenta apenas `ID Cliente` e `ID Func.`.

- Impacto: exige consultas adicionais para identificar as pessoas.
- Severidade: baixa.
- Prioridade: baixa.
- Comportamento esperado: exibir também os nomes do cliente e do funcionário.

![DEF-E2E-006 — Listagem de atendimentos exibindo apenas IDs](evidencias/7.5 Atendimentos/E2E-ATE-001-listagem.png)

---

## 10. Critérios de avaliação

Um caso foi considerado aprovado quando o resultado observado correspondeu ao esperado, incluindo a rejeição correta de entradas inválidas.

Um caso foi considerado reprovado quando ocorreu exceção não tratada, o sistema informou sucesso sem realizar a operação, houve persistência parcial, a mensagem foi insuficiente ou o fluxo não pôde ser concluído.

---

## 11. Conclusão

Os testes E2E permitiram avaliar o sistema de ponta a ponta pela CLI, verificando a integração entre a interação do usuário, as regras de negócio, a persistência no MySQL e os relacionamentos entre as entidades.

Os fluxos principais de autenticação, clientes, funcionários e serviços apresentaram funcionamento satisfatório em diversas situações positivas e negativas. Também foram identificadas falhas relevantes no tratamento de entradas inválidas, na comunicação de erros do banco e no cadastro de atendimentos.

Como a atividade solicitou o registro das falhas, os problemas encontrados foram documentados sem alteração do código durante esta etapa. A identificação desses problemas fornece evidências para futuras correções e novos testes de regressão.

---

## 12. Organização das evidências

Os arquivos estão organizados em subpastas dentro de `evidencias/`, no mesmo diretório deste README:

**`evidencias/7.1 Sistema e autenticação/`**
- `E2E-001-conexao-login.png`
- `E2E-002-menu-principal.png`

**`evidencias/7.2 Clientes/`**
- `E2E-CLI-001-cadastro.png`
- `E2E-CLI-002-placa-invalida.png`
- `E2E-CLI-004-atualizacao.png`
- `E2E-CLI-006-restricao-exclusao.png`
- `E2E-CLI-007-exclusao-id-inexistente.png`

**`evidencias/7.3 Funcionários/`**
- `E2E-FUN-005-atualizacao.png`
- `E2E-FUN-002-nome-vazio.png`
- `E2E-FUN-003-cargo-vazio.png`
- `E2E-FUN-004-telefone-vazio.png`
- `E2E-FUN-007-restricao-exclusao.png`

**`evidencias/7.4 Serviços/`**
- `E2E-SER-001-cadastro.png`
- `E2E-SER-004-valor-invalido.png`
- `E2E-SER-007-restricao-exclusao.png`

**`evidencias/7.5 Atendimentos/`**
- `E2E-ATE-001-listagem.png`
- `E2E-ATE-002-atendimento-excecao.png`
- `E2E-ATE-003-atualizacao.png`
- `E2E-ATE-004-erro-relacionamento.png`
