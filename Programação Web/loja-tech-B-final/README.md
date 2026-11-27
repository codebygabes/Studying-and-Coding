# Loja Tech / Nexvion

Backend Node.js + Express e MySQL para N2–AT2. Frontend original preservado, com ajustes de integração. Não há pagamento real.

## Execução local no Windows

1. Instale Node.js 22 ou superior e MySQL 8.0.16 ou superior. Deixe o serviço MySQL iniciado.
2. No MySQL Workbench, conecte com seu usuário local, abra `database/schema.sql` e execute o arquivo completo. Ele cria **nexvion_tech_refatorado**, as tabelas e os oito produtos iniciais. É um script de instalação em banco novo; execute uma vez. Não apaga nem migra o banco antigo `nexvion_tech`.
3. Abra o PowerShell na pasta `loja-tech` extraída e execute:

```powershell
cd backend
npm ci
Copy-Item .env.example .env
node -e "console.log(require('node:crypto').randomBytes(48).toString('hex'))"
```

4. Edite `.env`: preencha `DB_USER`, `DB_PASSWORD` e coloque a chave gerada em `JWT_SECRET`. Mantenha `DB_NAME=nexvion_tech_refatorado`; ajuste host e porta se seu MySQL usar outros valores.
5. Inicie:

```powershell
npm start
```

