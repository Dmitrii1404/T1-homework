CREATE TABLE IF NOT EXISTS product_registries (
    id BIGSERIAL PRIMARY KEY,
    client_id BIGINT NOT NULL,
    account_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    interest_rate NUMERIC NOT NULL,
    open_date DATE NOT NULL
    );


CREATE TABLE IF NOT EXISTS payment_registries (
    id BIGSERIAL PRIMARY KEY,
    product_registry_id BIGINT NOT NULL,
    date DATE NOT NULL,
    amount NUMERIC(18, 2) NOT NULL,
    interest_rate_amount NUMERIC(5, 4) NOT NULL,
    debt_amount NUMERIC(18, 2) NOT NULL,
    expired BOOL NOT NULL,
    payment_expiration_date DATE NOT NULL,
    CONSTRAINT fk_paymentr_productr FOREIGN KEY (product_registry_id) REFERENCES product_registries(id)
);