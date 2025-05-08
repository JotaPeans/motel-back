-- Inserindo clientes
INSERT INTO Cliente (nome, telefone, email, CPF, RG) VALUES
('Alice Silva', '11987654321', 'alice@email.com', '123.456.789-00', 'MG-12.345.678'),
('Bruno Costa', '21912345678', 'bruno@email.com', '987.654.321-00', 'RJ-98.765.432');

-- Inserindo Funcionario
INSERT INTO Funcionario (nome, email, senha) VALUES ('Joao', 'joao@email.com', 'senhaJoao123');

-- Inserindo Gerente
INSERT INTO Gerente (funcionarioId) VALUES (1);

-- Inserindo serviços
INSERT INTO Servico (nome, descricao, valor) VALUES
('Massagem Relaxante', 'Sessão de massagem de 60 minutos', 150.00),
('Spa Facial', 'Tratamento facial revitalizante', 120.00);

-- Inserindo produtos
INSERT INTO Produto (nome, tipo, valor, custo) VALUES
('Shampoo', 'Higiene', 104.99, 20.00),
('Sabonete', 'Higiene', 104.99, 19.00),
('Água Mineral', 'Consumo', 104.99, 29.99);

-- Inserindo quartos
INSERT INTO Quarto (numero, tipo, valor) VALUES
(101, 'DELUXE', 100),
(102, 'DELUXE', 99),
(103, 'SUITE', 299);

-- Associando produtos aos quartos (Possui)
INSERT INTO Possui (produtoId, quartoId, quantidade) VALUES
(1, 1, 2),  -- Shampoo no quarto 101
(2, 2, 3),  -- Sabonete no quarto 102
(3, 2, 5);  -- Água mineral no quarto 102

-- Inserindo reservas (assumindo funcionarioId = 1)
INSERT INTO Reserva (status, funcionarioId, clienteId, quartoId) VALUES
("FINALIZADA", 1, 1, 1),
("CONFIRMADA", 1, 2, 2),
("FINALIZADA", 1, 1, 1),
("FINALIZADA", 1, 2, 2),
("CANCELADA", 1, 1, 1),
("FINALIZADA", 1, 2, 2);

-- Inserindo consumo (serviço ou produto pode ser nulo, mas cliente sempre obrigatório)
INSERT INTO Consumo (produtoId, servicoId, clienteId, quantidade, valor) VALUES
(NULL, 1, 1, 1, 150.00),  -- Cliente 1 fez uma massagem
(2, NULL, 2, 2, 10.00);   -- Cliente 2 consumiu 2 águas minerais

-- Inserindo pagamentos (assumindo consumoId = 1 e 2 e reservaId = 1 e 2)
INSERT INTO Pagamento (clienteId, reservaId, consumoId, payment_provider_id, forma_pagamento) VALUES
(1, 1, 1, 123123123123123, 'CREDITO'),
(2, 2, 2, 432452345234523, 'PIX'),
(1, 3, NULL, 1231231231, 'PIX');
