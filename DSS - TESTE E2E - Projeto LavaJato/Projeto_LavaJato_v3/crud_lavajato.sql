CREATE DATABASE lavajato;
USE lavajato;

CREATE TABLE usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL
);

INSERT INTO usuario (login, senha)
VALUES ('admin', '1234');
CREATE TABLE cliente (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    telefone VARCHAR(20),
    placa VARCHAR(10),
    modelo_veiculo VARCHAR(50),
    marca_veiculo VARCHAR(50)
);

CREATE TABLE funcionario (
    id_funcionario INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cargo VARCHAR(50),
    telefone VARCHAR(20)
);

CREATE TABLE servico (
    id_servico INT AUTO_INCREMENT PRIMARY KEY,
    nome_servico VARCHAR(100) NOT NULL,
    descricao VARCHAR(255),
    valor DECIMAL(10,2) NOT NULL
);

CREATE TABLE atendimento (
    id_atendimento INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    id_funcionario INT NOT NULL,
    data_atendimento DATETIME NOT NULL,
    observacao VARCHAR(255),

    FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
    FOREIGN KEY (id_funcionario) REFERENCES funcionario(id_funcionario)
);

CREATE TABLE atendimento_servico (
    id_atendimento INT NOT NULL,
    id_servico INT NOT NULL,
    quantidade INT DEFAULT 1,

    PRIMARY KEY (id_atendimento, id_servico),

    FOREIGN KEY (id_atendimento) REFERENCES atendimento(id_atendimento),
    FOREIGN KEY (id_servico) REFERENCES servico(id_servico)
);

INSERT INTO cliente (nome, telefone, placa, modelo_veiculo, marca_veiculo)
VALUES ('João', '61996569908', 'ABC1234', 'Civic', 'Honda');

INSERT INTO funcionario (nome, cargo, telefone)
VALUES ('Carlos', 'Lavador', '61988888888');

INSERT INTO servico (nome_servico, descricao, valor)
VALUES ('Lavagem Completa', 'Lavagem externa e interna', 50.00);

INSERT INTO atendimento (id_cliente, id_funcionario, data_atendimento, observacao)
VALUES (1, 1, NOW(), 'Primeiro atendimento teste');

INSERT INTO atendimento_servico (id_atendimento, id_servico, quantidade)
VALUES (1, 1, 1);

INSERT INTO cliente (nome, telefone, placa, modelo_veiculo, marca_veiculo)
VALUES ('Maria', '61993469798', 'DFG5678', 'Celta', 'Chevrolet');

INSERT INTO funcionario (nome, cargo, telefone)
VALUES ('José', 'Lavador', '61977777777');

INSERT INTO servico (nome_servico, descricao, valor)
VALUES ('Lavagem Parcial', 'Lavagem externa', 35.00);

INSERT INTO atendimento (id_cliente, id_funcionario, data_atendimento, observacao)
VALUES (2, 2, NOW(), 'Segundo atendimento teste');

INSERT INTO atendimento_servico (id_atendimento, id_servico, quantidade)
VALUES (2, 2, 1);

INSERT INTO cliente (nome, telefone, placa, modelo_veiculo, marca_veiculo)
VALUES ('Pedro', '61991234567', 'HIJ9012', 'HB20', 'Hyundai');

INSERT INTO atendimento (id_cliente, id_funcionario, data_atendimento, observacao)
VALUES (3, 1, NOW(), 'Terceiro atendimento teste');

INSERT INTO atendimento_servico (id_atendimento, id_servico, quantidade)
VALUES (3, 2, 1);

SELECT * FROM cliente;
SELECT * FROM funcionario;
SELECT * FROM servico;
SELECT * FROM atendimento;
SELECT * FROM atendimento_servico;

SELECT 
    a.id_atendimento,
    c.nome AS cliente,
    c.placa,
    f.nome AS funcionario,
    s.nome_servico,
    asv.quantidade,
    s.valor,
    a.data_atendimento,
    a.observacao
FROM atendimento a
JOIN cliente c ON c.id_cliente = a.id_cliente
JOIN funcionario f ON f.id_funcionario = a.id_funcionario
JOIN atendimento_servico asv ON asv.id_atendimento = a.id_atendimento
JOIN servico s ON s.id_servico = asv.id_servico
ORDER BY a.id_atendimento;

UPDATE cliente
SET telefone = '61900000000'
WHERE id_cliente = 1;

UPDATE funcionario
SET cargo = 'Supervisor'
WHERE id_funcionario = 1;

UPDATE servico
SET valor = 80.00
WHERE id_servico = 1;

UPDATE atendimento
SET observacao = 'Preservar banco de couro'
WHERE id_atendimento = 1;

DELETE FROM atendimento_servico
WHERE id_atendimento = 1;

DELETE FROM atendimento
WHERE id_atendimento = 1;

DELETE FROM cliente
WHERE id_cliente = 1;
