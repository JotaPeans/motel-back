CREATE TABLE Funcionario (
    id INTEGER NOT NULL AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE Telefone (
    Telefone_PK INTEGER NOT NULL AUTO_INCREMENT,
    numero VARCHAR(255) NOT NULL,
    funcionarioId INTEGER NOT NULL,

    PRIMARY KEY (Telefone_PK)
);

CREATE TABLE Gerente (
    id INTEGER NOT NULL AUTO_INCREMENT,
    funcionarioId INTEGER NOT NULL,

    UNIQUE INDEX Gerente_funcionarioId_key(funcionarioId),
    PRIMARY KEY (id)
);

CREATE TABLE Camareira (
    id INTEGER NOT NULL AUTO_INCREMENT,
    funcionarioId INTEGER NOT NULL,
    gerenteId INTEGER NOT NULL,

    UNIQUE INDEX Camareira_funcionarioId_key(funcionarioId),
    PRIMARY KEY (id)
);

CREATE TABLE Cliente (
    id INTEGER NOT NULL AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    telefone VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    CPF VARCHAR(255) NOT NULL,
    RG VARCHAR(255) NOT NULL,

    UNIQUE INDEX Cliente_CPF_key(CPF),
    PRIMARY KEY (id)
);

CREATE TABLE Servico (
    id INTEGER NOT NULL AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    valor DOUBLE NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE Produto (
    id INTEGER NOT NULL AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    tipo VARCHAR(255) NOT NULL,
    valor DOUBLE NOT NULL,
    custo DOUBLE NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE Quarto (
    id INTEGER NOT NULL AUTO_INCREMENT,
    numero INTEGER NOT NULL,
    tipo ENUM("DELUXE", "SUITE") DEFAULT "SUITE" NOT NULL,
    status ENUM("DISPONIVEL", "MANUTENCAO", "LIMPEZA") DEFAULT "DISPONIVEL" NOT NULL,
    valor FLOAT NOT NULL,

    PRIMARY KEY (id)
);

-- Quarto Possui tantos Produtos.
CREATE TABLE Possui (
    produtoId INTEGER NOT NULL,
    quartoId INTEGER NOT NULL,
    quantidade INTEGER,

    PRIMARY KEY (produtoId, quartoId)
);

CREATE TABLE Reserva (
	id INTEGER NOT NULL AUTO_INCREMENT,
	status ENUM("CONFIRMADA", "CANCELADA", "FINALIZADA") DEFAULT "CONFIRMADA" NOT NULL,
	data_checkin DATETIME DEFAULT (CURRENT_TIMESTAMP),
	data_checkout DATETIME NULL,
	funcionarioId INTEGER NOT NULL,
	clienteId INTEGER NOT NULL,
	quartoId INTEGER NOT NULL,
	
	PRIMARY KEY (id)
);

CREATE TABLE Consumo (
	id INTEGER NOT NULL AUTO_INCREMENT,
	produtoId INTEGER,
	servicoId INTEGER,
	clienteId INTEGER NOT NULL,
	data_consumo DATETIME DEFAULT (CURRENT_TIMESTAMP),
	quantidade INTEGER,
	valor FLOAT,
	
	PRIMARY KEY (id)
);

CREATE TABLE Pagamento (
	id INTEGER NOT NULL AUTO_INCREMENT,
	clienteId INTEGER NOT NULL,
	reservaId INTEGER,
	consumoId INTEGER,
	payment_provider_id BIGINT NOT NULL,
	forma_pagamento ENUM("PIX", "CREDITO", "DEBITO", "DINHEIRO") DEFAULT "PIX" NOT NULL,
	
	PRIMARY KEY (id)
);



ALTER TABLE Telefone ADD CONSTRAINT Telefone_funcionarioId_fkey FOREIGN KEY (funcionarioId) REFERENCES Funcionario(id) ON DELETE RESTRICT ON UPDATE CASCADE;


ALTER TABLE Gerente ADD CONSTRAINT Gerente_funcionarioId_fkey FOREIGN KEY (funcionarioId) REFERENCES Funcionario(id) ON DELETE RESTRICT ON UPDATE CASCADE;


ALTER TABLE Camareira ADD CONSTRAINT Camareira_funcionarioId_fkey FOREIGN KEY (funcionarioId) REFERENCES Funcionario(id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE Camareira ADD CONSTRAINT Camareira_gerenteId_fkey FOREIGN KEY (gerenteId) REFERENCES Gerente(id) ON DELETE RESTRICT ON UPDATE CASCADE;


ALTER TABLE Possui ADD CONSTRAINT Possui_produtoId_fkey FOREIGN KEY (produtoId) REFERENCES Produto(id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE Possui ADD CONSTRAINT Possui_quartoId_fkey FOREIGN KEY (quartoId) REFERENCES Quarto(id) ON DELETE RESTRICT ON UPDATE CASCADE;


ALTER TABLE Reserva ADD CONSTRAINT Reserva_funcionarioId_fkey FOREIGN KEY (funcionarioId) REFERENCES Funcionario(id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE Reserva ADD CONSTRAINT Reserva_clienteId_fkey FOREIGN KEY (clienteId) REFERENCES Cliente(id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE Reserva ADD CONSTRAINT Reserva_quartoId_fkey FOREIGN KEY (quartoId) REFERENCES Quarto(id) ON DELETE RESTRICT ON UPDATE CASCADE;


ALTER TABLE Consumo ADD CONSTRAINT Consumo_produtoId_fkey FOREIGN KEY (produtoId) REFERENCES Produto(id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE Consumo ADD CONSTRAINT Consumo_servicoId_fkey FOREIGN KEY (servicoId) REFERENCES Servico(id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE Consumo ADD CONSTRAINT Consumo_clienteId_fkey FOREIGN KEY (clienteId) REFERENCES Cliente(id) ON DELETE RESTRICT ON UPDATE CASCADE;


ALTER TABLE Pagamento ADD CONSTRAINT Pagamento_clienteId_fkey FOREIGN KEY (clienteId) REFERENCES Cliente(id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE Pagamento ADD CONSTRAINT Pagamento_reservaId_fkey FOREIGN KEY (reservaId) REFERENCES Reserva(id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE Pagamento ADD CONSTRAINT Pagamento_consumoId_fkey FOREIGN KEY (consumoId) REFERENCES Consumo(id) ON DELETE RESTRICT ON UPDATE CASCADE;



-- LOGS

CREATE TABLE log_funcionario (
	id INTEGER NOT NULL AUTO_INCREMENT,
	nome VARCHAR(255) NOT NULL,
	email VARCHAR(255) NOT NULL,
	acao VARCHAR(255) NOT NULL,
	
	PRIMARY KEY (id)
);

CREATE TABLE log_telefone (
	id INTEGER NOT NULL AUTO_INCREMENT,
	numero VARCHAR(255) NOT NULL,
	acao VARCHAR(255) NOT NULL,
	
	PRIMARY KEY (id)
);

CREATE TABLE log_cliente (
	id INTEGER NOT NULL AUTO_INCREMENT,
	nome VARCHAR(255) NOT NULL,
    telefone VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    CPF VARCHAR(255) NOT NULL,
    RG VARCHAR(255) NOT NULL,
	acao VARCHAR(255) NOT NULL,
	
	PRIMARY KEY (id)
);

CREATE TABLE log_servico (
	id INTEGER NOT NULL AUTO_INCREMENT,
	nome VARCHAR(255) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    valor DOUBLE NOT NULL,
    acao VARCHAR(255) NOT NULL,
	
	PRIMARY KEY (id)
);

CREATE TABLE log_produto (
	id INTEGER NOT NULL AUTO_INCREMENT,
	nome VARCHAR(255) NOT NULL,
    tipo VARCHAR(255) NOT NULL,
    valor DOUBLE NOT NULL,
    custo DOUBLE NOT NULL,
    acao VARCHAR(255) NOT NULL,
	
	PRIMARY KEY (id)
);

CREATE TABLE log_quarto (
	id INTEGER NOT NULL AUTO_INCREMENT,
	numero INTEGER NOT NULL,
    tipo ENUM("DELUXE", "SUITE") DEFAULT "SUITE" NOT NULL,
    valor FLOAT NOT NULL,
    acao VARCHAR(255) NOT NULL,
	
	PRIMARY KEY (id)
);


CREATE TABLE log_reserva (
	id INTEGER NOT NULL AUTO_INCREMENT,
	data_checkin DATETIME DEFAULT (CURRENT_TIMESTAMP),
	data_checkout DATETIME NULL,
	clienteId INTEGER NOT NULL,
	quartoId INTEGER NOT NULL,
    acao VARCHAR(255) NOT NULL,
	
	PRIMARY KEY (id)
);

CREATE TABLE log_Consumo (
	id INTEGER NOT NULL AUTO_INCREMENT,
	produtoId INTEGER,
	servicoId INTEGER,
	clienteId INTEGER NOT NULL,
	data_consumo DATETIME DEFAULT (CURRENT_TIMESTAMP),
	quantidade INTEGER,
	valor FLOAT,
    acao VARCHAR(255) NOT NULL,
	
	PRIMARY KEY (id)
);

CREATE TABLE log_Pagamento (
	id INTEGER NOT NULL AUTO_INCREMENT,
	clienteId INTEGER NOT NULL,
	reservaId INTEGER,
	consumoId INTEGER,
	forma_pagamento ENUM("PIX", "CREDITO", "DEBITO", "DINHEIRO") DEFAULT "PIX" NOT NULL,
    acao VARCHAR(255) NOT NULL,
	
	PRIMARY KEY (id)
);

DELIMITER //

CREATE TRIGGER trg_funcionario_insert AFTER INSERT ON Funcionario
FOR EACH ROW BEGIN
    INSERT INTO log_funcionario (nome, email, acao) VALUES (NEW.nome, NEW.email, 'INSERIDO');
END//

CREATE TRIGGER trg_funcionario_update AFTER UPDATE ON Funcionario
FOR EACH ROW BEGIN
    INSERT INTO log_funcionario (nome, email, acao) VALUES (NEW.nome, NEW.email, 'ATUALIZADO');
END//

CREATE TRIGGER trg_funcionario_delete AFTER DELETE ON Funcionario
FOR EACH ROW BEGIN
    INSERT INTO log_funcionario (nome, email, acao) VALUES (OLD.nome, OLD.email, 'DELETADO');
END//


CREATE TRIGGER trg_telefone_insert AFTER INSERT ON Telefone
FOR EACH ROW BEGIN
    INSERT INTO log_telefone (numero, acao) VALUES (NEW.numero, 'INSERIDO');
END//

CREATE TRIGGER trg_telefone_update AFTER UPDATE ON Telefone
FOR EACH ROW BEGIN
    INSERT INTO log_telefone (numero, acao) VALUES (NEW.numero, 'ATUALIZADO');
END//

CREATE TRIGGER trg_telefone_delete AFTER DELETE ON Telefone
FOR EACH ROW BEGIN
    INSERT INTO log_telefone (numero, acao) VALUES (OLD.numero, 'DELETADO');
END//


CREATE TRIGGER trg_cliente_insert AFTER INSERT ON Cliente
FOR EACH ROW BEGIN
    INSERT INTO log_cliente (nome, telefone, email, CPF, RG, acao) 
    VALUES (NEW.nome, NEW.telefone, NEW.email, NEW.CPF, NEW.RG, 'INSERIDO');
END//

CREATE TRIGGER trg_cliente_update AFTER UPDATE ON Cliente
FOR EACH ROW BEGIN
    INSERT INTO log_cliente (nome, telefone, email, CPF, RG, acao) 
    VALUES (NEW.nome, NEW.telefone, NEW.email, NEW.CPF, NEW.RG, 'ATUALIZADO');
END//

CREATE TRIGGER trg_cliente_delete AFTER DELETE ON Cliente
FOR EACH ROW BEGIN
    INSERT INTO log_cliente (nome, telefone, email, CPF, RG, acao) 
    VALUES (OLD.nome, OLD.telefone, OLD.email, OLD.CPF, OLD.RG, 'DELETADO');
END//


CREATE TRIGGER trg_servico_insert AFTER INSERT ON Servico
FOR EACH ROW BEGIN
    INSERT INTO log_servico (nome, descricao, valor, acao) 
    VALUES (NEW.nome, NEW.descricao, NEW.valor, 'INSERIDO');
END//

CREATE TRIGGER trg_servico_update AFTER UPDATE ON Servico
FOR EACH ROW BEGIN
    INSERT INTO log_servico (nome, descricao, valor, acao) 
    VALUES (NEW.nome, NEW.descricao, NEW.valor, 'ATUALIZADO');
END//

CREATE TRIGGER trg_servico_delete AFTER DELETE ON Servico
FOR EACH ROW BEGIN
    INSERT INTO log_servico (nome, descricao, valor, acao) 
    VALUES (OLD.nome, OLD.descricao, OLD.valor, 'DELETADO');
END//


CREATE TRIGGER trg_produto_insert AFTER INSERT ON Produto
FOR EACH ROW BEGIN
    INSERT INTO log_produto (nome, tipo, valor, custo, acao) 
    VALUES (NEW.nome, NEW.tipo, NEW.valor, NEW.custo, 'INSERIDO');
END//

CREATE TRIGGER trg_produto_update AFTER UPDATE ON Produto
FOR EACH ROW BEGIN
    INSERT INTO log_produto (nome, tipo, valor, custo, acao) 
    VALUES (NEW.nome, NEW.tipo, NEW.valor, NEW.custo, 'ATUALIZADO');
END//

CREATE TRIGGER trg_produto_delete AFTER DELETE ON Produto
FOR EACH ROW BEGIN
    INSERT INTO log_produto (nome, tipo, valor, custo, acao) 
    VALUES (OLD.nome, OLD.tipo, OLD.valor, OLD.custo, 'DELETADO');
END//


CREATE TRIGGER trg_quarto_insert AFTER INSERT ON Quarto
FOR EACH ROW BEGIN
    INSERT INTO log_quarto (numero, tipo, valor, acao) 
    VALUES (NEW.numero, NEW.tipo, NEW.valor, 'INSERIDO');
END//

CREATE TRIGGER trg_quarto_update AFTER UPDATE ON Quarto
FOR EACH ROW BEGIN
    INSERT INTO log_quarto (numero, tipo, valor, acao) 
    VALUES (NEW.numero, NEW.tipo, NEW.valor, 'ATUALIZADO');
END//

CREATE TRIGGER trg_quarto_delete AFTER DELETE ON Quarto
FOR EACH ROW BEGIN
    INSERT INTO log_quarto (numero, tipo, valor, acao) 
    VALUES (OLD.numero, OLD.tipo, OLD.valor, 'DELETADO');
END//


CREATE TRIGGER trg_reserva_insert AFTER INSERT ON Reserva
FOR EACH ROW BEGIN
    INSERT INTO log_reserva (data_checkin, data_checkout, clienteId, quartoId, acao) 
    VALUES (NEW.data_checkin, NEW.data_checkout, NEW.clienteId, NEW.quartoId, 'INSERIDO');
END//

CREATE TRIGGER trg_reserva_update AFTER UPDATE ON Reserva
FOR EACH ROW BEGIN
    INSERT INTO log_reserva (data_checkin, data_checkout, clienteId, quartoId, acao) 
    VALUES (NEW.data_checkin, NEW.data_checkout, NEW.clienteId, NEW.quartoId, 'ATUALIZADO');
END//

CREATE TRIGGER trg_reserva_delete AFTER DELETE ON Reserva
FOR EACH ROW BEGIN
    INSERT INTO log_reserva (data_checkin, data_checkout, clienteId, quartoId, acao) 
    VALUES (OLD.data_checkin, OLD.data_checkout, OLD.clienteId, OLD.quartoId, 'DELETADO');
END//


CREATE TRIGGER trg_Consumo_insert AFTER INSERT ON Consumo
FOR EACH ROW BEGIN
    INSERT INTO log_Consumo (produtoId, servicoId, clienteId, data_consumo, quantidade, valor, acao) 
    VALUES (NEW.produtoId, NEW.servicoId, NEW.clienteId, NEW.data_consumo, NEW.quantidade, NEW.valor, 'INSERIDO');
END//

CREATE TRIGGER trg_Consumo_update AFTER UPDATE ON Consumo
FOR EACH ROW BEGIN
    INSERT INTO log_Consumo (produtoId, servicoId, clienteId, data_consumo, quantidade, valor, acao) 
    VALUES (NEW.produtoId, NEW.servicoId, NEW.clienteId, NEW.data_consumo, NEW.quantidade, NEW.valor, 'ATUALIZADO');
END//

CREATE TRIGGER trg_Consumo_delete AFTER DELETE ON Consumo
FOR EACH ROW BEGIN
    INSERT INTO log_Consumo (produtoId, servicoId, clienteId, data_consumo, quantidade, valor, acao) 
    VALUES (OLD.produtoId, OLD.servicoId, OLD.clienteId, OLD.data_consumo, OLD.quantidade, OLD.valor, 'DELETADO');
END//


CREATE TRIGGER trg_Pagamento_insert AFTER INSERT ON Pagamento
FOR EACH ROW BEGIN
    INSERT INTO log_Pagamento (clienteId, reservaId, consumoId, forma_pagamento, acao) 
    VALUES (NEW.clienteId, NEW.reservaId, NEW.consumoId, NEW.forma_pagamento, 'INSERIDO');
END//

CREATE TRIGGER trg_Pagamento_update AFTER UPDATE ON Pagamento
FOR EACH ROW BEGIN
    INSERT INTO log_Pagamento (clienteId, reservaId, consumoId, forma_pagamento, acao) 
    VALUES (NEW.clienteId, NEW.reservaId, NEW.consumoId, NEW.forma_pagamento, 'ATUALIZADO');
END//

CREATE TRIGGER trg_Pagamento_delete AFTER DELETE ON Pagamento
FOR EACH ROW BEGIN
    INSERT INTO log_Pagamento (clienteId, reservaId, consumoId, forma_pagamento, acao) 
    VALUES (OLD.clienteId, OLD.reservaId, OLD.consumoId, OLD.forma_pagamento, 'DELETADO');
END//

DELIMITER ;





















