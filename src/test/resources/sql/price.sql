DROP TABLE price IF EXISTS CASCADE;
--Price
INSERT INTO price (brand_id, product_id, currency_code, start_date, end_date, priority, price)
VALUES
(1, 35455, 'EUR', '2020-06-14 00:00:00', '2020-12-31 23:59:59', 0, 35.50),
(1, 35455, 'EUR', '2020-06-14 15:00:00', '2020-06-14 18:30:00', 1, 25.45),
(1, 35455, 'EUR', '2020-06-15 00:00:00', '2020-06-15 11:00:00', 1, 30.50),
(1, 35455, 'EUR', '2020-06-15 16:00:00', '2020-12-31 23:59:59', 1, 38.95);