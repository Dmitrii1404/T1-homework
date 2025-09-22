CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    login VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE
    );

CREATE INDEX IF NOT EXISTS uk_user_email ON users(email);


CREATE TABLE IF NOT EXISTS products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    key VARCHAR(10) NOT NULL,
    create_date DATE NOT NULL
    );

CREATE INDEX IF NOT EXISTS uk_product_productid ON products(id);


CREATE TABLE IF NOT EXISTS clients (
    id BIGSERIAL PRIMARY KEY,
    client_id VARCHAR(12) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    middle_name VARCHAR(100),
    last_name VARCHAR(100),
    date_of_birth DATE,
    document_type VARCHAR(20) NOT NULL,
    document_id VARCHAR(100) NOT NULL UNIQUE,
    document_prefix VARCHAR(100),
    document_suffix VARCHAR(100),
    CONSTRAINT fk_client_user FOREIGN KEY (user_id) REFERENCES users(id)
    );

CREATE INDEX IF NOT EXISTS uk_client_clientid ON clients(client_id);


CREATE TABLE IF NOT EXISTS client_products (
    id BIGSERIAL PRIMARY KEY,
    client_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    open_date DATE NOT NULL,
    close_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL,
    CONSTRAINT fk_cp_client FOREIGN KEY (client_id) REFERENCES clients(id),
    CONSTRAINT fk_cp_product FOREIGN KEY (product_id) REFERENCES products(id)
    );

CREATE TABLE IF NOT EXISTS blacklist_registries (
    id BIGSERIAL PRIMARY KEY,
    document_type VARCHAR(20),
    document_id VARCHAR(100) NOT NULL UNIQUE,
    black_listed_at DATE NOT NULL,
    reason VARCHAR(255),
    blacklist_expiration_date DATE NOT NULL
    );

CREATE INDEX IF NOT EXISTS uk_blacklist_documentid ON blacklist_registries(document_id);
