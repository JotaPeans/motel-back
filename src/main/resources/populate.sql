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

-- Inserindo 15 reservas com status, datas de check-in e check-out
INSERT INTO Reserva (funcionarioId, clienteId, quartoId, data_checkin, data_checkout, status) VALUES
  (1, 1, 1, '2025-05-01 14:00:00', '2025-05-03 12:00:00', 'FINALIZADA'),
  (1, 2, 2, '2025-05-02 15:30:00', '2025-05-04 11:00:00', 'FINALIZADA'),
  (1, 1, 3, '2025-05-05 16:00:00', '2025-05-07 10:00:00', 'FINALIZADA'),
  (1, 2, 1, '2025-05-06 13:15:00', '2025-05-09 12:00:00', 'FINALIZADA'),
  (1, 1, 2, '2025-05-08 14:45:00', '2025-05-10 11:30:00', 'FINALIZADA'),
  (1, 2, 3, '2025-05-09 12:00:00', '2025-05-11 13:00:00', 'FINALIZADA'),
  (1, 1, 1, '2025-05-10 15:00:00', '2025-05-12 12:00:00', 'FINALIZADA'),
  (1, 2, 2, '2025-05-11 14:00:00', '2025-05-13 11:00:00', 'FINALIZADA'),
  (1, 1, 3, '2025-05-12 16:00:00', '2025-05-14 10:00:00', 'FINALIZADA'),
  (1, 2, 1, '2025-05-13 13:00:00', '2025-05-15 12:00:00', 'FINALIZADA'),
  (1, 1, 2, '2025-05-14 14:30:00', '2025-05-16 11:00:00', 'CONFIRMADA'),
  (1, 2, 3, '2025-05-15 15:45:00', '2025-05-17 10:30:00', 'CONFIRMADA'),
  (1, 1, 1, '2025-05-20 14:00:00', '2025-05-22 12:00:00', 'CONFIRMADA'),
  (1, 2, 2, '2025-05-21 16:00:00', '2025-05-23 11:00:00', 'CONFIRMADA'),
  (1, 1, 3, '2025-05-22 17:00:00', '2025-05-24 12:00:00', 'CONFIRMADA');

-- Inserindo 15 consumos (produto ou serviço) para os clientes 1 e 2
INSERT INTO Consumo (produtoId, servicoId, clienteId, quantidade, valor) VALUES
  (NULL,  1,    1, 1,  150.00),   -- Cliente 1 fez 1 Massagem Relaxante
  (NULL,  2,    2, 1,  120.00),   -- Cliente 2 fez 1 Spa Facial
  (1,     NULL, 1, 1,  104.99),   -- Cliente 1 consumiu 1 Shampoo
  (2,     NULL, 2, 2,  209.98),   -- Cliente 2 consumiu 2 Sabonetes
  (3,     NULL, 1, 3,  314.97),   -- Cliente 1 consumiu 3 Águas Minerais
  (NULL,  1,    2, 2,  300.00),   -- Cliente 2 fez 2 Massagens Relaxantes
  (NULL,  2,    1, 2,  240.00),   -- Cliente 1 fez 2 Spa Faciais
  (1,     NULL, 2, 3,  314.97),   -- Cliente 2 consumiu 3 Shampoos
  (2,     NULL, 1, 1,  104.99),   -- Cliente 1 consumiu 1 Sabonete
  (3,     NULL, 2, 1,  104.99),   -- Cliente 2 consumiu 1 Água Mineral
  (NULL,  1,    1, 3,  450.00),   -- Cliente 1 fez 3 Massagens Relaxantes
  (NULL,  2,    2, 1,  120.00),   -- Cliente 2 fez 1 Spa Facial
  (1,     NULL, 1, 2,  209.98),   -- Cliente 1 consumiu 2 Shampoos
  (2,     NULL, 2, 3,  314.97),   -- Cliente 2 consumiu 3 Sabonetes
  (3,     NULL, 1, 2,  209.98);   -- Cliente 1 consumiu 2 Águas Minerais

-- Inserindo pagamentos para reservas de 1 a 15 (forma de pagamento: PIX)
INSERT INTO Pagamento (clienteId, reservaId, consumoId, payment_provider_id, forma_pagamento) VALUES
  (1,  1,  1,  100000000000001, 'PIX'),
  (2,  2,  2,  100000000000002, 'PIX'),
  (1,  3,  3,  100000000000003, 'PIX'),
  (2,  4,  4,  100000000000004, 'PIX'),
  (1,  5,  5,  100000000000005, 'PIX'),
  (2,  6,  6,  100000000000006, 'PIX'),
  (1,  7,  7,  100000000000007, 'PIX'),
  (2,  8,  8,  100000000000008, 'PIX'),
  (1,  9,  9,  100000000000009, 'PIX'),
  (2, 10, 10,  100000000000010, 'PIX'),
  (1, 11, 11,  100000000000011, 'PIX'),
  (2, 12, 12,  100000000000012, 'PIX'),
  (1, 13, 13,  100000000000013, 'PIX'),
  (2, 14, 14,  100000000000014, 'PIX'),
  (1, 15, 15,  100000000000015, 'PIX');
