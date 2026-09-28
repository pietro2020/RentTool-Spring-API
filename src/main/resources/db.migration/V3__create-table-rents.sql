CREATE TABLE rents (
    id BIGSERIAL PRIMARY KEY,
    start_date TIMESTAMP,
    expected_return_date TIMESTAMP,
    actual_return_date TIMESTAMP,
    rent_status BOOLEAN,
    client_id BIGINT NOT NULL,
    tool_id BIGINT NOT NULL,

    CONSTRAINT fk_rent_client
        FOREIGN KEY (client_id)
        REFERENCES clients(id),

    CONSTRAINT fk_rent_tool
        FOREIGN KEY (tool_id)
        REFERENCES tools(id)
);