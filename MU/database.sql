-- Tạo database MU nếu chưa tồn tại
CREATE DATABASE IF NOT EXISTS MU;
USE MU;

-- Tạo bảng users
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    country VARCHAR(100) NOT NULL
);

-- Thêm dữ liệu mẫu
INSERT INTO users (name, email, country) VALUES
('Nguyễn Văn An', 'an.nguyen@email.com', 'Việt Nam'),
('Trần Thị Bình', 'binh.tran@email.com', 'Việt Nam'),
('Lê Văn Cường', 'cuong.le@email.com', 'Việt Nam'),
('Phạm Thị Dung', 'dung.pham@email.com', 'Việt Nam'),
('Hoàng Văn Em', 'em.hoang@email.com', 'Việt Nam'),
('John Smith', 'john.smith@email.com', 'United States'),
('Mary Johnson', 'mary.johnson@email.com', 'United States'),
('David Brown', 'david.brown@email.com', 'United Kingdom'),
('Sarah Wilson', 'sarah.wilson@email.com', 'Canada'),
('Michael Davis', 'michael.davis@email.com', 'Australia'),
('Li Wei', 'li.wei@email.com', 'China'),
('Yuki Tanaka', 'yuki.tanaka@email.com', 'Japan'),
('Kim Min-seok', 'kim.minseok@email.com', 'South Korea'),
('Raj Patel', 'raj.patel@email.com', 'India'),
('Maria Garcia', 'maria.garcia@email.com', 'Spain');

-- Tạo index để tối ưu hiệu suất tìm kiếm và sắp xếp
CREATE INDEX idx_users_name ON users(name);
CREATE INDEX idx_users_country ON users(country);
CREATE INDEX idx_users_email ON users(email);
