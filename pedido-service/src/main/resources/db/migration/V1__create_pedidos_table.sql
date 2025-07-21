CREATE TABLE pedidos
(
    id             SERIAL PRIMARY KEY,
    cliente_id     BIGINT,
    valor_total    NUMERIC(10, 2),
    status         VARCHAR(50),
    uuid_transacao UUID
);

CREATE TABLE itens_pedido
(
    id         SERIAL PRIMARY KEY,
    sku        VARCHAR(100),
    quantidade INTEGER,
    pedido_id  BIGINT REFERENCES pedidos (id) ON DELETE CASCADE
);