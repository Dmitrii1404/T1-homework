INSERT INTO accounts (client_id, product_id, balance, interest_rate, is_recalc, card_exist, status) VALUES
    ('1', '1', '10000.50', '5.0', 'true', 'false', 'ACTIVE'),
    ('1', '1', '200000.00', '4.0', 'true', 'false', 'ACTIVE'),
    ('2', '3', '100000', '5.0', 'true', 'false', 'ACTIVE'),
    ('2', '3', '2000000', '5.0', 'true', 'false', 'ACTIVE');


INSERT INTO cards (account_id, card_id, payment_system, status) VALUES
    ('1', '1234567890', 'VISA', 'ACTIVE'),
    ('2', '0987654321', 'MIR', 'ACTIVE');


INSERT INTO payments (account_id, payment_date, amount, is_credit, payed_at, type) VALUES
    ('1', CURRENT_DATE, '1234.12', 'false', CURRENT_DATE, 'REGULAR'),
    ('2', CURRENT_DATE, '5678.90', 'true', CURRENT_DATE, 'REGULAR');


INSERT INTO transactions (account_id, card_id, type, amount, status, timestamp) VALUES
    ('1', '1', 'DEPOSIT', '1000.00', 'COMPLETE', CURRENT_DATE),
    ('2', '2', 'WITHDRAWAL', '5000.00', 'PROCESSING', CURRENT_DATE);