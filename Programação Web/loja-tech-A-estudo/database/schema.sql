CREATE DATABASE IF NOT EXISTS nexvion_tech_refatorado CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE nexvion_tech_refatorado;

-- Senhas são hashes; perfil público é sempre cliente.
CREATE TABLE usuarios (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(150) NOT NULL,
  email VARCHAR(150) NOT NULL UNIQUE,
  senha_hash VARCHAR(255) NOT NULL,
  perfil ENUM('admin','cliente') NOT NULL DEFAULT 'cliente',
  ativo TINYINT(1) NOT NULL DEFAULT 1,
  criado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- Categorias usadas pelo catálogo e pelo formulário administrativo.
CREATE TABLE categorias (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100) NOT NULL UNIQUE
) ENGINE=InnoDB;

-- O id é também o código; saldo nunca pode ser negativo.
CREATE TABLE produtos (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(200) NOT NULL,
  categoria_id INT NOT NULL,
  preco DECIMAL(10,2) NOT NULL CHECK (preco >= 0),
  estoque INT NOT NULL DEFAULT 0 CHECK (estoque >= 0),
  imagem_url VARCHAR(500),
  ativo TINYINT(1) NOT NULL DEFAULT 1,
  criado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (categoria_id) REFERENCES categorias(id)
) ENGINE=InnoDB;

-- Cada pedido pertence ao usuário que confirmou a compra.
CREATE TABLE pedidos (
  id INT AUTO_INCREMENT PRIMARY KEY,
  usuario_id INT NOT NULL,
  subtotal DECIMAL(10,2) NOT NULL CHECK (subtotal >= 0),
  desconto DECIMAL(10,2) NOT NULL DEFAULT 0 CHECK (desconto >= 0),
  total DECIMAL(10,2) NOT NULL CHECK (total >= 0),
  status VARCHAR(50) NOT NULL DEFAULT 'Concluído',
  criado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CHECK (total = subtotal - desconto),
  FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
) ENGINE=InnoDB;

-- Nome e preço são cópias do momento da compra.
CREATE TABLE itens_pedido (
  id INT AUTO_INCREMENT PRIMARY KEY,
  pedido_id INT NOT NULL,
  produto_id INT NOT NULL,
  nome_produto VARCHAR(200) NOT NULL,
  preco_unitario DECIMAL(10,2) NOT NULL CHECK (preco_unitario >= 0),
  quantidade INT NOT NULL CHECK (quantidade > 0),
  subtotal DECIMAL(10,2) NOT NULL CHECK (subtotal >= 0),
  UNIQUE (pedido_id, produto_id),
  FOREIGN KEY (pedido_id) REFERENCES pedidos(id),
  FOREIGN KEY (produto_id) REFERENCES produtos(id)
) ENGINE=InnoDB;

-- Cada mudança guarda quantidade, saldo, motivo, data e responsável.
CREATE TABLE movimentacoes_estoque (
  id INT AUTO_INCREMENT PRIMARY KEY,
  produto_id INT NOT NULL,
  tipo ENUM('entrada','saida','ajuste') NOT NULL,
  quantidade INT NOT NULL CHECK (quantidade >= 0),
  saldo_resultante INT NOT NULL CHECK (saldo_resultante >= 0),
  motivo VARCHAR(255),
  usuario_id INT,
  criado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (produto_id) REFERENCES produtos(id),
  FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
) ENGINE=InnoDB;

-- Sessões permitem invalidar o token imediatamente no logout.
CREATE TABLE sessoes (
  id CHAR(36) PRIMARY KEY,
  usuario_id INT NOT NULL,
  expira_em DATETIME NOT NULL,
  INDEX (expira_em),
  FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
) ENGINE=InnoDB;

INSERT INTO usuarios (nome, email, senha_hash, perfil) VALUES
('Administrador', 'admin@nexvion.com', '$2b$10$QSS7W0zMdfg2UbVLsqVaO.35253CNwDIicGvfu5sA8.L60opUOoSi', 'admin');

INSERT INTO categorias (nome) VALUES
('Notebooks'),
('Periféricos'),
('Games'),
('Acessórios');

INSERT INTO produtos (id, nome, categoria_id, preco, estoque, imagem_url) VALUES
(1, 'Notebook UltraSlim Pro 15"', 1, 3799.99, 5, 'https://images.unsplash.com/photo-1496181133206-80ce9b88a853?w=400&q=80'),
(2, 'Notebook Gamer FireEdge', 1, 6499.90, 3, 'https://images.unsplash.com/photo-1525547719571-a2d4ac8945e2?w=400&q=80'),
(3, 'Mouse Óptico Precision X', 2, 149.90, 20, 'https://images.unsplash.com/photo-1527814050087-3793815479db?w=400&q=80'),
(4, 'Teclado Mecânico RGB StrikeKeys', 2, 389.90, 8, 'https://images.unsplash.com/photo-1587829741301-dc798b83add3?w=400&q=80'),
(5, 'Headset Gamer SoundBlast 7.1', 3, 279.90, 12, 'https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=400&q=80'),
(6, 'Controle Gamepad Pro Wireless', 3, 319.90, 0, 'https://images.unsplash.com/photo-1592840496694-26d035b52b48?w=400&q=80'),
(7, 'Webcam Full HD 1080p AutoFocus', 4, 229.90, 15, 'https://images.unsplash.com/photo-1611532736597-de2d4265fba3?w=400&q=80'),
(8, 'Hub USB-C 7 em 1 TechConnect', 4, 189.90, 10, 'https://images.unsplash.com/photo-1625723044792-44de16ccb4e9?w=400&q=80');

-- Registra a origem dos saldos iniciais sem simular histórico de compras.
INSERT INTO movimentacoes_estoque (produto_id, tipo, quantidade, saldo_resultante, motivo, usuario_id)
SELECT id, 'entrada', estoque, estoque, 'Carga inicial de demonstração', 1 FROM produtos WHERE estoque > 0;
