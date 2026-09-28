CREATE TABLE tools (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    price DOUBLE PRECISION,
    category VARCHAR(50),
    description VARCHAR(255),
    minimum_rental_days INTEGER,
    available VARCHAR(50),
    tool_condition VARCHAR(50)
);