CREATE DATABASE IF NOT EXISTS plant_shop;
USE plant_shop;

-- 1. Bảng người dùng (admin và khách hàng)
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    role ENUM('customer', 'admin') DEFAULT 'customer',
    full_name VARCHAR(100),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 2. Bảng địa chỉ giao hàng
CREATE TABLE addresses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    recipient_name VARCHAR(100),
    phone VARCHAR(20),
    address_line TEXT,
    is_default BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- 3. Bảng loại cây
CREATE TABLE categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- 4. Bảng sản phẩm cây cảnh
CREATE TABLE plants (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    image_url TEXT,
    description TEXT,
    stock INT DEFAULT 0,
    rating_avg FLOAT DEFAULT 0,
    category_id INT,
    is_active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- 5. Bảng giỏ hàng
CREATE TABLE cart_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    plant_id INT,
    quantity INT DEFAULT 1,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (plant_id) REFERENCES plants(id),
    UNIQUE(user_id, plant_id)
);

-- 6. Bảng đơn hàng
CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    address_id INT,
    total_amount DECIMAL(10,2),
    status ENUM('pending', 'processing', 'shipped', 'cancelled') DEFAULT 'pending',
    order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (address_id) REFERENCES addresses(id)
);

-- 7. Bảng chi tiết đơn hàng
CREATE TABLE order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    plant_id INT,
    quantity INT,
    unit_price DECIMAL(10,2),
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (plant_id) REFERENCES plants(id)
);

-- 8. Bảng đánh giá sản phẩm
CREATE TABLE reviews (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    plant_id INT,
    rating INT CHECK (rating BETWEEN 1 AND 5),
    comment TEXT,
    review_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (plant_id) REFERENCES plants(id)
);

-- 9. Bảng wishlist (sản phẩm yêu thích)
CREATE TABLE wishlist (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    plant_id INT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (plant_id) REFERENCES plants(id),
    UNIQUE(user_id, plant_id)
);

-- 10. Mã giảm giá
CREATE TABLE vouchers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    discount_percent INT CHECK (discount_percent BETWEEN 0 AND 100),
    start_date DATE,
    end_date DATE,
    is_active BOOLEAN DEFAULT TRUE
);

-- 11. Vouchers đã dùng
CREATE TABLE used_vouchers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    voucher_id INT,
    used_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (voucher_id) REFERENCES vouchers(id),
    UNIQUE(user_id, voucher_id)
);

-- 12. Nhật ký hoạt động quản trị (tuỳ chọn)
CREATE TABLE admin_logs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    admin_id INT,
    action TEXT,
    log_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (admin_id) REFERENCES users(id)
);
