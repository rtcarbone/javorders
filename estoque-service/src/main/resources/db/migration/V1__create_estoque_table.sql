CREATE TABLE estoques (
    id SERIAL PRIMARY KEY,
    sku VARCHAR(100) NOT NULL UNIQUE,
    quantidade INTEGER
);