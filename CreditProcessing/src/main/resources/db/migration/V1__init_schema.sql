CREATE TABLE IF NOT EXISTS product_registries (
    id BIGSERIAL PRIMARY KEY,
    client_id BIGINT NOT NULL,
    account_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    interest_rate NUMERIC(5, 4) NOT NULL,
    open_date DATE NOT NULL,
    month_count INTEGER NOT NULL
);

CREATE INDEX idx_product_registries_client_id ON product_registries(client_id);


CREATE TABLE IF NOT EXISTS payment_registries (
    id BIGSERIAL PRIMARY KEY,
    product_registry_id BIGINT NOT NULL,
    date DATE NOT NULL,
    amount NUMERIC(18, 2) NOT NULL,
    interest_rate_amount NUMERIC(18, 2) NOT NULL,
    debt_amount NUMERIC(18, 2) NOT NULL,
    expired BOOL NOT NULL DEFAULT FALSE,
    payment_expiration_date DATE NOT NULL,
    CONSTRAINT fk_paymentr_productr FOREIGN KEY (product_registry_id) REFERENCES product_registries(id)
);

CREATE INDEX idx_payment_registries_product_registry_id ON payment_registries(product_registry_id);
CREATE INDEX idx_payment_registries_payment_expiration_date ON payment_registries(payment_expiration_date);
CREATE INDEX idx_payment_registries_expired ON payment_registries(expired);
