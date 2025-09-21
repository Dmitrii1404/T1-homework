INSERT INTO product_registries (client_id, account_id, product_id, interest_rate, open_date) VALUES
    ('2', '3', '3', '5.0', CURRENT_DATE),
    ('2', '4', '3', '5.0', CURRENT_DATE);


INSERT INTO payment_registries (product_registry_id, date, amount, interest_rate_amount, debt_amount, expired, payment_expiration_date) VALUES
    ('1', CURRENT_DATE, '100000', '5.0', '1000', 'false', (CURRENT_DATE + INTERVAL '12 month')::date),
    ('2', CURRENT_DATE, '200000', '5.0', '1000', 'false', (CURRENT_DATE + INTERVAL '12 month')::date);
