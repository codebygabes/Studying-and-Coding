CREATE TABLE cliente (
   id INT AUTO_INCREMENT PRIMARY KEY,
   nome VARCHAR(100) NOT NULL,
   cpf VARCHAR(14) NOT NULL UNIQUE,
   email VARCHAR(100),
   telefone VARCHAR(20),
   endereco VARCHAR(150)
);

CREATE TABLE produto (
   id INT AUTO_INCREMENT PRIMARY KEY,
   nome VARCHAR(100) NOT NULL,
   descricao VARCHAR(200),
   preco DECIMAL(10,2) NOT NULL,
   quantidade_estoque INT NOT NULL
);

CREATE TABLE usuario (
   id INT AUTO_INCREMENT PRIMARY KEY,
   login VARCHAR(50) NOT NULL UNIQUE,
   senha VARCHAR(100) NOT NULL
);

CREATE TABLE venda (
   id INT AUTO_INCREMENT PRIMARY KEY,
   data_venda DATE NOT NULL,
   valor_total DECIMAL(10,2) NOT NULL,
   cliente_id INT NOT NULL,
   usuario_id INT NOT NULL,
   CONSTRAINT fk_venda_cliente
       FOREIGN KEY (cliente_id) REFERENCES cliente(id),
   CONSTRAINT fk_venda_usuario
       FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

CREATE TABLE item_venda (
   id INT AUTO_INCREMENT PRIMARY KEY,
   quantidade INT NOT NULL,
   preco_unitario DECIMAL(10,2) NOT NULL,
   venda_id INT NOT NULL,
   produto_id INT NOT NULL,
   CONSTRAINT fk_item_venda
       FOREIGN KEY (venda_id) REFERENCES venda(id),
   CONSTRAINT fk_item_produto
       FOREIGN KEY (produto_id) REFERENCES produto(id)
);
