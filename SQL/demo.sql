CREATE DATABASE if not exists demo;
USE demo;

CREATE TABLE Products (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    productCode VARCHAR(20),
    productName VARCHAR(100),
    productPrice DECIMAL(10,2),
    productAmount INT,
    productDescription TEXT,
    productStatus VARCHAR(20)
);
-- Index & EXPLAIN
INSERT INTO Products (productCode, productName, productPrice, productAmount, productDescription, productStatus)
VALUES 
('P001', 'Laptop Dell', 1500.00, 10, 'Laptop cho văn phòng', 'Còn hàng'),
('P002', 'Chuột Logitech', 25.50, 50, 'Chuột không dây', 'Còn hàng'),
('P003', 'Bàn phím cơ', 65.00, 30, 'Bàn phím cho game thủ', 'Hết hàng');

CREATE UNIQUE INDEX idx_product_code ON Products(productCode);
CREATE INDEX idx_name_price ON Products(productName, productPrice);

EXPLAIN SELECT * FROM Products WHERE productCode = 'P001';

EXPLAIN SELECT * FROM Products WHERE productName = 'MacBook Pro' AND productPrice = 2000.00;

-- view 
CREATE VIEW view_product_info AS
SELECT productCode, productName, productPrice, productStatus FROM Products;

CREATE OR REPLACE VIEW view_product_info AS
SELECT productCode, productName, productPrice, productStatus, productAmount FROM Products;

DROP VIEW view_product_info;


-- Store Procedure
DELIMITER //
CREATE PROCEDURE GetAllProducts()
BEGIN
    SELECT * FROM Products;
END //
DELIMITER ;
DELIMITER //
CREATE PROCEDURE AddProduct(
    IN p_code VARCHAR(50),
    IN p_name VARCHAR(100),
    IN p_price DECIMAL(10,2),
    IN p_amount INT,
    IN p_description TEXT,
    IN p_status BOOLEAN
)
BEGIN
    INSERT INTO Products(productCode, productName, productPrice, productAmount, productDescription, productStatus)
    VALUES (p_code, p_name, p_price, p_amount, p_description, p_status);
END //
DELIMITER ;
DELIMITER //
CREATE PROCEDURE UpdateProductById(
    IN p_id INT,
    IN p_code VARCHAR(50),
    IN p_name VARCHAR(100),
    IN p_price DECIMAL(10,2),
    IN p_amount INT,
    IN p_description TEXT,
    IN p_status BOOLEAN
)
BEGIN
    UPDATE Products
    SET productCode = p_code,
        productName = p_name,
        productPrice = p_price,
        productAmount = p_amount,
        productDescription = p_description,
        productStatus = p_status
    WHERE Id = p_id;
END //
DELIMITER ;
DELIMITER //
CREATE PROCEDURE DeleteProductById(IN p_id INT)
BEGIN
    DELETE FROM Products WHERE Id = p_id;
END //
DELIMITER ;

CALL GetAllProducts();

CALL AddProduct('P005', 'AirPods Pro', 249.99, 15, 'Tai nghe Apple', 1);

CALL UpdateProductById(2, 'P002', 'iPhone 13 Pro', 1099.99, 7, 'Bản cao cấp hơn', 1);

CALL DeleteProductById(4);