6. Abra [a loja local](http://localhost:3001). O Express serve tanto o frontend quanto `/api`; mantenha o terminal aberto. Não abra o HTML diretamente nem use Live Server. Para parar, pressione Ctrl+C.

Alternativa ao Workbench: entre no cliente com `mysql -u root -p` e execute `SOURCE C:/caminho/extraido/loja-tech/database/schema.sql;`. Se `mysql` não estiver no PATH, use o executável da pasta `bin` da sua instalação MySQL. A senha solicitada é a do seu MySQL, não a da loja.

## Demonstração

- Administrador: **admin@nexvion.com** / **admin123**. Credencial fictícia, somente para avaliação local.
- Cliente: use **Cadastrar**, informe nome, e-mail, senha com pelo menos 6 caracteres e confirmação. O cadastro público sempre cria cliente.
- Use somente uma das versões por vez. A e B compartilham o mesmo schema e as mesmas regras; não execute novamente o SQL ao trocar de versão.
- Publique a versão **B** no GitHub. Não inclua `.env`, `node_modules` nem arquivos da versão A.

## Organização para explicar

| Arquivo | Responsabilidade |
|---|---|
| `backend/server.js` | Express, JSON, arquivos públicos, rotas e tratamento de erros |
| `backend/db/connection.js` | Pool MySQL com `mysql2/promise` |
| `backend/util.js` | Validações, centavos, transação e registro de movimentação |
| `backend/middleware/auth.js` | JWT, sessão ativa no banco e perfil de administrador |
| `backend/routes/authRoutes.js` | Cadastro, login e logout |
| `backend/routes/produtoRoutes.js` | Consulta, cadastro, edição e desativação |
| `backend/routes/pedidoRoutes.js` | Compra transacional e consulta por proprietário |
| `backend/routes/estoqueRoutes.js` | Entrada, saída, ajuste, histórico e indicadores |
| `database/schema.sql` | Seis entidades exigidas e tabela auxiliar de sessões |

O código do produto é seu próprio `id`, retornado também como `codigo` na API. Isso elimina a geração insegura com `MAX(codigo) + 1`.

## Regras de negócio

- Catálogo e categorias são públicos. Estoque zero aparece como indisponível.
- Carrinho público fica no localStorage; autenticação e checkout passam pela API. Só limpa o carrinho após resposta de compra bem-sucedida.
- O contador soma unidades, não apenas produtos distintos. O catálogo mostra o saldo disponível para adicionar e a quantidade já no carrinho. Adicionar não reserva nem altera o MySQL: a baixa real acontece somente após confirmar a compra.
- O título e a descrição ficam abaixo da arte do banner, com espaçamento próprio. O catálogo é atualizado depois da compra e após um conflito de estoque; uma sessão inválida redireciona ao login preservando os itens.
- Senhas usam bcrypt com custo 10. A senha fica limitada a 72 bytes para evitar truncamento pelo bcrypt.
- JWT vale 8 horas. Sua assinatura e a sessão MySQL são conferidas em cada acesso protegido. Logout remove a sessão; tokens antigos dessa sessão deixam de funcionar.
- O perfil atual vem do banco. Cliente consulta somente seus pedidos; admin consulta todos e gerencia produtos e estoque.
- O backend recebe IDs e quantidades, consulta preços no MySQL e ignora preço, desconto, total ou dono enviados pelo navegador.
- Cálculos usam centavos inteiros. Desconto arredondado ao centavo: abaixo de R$ 300 sem desconto; a partir de R$ 300, 10%; a partir de R$ 500, 15%. Não acumula descontos.
- Pedido aceita até 100 produtos distintos. Produto repetido é erro: envie uma linha com a quantidade total. Quantidades devem ser inteiras e positivas.
- Produtos são bloqueados por `SELECT ... FOR UPDATE`, em ordem de ID, na mesma conexão da transação. O estoque é conferido depois do bloqueio.
- Pedido, itens, estoque e histórico só são confirmados juntos. Erro causa rollback e a conexão é liberada em `finally`.
- Cadastro com estoque inicial, entrada, saída e ajuste também são transacionais. Saída administrativa e ajuste exigem motivo. No ajuste, a quantidade enviada é o novo saldo; o histórico guarda a diferença absoluta e o saldo resultante.
- Saldo entre 1 e 3 indica estoque baixo; zero indica esgotado. Produto é desativado em vez de excluído, preservando referências históricas.
- Preços e nomes dos itens são copiados para o pedido; alterar o catálogo não altera compras antigas.
- Queries usam parâmetros `?`. O frontend acessa apenas HTTP/JSON, sem credenciais do banco.

## API

| Método | Caminho | Acesso |
|---|---|---|
| POST | `/api/auth/cadastro` | Público |
| POST | `/api/auth/login` | Público |
| POST | `/api/auth/logout` | Autenticado |
| GET | `/api/produtos` | Público |
| GET | `/api/produtos/categorias` | Público |
| GET | `/api/produtos/todos` | Admin |
| POST | `/api/produtos` | Admin |
| PUT | `/api/produtos/:id` | Admin |
| DELETE | `/api/produtos/:id` | Admin, desativa |
| POST | `/api/pedidos` | Autenticado |
| GET | `/api/pedidos` | Próprios; admin vê todos |
| GET | `/api/estoque/dashboard` | Admin |
| GET | `/api/estoque/historico` | Admin |
| POST | `/api/estoque/entrada` | Admin |
| POST | `/api/estoque/saida` | Admin |
| POST | `/api/estoque/ajuste` | Admin |

Login recebe `{ "email": "...", "senha": "..." }`. Cadastro também recebe `nome` e `confirmar_senha`. Rotas protegidas recebem `Authorization: Bearer <token>`.

Compra recebe `{ "itens": [{ "produto_id": 3, "quantidade": 2 }] }`. Produto recebe `nome`, `categoria_id`, `preco`, `imagem_url` e, no cadastro, `estoque`. Movimentação recebe `produto_id`, `quantidade` e `motivo`.

HTTP: 200 consulta/alteração; 201 criação; 400 dados inválidos; 401 autenticação ausente/inválida; 403 sem permissão; 404 inexistente; 409 duplicidade, indisponibilidade ou conflito de estoque; 413 JSON grande; 500 falha interna. O backend não envia detalhes internos do MySQL ao navegador.

## Demonstração sugerida para a defesa

1. Adicione um produto ao carrinho sem login e tente finalizar.
2. Cadastre ou autentique um cliente; mostre que o carrinho foi preservado.
3. Confirme a compra e mostre o número do pedido e o carrinho vazio.
4. Abra Meus Pedidos. No Workbench, consulte `pedidos`, `itens_pedido`, `produtos` e `movimentacoes_estoque`.
5. Saia e entre como admin. Cadastre um produto ou edite seu preço; faça uma entrada e uma saída justificada. Mostre o histórico e os itens dos pedidos no painel.

## Solução de problemas

- `ER_ACCESS_DENIED_ERROR`: confira usuário e senha do MySQL no `.env`.
- `ECONNREFUSED`: inicie o serviço MySQL e confira host/porta.
- `ER_BAD_DB_ERROR` ou tabela inexistente: execute o schema completo no servidor MySQL indicado no `.env`.
- Aviso sobre `JWT_SECRET`: substitua o texto de exemplo por uma chave aleatória de pelo menos 32 caracteres.
- Porta ocupada: pare outra instância da loja ou altere `PORT`; abra o endereço com a nova porta.
- Sessão encerrada: faça login novamente. Logout revoga somente a sessão atual.
- Schema já importado: não execute novamente; os dados são persistentes. O script não é uma migração de uma instalação anterior.
