INSERT INTO users (login, password, email) VALUES
    ('Dima', 'myHashPassword', 'Dima@yandex.ru'),
    ('Andrey', 'myHashPassword', 'Andrey@yandex.ru');


INSERT INTO products (name, key) VALUES
    ('Debit Card', 'DC'),
    ('Credit Card', 'CC'),
    ('Ipoteka', 'IPO');

INSERT INTO clients (client_id, user_id, first_name, middle_name, last_name, date_of_birth, document_type, document_id, document_prefix, document_suffix) VALUES
    ('770100000001', 1, 'Dima', 'Dmitrievich', 'Dmitrii', '2005-04-14', 'PASSPORT', '1234567890', 'somePrefix', 'someSuffix'),
    ('770100000002', 2, 'Andrey', 'Andreevich', 'Andrey', '2006-08-13', 'PASSPORT', '098765432', 'somePrefix2', 'someSuffix2');


INSERT INTO client_products (client_id, product_id, open_date, close_date, status) VALUES
    (1, (SELECT id FROM products WHERE key = 'DC' LIMIT 1), CURRENT_DATE, (CURRENT_DATE + INTERVAL '1 month')::date, 'ACTIVE'),
    (2, (SELECT id FROM products WHERE key = 'IPO' LIMIT 1), CURRENT_DATE, (CURRENT_DATE + INTERVAL '12 month')::date, 'ACTIVE');

INSERT INTO blacklist_registries (document_type, document_id, black_listed_at, reason, blacklist_expiration_date) VALUES
    ('PASSPORT', '3454628225', CURRENT_DATE, 'Banned', (CURRENT_DATE + INTERVAL '3 month')::date),
    ('PASSPORT', '4940400404', CURRENT_DATE, 'Have ban', (CURRENT_DATE + INTERVAL '2 month')::date);
