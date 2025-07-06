CREATE TABLE clientes (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255),
    cpf VARCHAR(14) NOT NULL UNIQUE,
    data_nascimento DATE
);

CREATE TABLE enderecos (
    id SERIAL PRIMARY KEY,
    rua VARCHAR(255),
    numero VARCHAR(50),
    cep VARCHAR(20),
    cliente_id BIGINT REFERENCES clientes(id) ON DELETE CASCADE
);