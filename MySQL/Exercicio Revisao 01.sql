-- 1. Criação do Banco de Dados
CREATE DATABASE bd_hospital;

-- 2. Definição de qual banco de dados receberá as tabelas
USE bd_hospital;

-- 3. Criação da tabela de Pacientes
CREATE TABLE tb_paciente (
    matricula INT PRIMARY KEY,
    paciente CHAR(50),
    telefone CHAR(11),
    endereco CHAR(30)
);

-- 4. Criação da tabela de Exames
CREATE TABLE tb_exame (
    cd_exame INT PRIMARY KEY,
    exame CHAR(30)
);

-- 5. Criação da tabela de Convênios
CREATE TABLE tb_convenio (
    cd_convenio INT PRIMARY KEY,
    convenio CHAR(30)
);

-- 6. Criação da tabela de Relacionamento (Associativa)
CREATE TABLE rl_exame_paciente (
    cd_exame INT,
    cd_convenio INT,
    matricula INT,
    dt_exame DATE,
    
    -- Chave primária composta
    PRIMARY KEY (cd_exame, cd_convenio, matricula, dt_exame),
    
    -- Chaves estrangeiras
    FOREIGN KEY (cd_exame) REFERENCES tb_exame(cd_exame),
    FOREIGN KEY (cd_convenio) REFERENCES tb_convenio(cd_convenio),
    FOREIGN KEY (matricula) REFERENCES tb_paciente(matricula)
);

-- Inserindo dados na tabela tb_exame
insert into tb_exame
(cd_exame, exame)
values
(1, 'Mamografia'),
(2, 'Desintometria óssea'),
(3, 'RM - Ressonância Magnética'),
(4, 'Tomografia Computadorizada'),
(5, 'Ultra-sonografia'),
(6, 'Ecografia tridimensional'),
(7, 'Ecodopler');

-- Inserindo dados na tabela tb_convenio
insert into tb_convenio
(cd_convenio, convenio)
values
(1, 'Qualicorp'),
(2, 'Allianz'),
(3, 'Cassi'),
(4, 'Bradesco');

-- Inserindo dados na tabela tb_paciente
insert into tb_paciente
(matricula, paciente, telefone, endereco)
values
(1, 'Joao', '22101010', 'Rua 10'),
(2, 'Maria', '22101011', 'Rua 11'),
(3, 'Carlos', '22101012', 'Rua 12'),
(4, 'Paula', '22101013', 'Rua 13'),
(5, 'Ricardo', '22101014', 'Rua 14'),
(6, 'Hugo', '22101015', 'Rua 15'),
(7, 'Pedro', '22101016', 'Rua 16');

-- Inserindo dados na tabela rl_exame_paciente (Relacionamento)
insert into rl_exame_paciente
(cd_exame, cd_convenio, matricula, dt_exame)
values
(1, 1, 1, '2021-01-01'),
(2, 2, 2, '2021-01-02'),
(3, 3, 3, '2021-01-03'),
(4, 1, 4, '2021-01-04'),
(5, 2, 1, '2021-01-05'),
(6, 3, 2, '2021-01-06'),
(7, 1, 3, '2021-01-07'),
(1, 1, 4, '2021-01-08');