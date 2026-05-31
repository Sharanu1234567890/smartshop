INSERT INTO customers (name, email, phone, total_orders, is_vip, created_at)
SELECT * FROM (VALUES
('Rahul Sharma', 'rahul@gmail.com', '9876543210', 15, true, NOW()),
('Priya Patel', 'priya@gmail.com', '9123456789', 3, false, NOW()),
('Amit Kumar', 'amit@gmail.com', '9988776655', 1, false, NOW())
) AS v(name, email, phone, total_orders, is_vip, created_at)
WHERE NOT EXISTS (SELECT 1 FROM customers LIMIT 1);

INSERT INTO orders (id, customer_id, product_name, product_category,
amount, status, order_date, delivery_date, return_status,
refund_status, is_high_value, is_returnable)
SELECT * FROM (VALUES
('ORD001', 1, 'Samsung Galaxy M34', 'Phone', 18999,
'DELIVERED', NOW()-INTERVAL '5 days', NOW()-INTERVAL '2 days',
'NONE', 'NONE', true, true),
('ORD002', 1, 'boAt Airdopes 141', 'Earphones', 1299,
'DELIVERED', NOW()-INTERVAL '10 days', NOW()-INTERVAL '7 days',
'NONE', 'NONE', false, true),
('ORD003', 2, 'Philips Air Fryer', 'Kitchen', 6999,
'SHIPPED', NOW()-INTERVAL '2 days', NOW()+INTERVAL '1 day',
'NONE', 'NONE', false, true),
('ORD004', 2, 'Nike Air Max 270', 'Footwear', 7999,
'DELIVERED', NOW()-INTERVAL '20 days', NOW()-INTERVAL '17 days',
'NONE', 'NONE', false, true),
('ORD005', 3, 'Sony WH-1000XM4', 'Earphones', 24999,
'DELIVERED', NOW()-INTERVAL '3 days', NOW()-INTERVAL '1 day',
'NONE', 'NONE', true, true)
) AS v(id, customer_id, product_name, product_category, amount,
status, order_date, delivery_date, return_status, refund_status,
is_high_value, is_returnable)
WHERE NOT EXISTS (SELECT 1 FROM orders LIMIT 1);