package loipt.example.product.service;

import loipt.example.product.model.Category;
import loipt.example.product.model.Product;
import loipt.example.product.repository.ProductRepository;
import loipt.example.product.repository.ProductRepositoryImpl;
import loipt.example.product.util.DatabaseConnection;

import java.sql.*;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository = new ProductRepositoryImpl();

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(int id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> searchProducts(String keyword) {
        return productRepository.searchByName(keyword);
    }

    @Override
    public void addProduct(Product product) throws Exception {
        // Sử dụng transaction trực tiếp
        addProductWithTransaction(product, null);
    }

    /**
     * Thêm sản phẩm với transaction - có thể thêm category mới
     */
    public void addProductWithTransaction(Product product, Category newCategory) throws Exception {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Bắt đầu transaction
            
            System.out.println("=== BẮT ĐẦU TRANSACTION THÊM SẢN PHẨM ===");
            
            // Bước 1: Thêm category mới (nếu có)
            if (newCategory != null && newCategory.getCategoryCode() != null) {
                System.out.println("1. Đang thêm category mới: " + newCategory.getCategoryName());
                addCategoryWithConnection(conn, newCategory);
                // Cập nhật categoryId cho product
                product.setCategoryId(getLastInsertedCategoryId(conn));
            }
            
            // Bước 2: Thêm sản phẩm
            System.out.println("2. Đang thêm sản phẩm: " + product.getProductName());
            addProductWithConnection(conn, product);
            
            // Nếu tất cả thành công, commit transaction
            conn.commit();
            System.out.println("=== COMMIT TRANSACTION THÀNH CÔNG ===");
            
        } catch (Exception e) {
            // Nếu có lỗi, rollback transaction
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("=== ROLLBACK TRANSACTION DO LỖI: " + e.getMessage() + " ===");
                } catch (SQLException rollbackEx) {
                    System.err.println("Lỗi khi rollback: " + rollbackEx.getMessage());
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Lỗi khi đóng connection: " + e.getMessage());
                }
            }
        }
    }

    @Override
    public void updateProduct(int id, Product product) throws Exception {
        // Sử dụng transaction trực tiếp
        updateProductWithTransaction(id, product, null);
    }

    /**
     * Cập nhật sản phẩm với transaction
     */
    public void updateProductWithTransaction(int productId, Product product, Category newCategory) throws Exception {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            System.out.println("=== BẮT ĐẦU TRANSACTION CẬP NHẬT SẢN PHẨM ===");
            
            // Bước 1: Cập nhật hoặc thêm category mới
            if (newCategory != null && newCategory.getCategoryCode() != null) {
                Category existingCategory = findCategoryByCode(conn, newCategory.getCategoryCode());
                if (existingCategory != null) {
                    System.out.println("1. Sử dụng category hiện có: " + existingCategory.getCategoryName());
                    product.setCategoryId(existingCategory.getId());
                } else {
                    System.out.println("1. Thêm category mới: " + newCategory.getCategoryName());
                    addCategoryWithConnection(conn, newCategory);
                    product.setCategoryId(getLastInsertedCategoryId(conn));
                }
            }
            
            // Bước 2: Cập nhật sản phẩm
            System.out.println("2. Cập nhật sản phẩm: " + product.getProductName());
            updateProductWithConnection(conn, productId, product);
            
            conn.commit();
            System.out.println("=== COMMIT TRANSACTION CẬP NHẬT THÀNH CÔNG ===");
            
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("=== ROLLBACK TRANSACTION DO LỖI: " + e.getMessage() + " ===");
                } catch (SQLException rollbackEx) {
                    System.err.println("Lỗi khi rollback: " + rollbackEx.getMessage());
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Lỗi khi đóng connection: " + e.getMessage());
                }
            }
        }
    }

    @Override
    public void deleteProduct(int id) throws Exception {
        // Sử dụng transaction trực tiếp
        deleteProductWithTransaction(id);
    }

    /**
     * Xóa sản phẩm với transaction
     */
    public void deleteProductWithTransaction(int productId) throws Exception {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            System.out.println("=== BẮT ĐẦU TRANSACTION XÓA SẢN PHẨM ===");
            
            // Bước 1: Lấy thông tin sản phẩm trước khi xóa
            Product product = getProductById(conn, productId);
            if (product == null) {
                throw new Exception("Không tìm thấy sản phẩm với ID: " + productId);
            }
            
            System.out.println("1. Tìm thấy sản phẩm: " + product.getProductName());
            
            // Bước 2: Xóa sản phẩm
            System.out.println("2. Đang xóa sản phẩm...");
            deleteProductWithConnection(conn, productId);
            
            // Bước 3: Kiểm tra xem category có còn sản phẩm nào không
            if (product.getCategoryId() > 0) {
                int remainingProducts = countProductsByCategory(conn, product.getCategoryId());
                System.out.println("3. Category " + product.getCategoryId() + " còn " + remainingProducts + " sản phẩm");
                
                // Nếu category không còn sản phẩm nào, có thể xóa category (tùy chọn)
                if (remainingProducts == 0) {
                    System.out.println("4. Category không còn sản phẩm, có thể xóa category");
                    // Uncomment dòng dưới nếu muốn tự động xóa category trống
                    // deleteCategoryWithConnection(conn, product.getCategoryId());
                }
            }
            
            conn.commit();
            System.out.println("=== COMMIT TRANSACTION XÓA THÀNH CÔNG ===");
            
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("=== ROLLBACK TRANSACTION DO LỖI: " + e.getMessage() + " ===");
                } catch (SQLException rollbackEx) {
                    System.err.println("Lỗi khi rollback: " + rollbackEx.getMessage());
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Lỗi khi đóng connection: " + e.getMessage());
                }
            }
        }
    }

    // Các method hỗ trợ cho transaction
    private void addCategoryWithConnection(Connection conn, Category category) throws SQLException {
        String sql = "INSERT INTO categories (categoryCode, categoryName, description, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category.getCategoryCode());
            stmt.setString(2, category.getCategoryName());
            stmt.setString(3, category.getDescription());
            stmt.setString(4, category.getStatus());
            stmt.executeUpdate();
            System.out.println("   ✓ Thêm category thành công");
        }
    }
    
    private void addProductWithConnection(Connection conn, Product product) throws SQLException {
        String sql = "INSERT INTO products (productCode, productName, productPrice, productAmount, productDescription, productStatus, categoryId) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getProductCode());
            stmt.setString(2, product.getProductName());
            stmt.setDouble(3, product.getProductPrice());
            stmt.setInt(4, product.getProductAmount());
            stmt.setString(5, product.getProductDescription());
            stmt.setString(6, product.getProductStatus());
            stmt.setInt(7, product.getCategoryId());
            stmt.executeUpdate();
            System.out.println("   ✓ Thêm sản phẩm thành công");
        }
    }
    
    private void updateProductWithConnection(Connection conn, int productId, Product product) throws SQLException {
        String sql = "UPDATE products SET productCode = ?, productName = ?, productPrice = ?, productAmount = ?, productDescription = ?, productStatus = ?, categoryId = ? WHERE Id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getProductCode());
            stmt.setString(2, product.getProductName());
            stmt.setDouble(3, product.getProductPrice());
            stmt.setInt(4, product.getProductAmount());
            stmt.setString(5, product.getProductDescription());
            stmt.setString(6, product.getProductStatus());
            stmt.setInt(7, product.getCategoryId());
            stmt.setInt(8, productId);
            stmt.executeUpdate();
            System.out.println("   ✓ Cập nhật sản phẩm thành công");
        }
    }
    
    private void deleteProductWithConnection(Connection conn, int productId) throws SQLException {
        String sql = "DELETE FROM products WHERE Id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("   ✓ Xóa sản phẩm thành công");
            } else {
                throw new SQLException("Không tìm thấy sản phẩm để xóa");
            }
        }
    }
    
    private int getLastInsertedCategoryId(Connection conn) throws SQLException {
        String sql = "SELECT LAST_INSERT_ID() as id";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("id");
            }
            throw new SQLException("Không thể lấy ID category vừa thêm");
        }
    }
    
    private Category findCategoryByCode(Connection conn, String categoryCode) throws SQLException {
        String sql = "SELECT * FROM categories WHERE categoryCode = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoryCode);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("Id"));
                category.setCategoryCode(rs.getString("categoryCode"));
                category.setCategoryName(rs.getString("categoryName"));
                category.setDescription(rs.getString("description"));
                category.setStatus(rs.getString("status"));
                return category;
            }
        }
        return null;
    }
    
    private Product getProductById(Connection conn, int productId) throws SQLException {
        String sql = "SELECT * FROM products WHERE Id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("Id"));
                product.setProductCode(rs.getString("productCode"));
                product.setProductName(rs.getString("productName"));
                product.setProductPrice(rs.getDouble("productPrice"));
                product.setProductAmount(rs.getInt("productAmount"));
                product.setProductDescription(rs.getString("productDescription"));
                product.setProductStatus(rs.getString("productStatus"));
                product.setCategoryId(rs.getInt("categoryId"));
                return product;
            }
        }
        return null;
    }
    
    private int countProductsByCategory(Connection conn, int categoryId) throws SQLException {
        String sql = "SELECT COUNT(*) as count FROM products WHERE categoryId = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
        }
        return 0;
    }
}

