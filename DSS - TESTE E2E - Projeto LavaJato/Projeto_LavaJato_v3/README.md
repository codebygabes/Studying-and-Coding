# Sistema Lava-Jato — Como Rodar

Sistema em Java (CLI) com banco MySQL, login e uma API HTTP simples.
Estrutura pensada para rodar no VS Code, com o driver do MySQL já
incluído na pasta `lib/` (sem precisar baixar nada externo).

## 1. Pré-requisitos

- **JDK** instalado (Java 11 ou superior).
- **MySQL Server** instalado e rodando na sua máquina (`localhost:3306`).
- **VS Code** com as extensões:
  - **Extension Pack for Java** (Microsoft) — para rodar o projeto Java.
  - Uma extensão de banco de dados, tipo **SQLTools** (com o driver MySQL/MariaDB)
    ou a extensão **MySQL** — só pra você conseguir abrir e rodar o
    `crud_lavajato.sql` direto pelo VS Code, se preferir. Também dá
    pra rodar esse script direto pelo MySQL Workbench ou pelo terminal
    (`mysql -u root -p < crud_lavajato.sql`), sem precisar de extensão nenhuma.

O driver JDBC do MySQL (`mysql-connector-j-9.7.0.jar`) já está na pasta
`lib/` e já é referenciado pelo `.vscode/settings.json` — não precisa
adicionar nada, é só abrir a pasta do projeto no VS Code.

## 2. Criar o banco de dados

Rode o script `crud_lavajato.sql` no seu MySQL (workbench, terminal ou
extensão do VS Code). Ele cria o banco `lavajato`, todas as tabelas
(`usuario`, `cliente`, `funcionario`, `servico`, `atendimento`,
`atendimento_servico`) e já insere alguns dados de teste, incluindo um
usuário padrão para login:

- **Login:** `admin`
- **Senha:** `1234`

## 3. Configurar a conexão com o banco

Abra o arquivo `src/util/Conexao.java` e ajuste, se precisar, o
usuário e a senha do **seu** MySQL:

```java
private static final String URL =
    "jdbc:mysql://localhost:3306/lavajato";

private static final String USER = "root";
private static final String PASSWORD = "sua_senha_aqui";
```

Se o seu MySQL estiver em outra porta, outro host, ou com outro nome
de usuário, ajuste `URL` e `USER` também.

## 4. Rodar o sistema (CLI)

No VS Code, abra `src/applications/Main.java` e rode (botão "Run" ou
`F5`). O programa vai:

1. Testar a conexão com o banco.
2. Pedir login e senha (use `admin` / `1234`, ou o usuário que você
   cadastrar).
3. Abrir o menu principal, com Clientes, Funcionários, Serviços e
   Atendimentos (cada um com cadastrar, listar, atualizar e excluir).

## 5. Rodar a API

A API é uma classe separada, feita só com recursos nativos do Java
(`com.sun.net.httpserver`), sem framework nenhum. Para rodar, abra
`src/api/ApiServer.java` no VS Code e execute (Run/F5). Ela sobe em:

```
http://localhost:8080
```

### Endpoints disponíveis

| Método | Rota            | Corpo (JSON)                                                                 |
|--------|-----------------|-------------------------------------------------------------------------------|
| POST   | `/login`        | `{"login":"admin","senha":"1234"}`                                            |
| GET    | `/clientes`     | —                                                                              |
| POST   | `/clientes`     | `{"nome":"...","telefone":"...","placa":"...","modeloVeiculo":"...","marcaVeiculo":"..."}` |
| GET    | `/funcionarios` | —                                                                              |
| POST   | `/funcionarios` | `{"nome":"...","cargo":"...","telefone":"..."}`                              |
| GET    | `/servicos`     | —                                                                              |
| POST   | `/servicos`     | `{"nomeServico":"...","descricao":"...","valor":49.90}`                      |

### Exemplos com curl

```bash
# Login
curl -X POST http://localhost:8080/login -d "{\"login\":\"admin\",\"senha\":\"1234\"}"

# Listar clientes
curl http://localhost:8080/clientes

# Cadastrar cliente
curl -X POST http://localhost:8080/clientes -d "{\"nome\":\"Ana\",\"telefone\":\"61999998888\",\"placa\":\"XYZ1234\",\"modeloVeiculo\":\"Onix\",\"marcaVeiculo\":\"Chevrolet\"}"
```

Pode testar também pelo Postman/Insomnia, apontando pra
`http://localhost:8080` com os mesmos métodos e corpos acima.

## Observações

- A senha do usuário fica salva em texto puro no banco (sem hash) —
  suficiente para os fins deste trabalho.
- O CLI (`Main.java`) e a API (`ApiServer.java`) são dois pontos de
  entrada independentes — rode um ou outro (ou os dois ao mesmo
  tempo, em execuções separadas), conforme o que você for testar.
