CREATE TABLE IF NOT EXISTS accounts (
    id BIGSERIAL PRIMARY KEY,
    client_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    balance NUMERIC(18, 2) NOT NULL,
    interest_rate NUMERIC(5, 4) NOT NULL,
    is_recalc BOOL NOT NULL,
    card_exist BOOL NOT NULL,
    status VARCHAR(20) NOT NULL
    );

CREATE INDEX IF NOT EXISTS idx_accounts_clientid ON accounts(client_id);
CREATE INDEX IF NOT EXISTS idx_accounts_productid ON accounts(product_id);


CREATE TABLE IF NOT EXISTS cards (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    card_id VARCHAR(100) NOT NULL UNIQUE,
    payment_system VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    CONSTRAINT fk_card_account FOREIGN KEY (account_id) REFERENCES accounts(id)
    );

CREATE INDEX IF NOT EXISTS idx_cards_cardid ON cards(card_id);


CREATE TABLE IF NOT EXISTS payments (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    payment_date DATE NOT NULL,
    amount NUMERIC(18, 2) NOT NULL,
    is_credit BOOL NOT NULL,
    payed_at DATE,
    type VARCHAR(20) NOT NULL,
    CONSTRAINT fk_payments_account FOREIGN KEY (account_id) REFERENCES accounts(id)
);


CREATE TABLE IF NOT EXISTS transactions (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    card_id BIGINT NOT NULL,
    type VARCHAR(20) NOT NULL,
    amount NUMERIC(18, 2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    timestamp DATE NOT NULL,
    CONSTRAINT fk_transaction_account FOREIGN KEY (account_id) REFERENCES accounts(id),
    CONSTRAINT fk_transaction_card FOREIGN KEY (card_id) REFERENCES cards(id)
    );
