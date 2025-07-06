CREATE TABLE pagamentos (
    id SERIAL PRIMARY KEY,
    cliente_id BIGINT,
    valor NUMERIC(10,2),
    uuid_transacao UUID,
    status VARCHAR(50)
);