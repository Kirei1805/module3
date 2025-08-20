-- Tạo database demo nếu chưa tồn tại
CREATE DATABASE IF NOT EXISTS demo;
USE demo;

-- Tạo bảng categories
CREATE TABLE IF NOT EXISTS categories (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    categoryCode VARCHAR(50) UNIQUE NOT NULL,
    categoryName VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(100) DEFAULT 'Active'
);

-- Tạo bảng products với foreign key đến categories
CREATE TABLE IF NOT EXISTS products (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    productCode VARCHAR(50) UNIQUE NOT NULL,
    productName VARCHAR(255) NOT NULL,
    productPrice DECIMAL(10,2) NOT NULL,
    productAmount INT NOT NULL,
    productDescription TEXT,
    productStatus VARCHAR(100) DEFAULT 'Còn hàng',
    categoryId INT,
    FOREIGN KEY (categoryId) REFERENCES categories(Id)
);

-- Tạo index cho productCode để tối ưu tìm kiếm
CREATE INDEX idx_product_code ON products(productCode);

-- Thêm dữ liệu mẫu cho categories
INSERT INTO categories (categoryCode, categoryName, description, status) VALUES
('CAT001', 'Điện tử', 'Các sản phẩm điện tử như laptop, điện thoại, tablet', 'Active'),
('CAT002', 'Đồ gia dụng', 'Các sản phẩm gia dụng như tủ lạnh, máy giặt', 'Active'),
('CAT003', 'Thời trang', 'Quần áo, giày dép, phụ kiện', 'Active');

-- Thêm dữ liệu mẫu cho products
INSERT INTO products (productCode, productName, productPrice, productAmount, productDescription, productStatus, categoryId) VALUES
('P001', 'Laptop Dell Inspiron 15', 15000000, 10, 'Laptop Dell Inspiron 15 inch, RAM 8GB, SSD 256GB', 'Còn hàng', 1),
('P002', 'iPhone 15 Pro', 25000000, 5, 'iPhone 15 Pro 128GB, màu Titan', 'Còn hàng', 1),
('P003', 'Samsung Galaxy S24', 20000000, 8, 'Samsung Galaxy S24 Ultra, RAM 12GB, 256GB', 'Còn hàng', 1),
('P004', 'MacBook Air M2', 30000000, 3, 'MacBook Air M2 13 inch, RAM 8GB, SSD 256GB', 'Sắp hết', 1),
('P005', 'iPad Pro 12.9', 28000000, 0, 'iPad Pro 12.9 inch, M2 chip, 128GB', 'Hết hàng', 1);

-- Hiển thị thông báo hoàn thành
SELECT 'Database setup completed successfully!' AS message;
