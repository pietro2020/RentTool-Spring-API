CREATE TABLE clients (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    cpf VARCHAR(14),
    customer_category VARCHAR(50),
    defaulted BOOLEAN NOT NULL DEFAULT FALSE
);
