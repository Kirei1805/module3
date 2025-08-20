# Ứng dụng Quản lý Sản phẩm với Transaction

## 📋 Mô tả
Ứng dụng web quản lý sản phẩm sử dụng JSP, Servlet, JDBC và MySQL với Stored Procedures. **Transaction được tích hợp trực tiếp vào ProductServiceImpl** để đảm bảo tính toàn vẹn dữ liệu trong các thao tác CRUD.

## 🏗️ Cấu trúc dự án
```
Product/
├── build.gradle
├── database_setup.sql
├── stored_procedures.sql
├── src/main/java/loipt/example/product/
│   ├── model/
│   │   ├── Product.java
│   │   ├── Category.java
│   │   └── ProductStatistics.java
│   ├── repository/
│   │   ├── ProductRepository.java
│   │   ├── ProductRepositoryImpl.java
│   │   ├── CategoryRepository.java
│   │   └── CategoryRepositoryImpl.java
│   ├── service/
│   │   ├── ProductService.java
│   │   ├── ProductServiceImpl.java
│   │   └── StatisticsService.java
│   ├── controller/
│   │   ├── ProductServlet.java
│   │   └── StatisticsServlet.java
│   └── util/
│       └── DatabaseConnection.java
└── src/main/webapp/product/
    ├── list.jsp
    ├── create.jsp
    ├── edit.jsp
    ├── view.jsp
    ├── search.jsp
    └── statistics.jsp
```

## 🚀 Cài đặt và chạy

### 1. Cài đặt database
```sql
-- Chạy script tạo database và bảng
source database_setup.sql

-- Chạy script tạo Stored Procedures
source stored_procedures.sql
```

### 2. Cấu hình database
Chỉnh sửa file `src/main/java/loipt/example/product/util/DatabaseConnection.java`:
```java
private static final String USERNAME = "root";
private static final String PASSWORD = "your_password";
```

### 3. Chạy ứng dụng
```bash
# Sử dụng Gradle
./gradlew appRun

# Hoặc build và deploy
./gradlew build
./gradlew appRun
```

## 🎯 Tính năng chính

### 1. Quản lý sản phẩm với Transaction tích hợp
- ✅ **Thêm sản phẩm**: Transaction tự động thêm category mới (nếu có)
- ✅ **Cập nhật sản phẩm**: Transaction đảm bảo cập nhật đồng bộ
- ✅ **Xóa sản phẩm**: Transaction kiểm tra và xử lý category
- ✅ **Tìm kiếm sản phẩm**: Hiển thị với thông tin category
- ✅ **Xem chi tiết sản phẩm**: Hiển thị đầy đủ thông tin

### 2. Quản lý danh mục tích hợp
- ✅ **Tạo category mới**: Khi thêm sản phẩm, có thể tạo category mới
- ✅ **Chọn category hiện có**: Dropdown chọn từ danh sách
- ✅ **Foreign key constraint**: Đảm bảo tính toàn vẹn dữ liệu

### 3. Thống kê
- ✅ Thống kê tổng số sản phẩm
- ✅ Thống kê tổng số lượng
- ✅ Giá trung bình
- ✅ Số sản phẩm còn/hết hàng
- ✅ Biểu đồ tỷ lệ tồn kho

## 🔄 Transaction tích hợp trong ProductServiceImpl

### 1. Thêm sản phẩm với Category mới
**Khi người dùng chọn "Tạo danh mục mới":**
```
=== BẮT ĐẦU TRANSACTION THÊM SẢN PHẨM ===
1. Đang thêm category mới: Điện tử cao cấp
   ✓ Thêm category thành công
2. Đang thêm sản phẩm: MacBook Pro M3
   ✓ Thêm sản phẩm thành công
=== COMMIT TRANSACTION THÀNH CÔNG ===
```

