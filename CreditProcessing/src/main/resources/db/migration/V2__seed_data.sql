INSERT INTO product_registries (client_id, account_id, product_id, interest_rate, open_date, month_count) VALUES
    ('2', '3', '3', '0.20', CURRENT_DATE, '1234'),
    ('2', '4', '3', '0.19', CURRENT_DATE, '5678');


INSERT INTO payment_registries (product_registry_id, date, amount, interest_rate_amount, debt_amount, expired, payment_expiration_date) VALUES
    ('1', CURRENT_DATE, '100000', '5.0', '1000', 'false', (CURRENT_DATE + INTERVAL '12 month')::date),
    ('2', CURRENT_DATE, '200000', '5.0', '1000', 'false', (CURRENT_DATE + INTERVAL '12 month')::date);
