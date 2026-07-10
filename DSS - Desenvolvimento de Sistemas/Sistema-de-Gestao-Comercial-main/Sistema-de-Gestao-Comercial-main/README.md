# Sistema de Gestão Comercial para loja de informatica

## 🛒 Objetivo Educacional

Este é um **projeto educacional em template** desenvolvido para que os alunos compreendam e implementem o funcionamento de uma **arquitetura em camadas**, muito utilizada no desenvolvimento de sistemas em Java.

### Como usar este projeto:
- A estrutura das classes já está organizada
- Os métodos devem ser implementados pelos alunos
- Utilize este README como guia
- O sistema simula um cenário real de comércio

### O que você vai aprender:
- Organização em camadas (Controller → Service → Repository)
- Separação de responsabilidades
- Implementação de regras de negócio
- CRUD completo
- Relacionamento entre entidades
- Controle de estoque
- Registro de vendas

---

## 🏗️ Estrutura da Arquitetura em Camadas

```
┌─────────────────────────────────────┐
│     CAMADA DE APRESENTAÇÃO          │
│     (LivroController)               │
│     - Recebe requisições            │
│     - Controla o fluxo da aplicação │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│     CAMADA DE NEGÓCIOS              │
│     (LivroService)                  │
│     - Lógica de negócio             │
│     - Regras de validação           │
│     - Processamento de dados        │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│     CAMADA DE PERSISTÊNCIA          │
│     (LivroRepository)               │
│     - Acesso aos dados              │
│     - Operações no banco de dados   │
└─────────────────────────────────────┘
```


```
arquitetura_camadas/
├── Cliente.java                 # Entidade (Model)
├── ClienteController.java       # Camada de Apresentação
├── ClienteService.java          # Interface - Camada de Negócios
├── ClienteRepository.java       # Interface - Camada de Persistência
├── Main.java                    # Ponto de entrada da aplicação
└── README.md                    # Este arquivo
```

## 🔍 Descrição das Entidades

### 👤 Cliente
- id  
- nome  
- cpf  
- email  
- telefone  
- endereco  

---

### 📦 Produto
- id  
- nome  
- descricao  
- preco  
- quantidadeEstoque  

---

### 👨‍💻 Usuario
- id  
- login  
- senha
  
---

### 🧾 Venda
- id  
- data  
- valorTotal  
- clienteId  
- usuarioId  

---

### 🛒 ItemVenda
- id  
- produtoId  
- quantidade  
- precoUnitario  

---

## 1️⃣ Camada de Apresentação (Controllers)

Responsável por receber as ações do usuário e chamar o service.

### ClienteController
- cadastrar  
- buscar por id  
- listar  
- atualizar  
- remover  

### ProdutoController
- cadastrar  
- buscar  
- listar  
- atualizar  

### VendaController
- registrar venda  
- consultar venda  

### UsuarioController
- login  
- cadastrar usuário  

---

## 2️⃣ Camada de Negócios (Services)

Responsável pelas regras do sistema.

### ClienteService
- valida CPF único  
- valida campos obrigatórios  

### ProdutoService
- valida preço > 0  
- controla estoque  

### VendaService
- registra venda  
- calcula valor total  
- diminui estoque  
- valida estoque disponível  

### UsuarioService
- valida login  
- controla tipo de usuário  

---

## 3️⃣ Camada de Persistência (Repositories)

Responsável por salvar e buscar dados.

### ClienteRepository
- salvar  
- buscar por id  
- listar  
- atualizar  
- deletar  

### ProdutoRepository
- salvar  
- buscar  
- listar  

### UsuarioRepository
- salvar  
- buscar por login  

### VendaRepository
- salvar  
- buscar venda  

---

## ✅ Funcionalidades do Sistema

### Gestão de Clientes
- Cadastro de clientes  
- Consulta de clientes  
- Atualização  
- Remoção  

### Gestão de Produtos
- Cadastro de produtos  
- Consulta  
- Controle de estoque  

### Gestão de Vendas
- Registro de venda  
- Cálculo automático do valor  
- Atualização de estoque  

### Autenticação
- Login de usuário  
- Controle de acesso  

---

## 📖 Exemplo de Uso

O arquivo `Main.java` pode ser utilizado para testar o sistema.

Após implementar os métodos:
- cadastrar clientes  
- cadastrar produtos  
- registrar vendas  

---

## 💡 Fluxo de Venda

### Registrar Venda

1. Usuário seleciona cliente  
2. Usuário seleciona produtos  
3. Informa quantidade  
4. Sistema verifica estoque  
5. Sistema calcula valor total  
6. Sistema salva venda  
7. Sistema atualiza estoque  

---

## 🚀 Guia de Implementação

### 1️⃣ Repository
- Utilizar estruturas como `List` ou `ArrayList`  
- Implementar salvar, buscar, listar  

### 2️⃣ Service
- Validar dados  
- Aplicar regras de negócio  

### 3️⃣ Controller
- Receber dados  
- Chamar o service  

### 4️⃣ Testar no Main
- Simular uso do sistema  

---

## ⚠️ Dicas Importantes

- Use estruturas simples  
- IDs podem ser gerados automaticamente  
- Sempre validar dados  
- Manter código organizado  
- Não complicar demais  

---

## ✅ Checklist

- [ ] Cadastro de cliente funcionando  
- [ ] Cadastro de produto funcionando  
- [ ] Registro de venda funcionando  
- [ ] Estoque sendo atualizado  
- [ ] Login funcionando  
- [ ] Sistema executa sem erros  

---

## 🔧 Padrões Utilizados

- Arquitetura em Camadas
- Model
- Controller
- Repository  
- Service  

---

## 📚 Conceitos Aplicados

- Organização de código  
- Separação de responsabilidades  
- CRUD  
- Relacionamento entre entidades  
- Lógica de negócio  

---

## 🚀 Melhorias Futuras

- Interface gráfica  
- API com Spring Boot  
- Banco de dados MySQL  
- Relatórios mais completos  
- Dashboard com gráficos  
- Autenticação com JWT  

---
