-- Sử dụng database demo
USE demo;

-- Xóa các Stored Procedures cũ nếu tồn tại
DROP PROCEDURE IF EXISTS GetAllProducts;
DROP PROCEDURE IF EXISTS GetProductById;
DROP PROCEDURE IF EXISTS SearchProductsByName;
DROP PROCEDURE IF EXISTS AddProduct;
DROP PROCEDURE IF EXISTS UpdateProduct;
DROP PROCEDURE IF EXISTS DeleteProduct;
DROP PROCEDURE IF EXISTS CheckProductExists;
DROP PROCEDURE IF EXISTS GetProductStatistics;
DROP PROCEDURE IF EXISTS GetAllCategories;
DROP PROCEDURE IF EXISTS GetCategoryById;
DROP PROCEDURE IF EXISTS AddCategory;
DROP PROCEDURE IF EXISTS UpdateCategory;
DROP PROCEDURE IF EXISTS DeleteCategory;
DROP PROCEDURE IF EXISTS AddProductWithCategory;

-- 1. Stored Procedure để lấy tất cả sản phẩm với thông tin category
DELIMITER //
CREATE PROCEDURE GetAllProducts()
BEGIN
    SELECT p.*, c.categoryName, c.categoryCode 
    FROM products p 
    LEFT JOIN categories c ON p.categoryId = c.Id 
    ORDER BY p.Id;
END //
DELIMITER ;

-- 2. Stored Procedure để lấy sản phẩm theo ID
DELIMITER //
CREATE PROCEDURE GetProductById(IN productId INT)
BEGIN
    SELECT p.*, c.categoryName, c.categoryCode 
    FROM products p 
    LEFT JOIN categories c ON p.categoryId = c.Id 
    WHERE p.Id = productId;
END //
DELIMITER ;

-- 3. Stored Procedure để tìm kiếm sản phẩm theo tên
DELIMITER //
CREATE PROCEDURE SearchProductsByName(IN searchName VARCHAR(255))
BEGIN
    SELECT p.*, c.categoryName, c.categoryCode 
    FROM products p 
    LEFT JOIN categories c ON p.categoryId = c.Id 
    WHERE p.productName LIKE CONCAT('%', searchName, '%') 
    ORDER BY p.Id;
END //
DELIMITER ;

-- 4. Stored Procedure để thêm sản phẩm mới
DELIMITER //
CREATE PROCEDURE AddProduct(
    IN p_productCode VARCHAR(50),
    IN p_productName VARCHAR(255),
    IN p_productPrice DECIMAL(10,2),
    IN p_productAmount INT,
    IN p_productDescription TEXT,
    IN p_productStatus VARCHAR(100),
    IN p_categoryId INT
)
BEGIN
    DECLARE EXIT HANDLER FOR 1062
    BEGIN
        SELECT -1 AS newId, 'Mã sản phẩm đã tồn tại' AS message;
    END;
    
    INSERT INTO products (productCode, productName, productPrice, productAmount, productDescription, productStatus, categoryId)
    VALUES (p_productCode, p_productName, p_productPrice, p_productAmount, p_productDescription, p_productStatus, p_categoryId);
    
    SELECT LAST_INSERT_ID() AS newId, 'Thêm sản phẩm thành công' AS message;
END //
DELIMITER ;

-- 5. Stored Procedure để cập nhật sản phẩm
DELIMITER //
CREATE PROCEDURE UpdateProduct(
    IN p_id INT,
    IN p_productCode VARCHAR(50),
    IN p_productName VARCHAR(255),
    IN p_productPrice DECIMAL(10,2),
    IN p_productAmount INT,
    IN p_productDescription TEXT,
    IN p_productStatus VARCHAR(100),
    IN p_categoryId INT
)
BEGIN
    UPDATE products 
    SET productCode = p_productCode,
        productName = p_productName,
        productPrice = p_productPrice,
        productAmount = p_productAmount,
        productDescription = p_productDescription,
        productStatus = p_productStatus,
        categoryId = p_categoryId
    WHERE Id = p_id;
    
    SELECT ROW_COUNT() AS affectedRows;
END //
DELIMITER ;

-- 6. Stored Procedure để xóa sản phẩm
DELIMITER //
CREATE PROCEDURE DeleteProduct(IN p_id INT)
BEGIN
    DELETE FROM products WHERE Id = p_id;
    SELECT ROW_COUNT() AS affectedRows;
END //
DELIMITER ;

-- 7. Stored Procedure để kiểm tra sản phẩm có tồn tại không
DELIMITER //
CREATE PROCEDURE CheckProductExists(IN p_id INT)
BEGIN
    SELECT COUNT(*) AS exists FROM products WHERE Id = p_id;
END //
DELIMITER ;

-- 8. Stored Procedure để lấy thống kê sản phẩm
DELIMITER //
CREATE PROCEDURE GetProductStatistics()
BEGIN
    SELECT 
        COUNT(*) AS totalProducts,
        SUM(productAmount) AS totalQuantity,
        AVG(productPrice) AS averagePrice,
        COUNT(CASE WHEN productStatus = 'Còn hàng' THEN 1 END) AS inStock,
        COUNT(CASE WHEN productStatus = 'Hết hàng' THEN 1 END) AS outOfStock
    FROM products;
END //
DELIMITER ;

-- 9. Stored Procedure để lấy tất cả categories
DELIMITER //
CREATE PROCEDURE GetAllCategories()
BEGIN
    SELECT * FROM categories ORDER BY Id;
END //
DELIMITER ;

-- 10. Stored Procedure để lấy category theo ID
DELIMITER //
CREATE PROCEDURE GetCategoryById(IN categoryId INT)
BEGIN
    SELECT * FROM categories WHERE Id = categoryId;
END //
DELIMITER ;

-- 11. Stored Procedure để thêm category mới
DELIMITER //
CREATE PROCEDURE AddCategory(
    IN p_categoryCode VARCHAR(50),
    IN p_categoryName VARCHAR(255),
    IN p_description TEXT,
    IN p_status VARCHAR(100)
)
BEGIN
    DECLARE EXIT HANDLER FOR 1062
    BEGIN
        SELECT -1 AS newId, 'Mã danh mục đã tồn tại' AS message;
    END;
    
    INSERT INTO categories (categoryCode, categoryName, description, status)
    VALUES (p_categoryCode, p_categoryName, p_description, p_status);
    
    SELECT LAST_INSERT_ID() AS newId, 'Thêm danh mục thành công' AS message;
END //
DELIMITER ;

-- 12. Stored Procedure để cập nhật category
DELIMITER //
CREATE PROCEDURE UpdateCategory(
    IN p_id INT,
    IN p_categoryCode VARCHAR(50),
    IN p_categoryName VARCHAR(255),
    IN p_description TEXT,
    IN p_status VARCHAR(100)
)
BEGIN
    UPDATE categories 
    SET categoryCode = p_categoryCode,
        categoryName = p_categoryName,
        description = p_description,
        status = p_status
    WHERE Id = p_id;
    
    SELECT ROW_COUNT() AS affectedRows;
END //
DELIMITER ;

-- 13. Stored Procedure để xóa category
DELIMITER //
CREATE PROCEDURE DeleteCategory(IN p_id INT)
BEGIN
    DELETE FROM categories WHERE Id = p_id;
    SELECT ROW_COUNT() AS affectedRows;
END //
DELIMITER ;

-- Hiển thị thông báo hoàn thành
SELECT 'All Stored Procedures have been created successfully!' AS message;