**Nếu có lỗi:**
```
=== BẮT ĐẦU TRANSACTION THÊM SẢN PHẨM ===
1. Đang thêm category mới: Điện tử cao cấp
   ✓ Thêm category thành công
2. Đang thêm sản phẩm: MacBook Pro M3
=== ROLLBACK TRANSACTION DO LỖI: Mã sản phẩm đã tồn tại ===
```

### 2. Cập nhật sản phẩm
```
=== BẮT ĐẦU TRANSACTION CẬP NHẬT SẢN PHẨM ===
1. Sử dụng category hiện có: Điện tử
2. Cập nhật sản phẩm: iPhone 15 Pro Max
   ✓ Cập nhật sản phẩm thành công
=== COMMIT TRANSACTION CẬP NHẬT THÀNH CÔNG ===
```

### 3. Xóa sản phẩm
```
=== BẮT ĐẦU TRANSACTION XÓA SẢN PHẨM ===
1. Tìm thấy sản phẩm: iPad Pro 12.9
2. Đang xóa sản phẩm...
   ✓ Xóa sản phẩm thành công
3. Category 1 còn 4 sản phẩm
=== COMMIT TRANSACTION XÓA THÀNH CÔNG ===
```

## 🗄️ Database Schema

### Bảng categories
```sql
CREATE TABLE categories (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    categoryCode VARCHAR(50) UNIQUE NOT NULL,
    categoryName VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(100) DEFAULT 'Active'
);
```

### Bảng products
```sql
CREATE TABLE products (
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
```

## 🔧 Stored Procedures

### Product Procedures
- `GetAllProducts()` - Lấy tất cả sản phẩm với thông tin category
- `GetProductById(IN productId INT)` - Lấy sản phẩm theo ID
- `SearchProductsByName(IN searchName VARCHAR(255))` - Tìm kiếm sản phẩm
- `AddProduct(...)` - Thêm sản phẩm mới
- `UpdateProduct(...)` - Cập nhật sản phẩm
- `DeleteProduct(IN p_id INT)` - Xóa sản phẩm
- `GetProductStatistics()` - Lấy thống kê sản phẩm

### Category Procedures
- `GetAllCategories()` - Lấy tất cả danh mục
- `GetCategoryById(IN categoryId INT)` - Lấy danh mục theo ID
- `AddCategory(...)` - Thêm danh mục mới
- `UpdateCategory(...)` - Cập nhật danh mục
- `DeleteCategory(IN p_id INT)` - Xóa danh mục

## 🎨 Giao diện
- Giao diện basic, không sử dụng framework CSS
- Form thêm sản phẩm có tùy chọn tạo category mới
- Responsive design
- Thân thiện với người dùng
- Hiển thị thông báo lỗi rõ ràng

## 🔍 Cách test Transaction

### Test thêm sản phẩm với category mới:
1. Truy cập: `http://localhost:8080/products?action=create`
2. Chọn "Tạo danh mục mới"
3. Nhập thông tin category mới
4. Nhập thông tin sản phẩm
5. Submit form
6. Quan sát console log để thấy transaction

### Test lỗi transaction:
1. Thử thêm sản phẩm với mã đã tồn tại
2. Quan sát console log để thấy rollback
3. Kiểm tra database - không có dữ liệu nào được thêm

## 📝 Lưu ý
- Đảm bảo MySQL đang chạy
- Kiểm tra thông tin kết nối database
- Chạy scripts database theo thứ tự
- Quan sát console log để theo dõi transaction
- Transaction đảm bảo tính toàn vẹn dữ liệu
- **Transaction được tích hợp trực tiếp vào ProductServiceImpl**

## 🎓 Mục đích học tập
- Luyện tập JSP, Servlet, JDBC
- Sử dụng Stored Procedures
- **Tích hợp transaction trực tiếp vào Service layer**
- Hiểu về commit, rollback trong thực tế
- Xử lý lỗi trong database
- Thiết kế giao diện web cơ bản
- Quản lý quan hệ giữa các bảng
- **Kiến trúc Service layer với transaction**
