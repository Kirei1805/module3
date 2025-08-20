package loipt.example.product.repository;

import loipt.example.product.model.Product;
import loipt.example.product.model.ProductStatistics;
import loipt.example.product.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String callSql = "{CALL GetAllProducts()}";
        
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(callSql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("Id"));
                product.setProductCode(rs.getString("productCode"));
                product.setProductName(rs.getString("productName"));
                product.setProductPrice(rs.getDouble("productPrice"));
                product.setProductAmount(rs.getInt("productAmount"));
                product.setProductDescription(rs.getString("productDescription"));
                product.setProductStatus(rs.getString("productStatus"));
                product.setCategoryId(rs.getInt("categoryId"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public Product findById(int id) {
        String callSql = "{CALL GetProductById(?)}";
        
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(callSql)) {
            
            stmt.setInt(1, id);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(Product product) throws Exception {
        String callSql = "{CALL AddProduct(?, ?, ?, ?, ?, ?, ?)}";
        
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(callSql)) {
            
            stmt.setString(1, product.getProductCode());
            stmt.setString(2, product.getProductName());
            stmt.setDouble(3, product.getProductPrice());
            stmt.setInt(4, product.getProductAmount());
            stmt.setString(5, product.getProductDescription());
            stmt.setString(6, product.getProductStatus());
            stmt.setInt(7, product.getCategoryId());
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                int newId = rs.getInt("newId");
                String message = rs.getString("message");
                
                if (newId == -1) {
                    throw new Exception("Mã sản phẩm '" + product.getProductCode() + "' đã tồn tại trong hệ thống!");
                }
            }
        } catch (SQLException e) {
            throw new Exception("Lỗi khi thêm sản phẩm: " + e.getMessage());
        }
    }

    @Override
    public void update(int id, Product product) {
        String callSql = "{CALL UpdateProduct(?, ?, ?, ?, ?, ?, ?, ?)}";
        
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(callSql)) {
            
            stmt.setInt(1, id);
            stmt.setString(2, product.getProductCode());
            stmt.setString(3, product.getProductName());
            stmt.setDouble(4, product.getProductPrice());
            stmt.setInt(5, product.getProductAmount());
            stmt.setString(6, product.getProductDescription());
            stmt.setString(7, product.getProductStatus());
            stmt.setInt(8, product.getCategoryId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String callSql = "{CALL DeleteProduct(?)}";
        
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(callSql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> searchByName(String name) {
        List<Product> products = new ArrayList<>();
        String callSql = "{CALL SearchProductsByName(?)}";
        
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(callSql)) {
            
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("Id"));
                product.setProductCode(rs.getString("productCode"));
                product.setProductName(rs.getString("productName"));
                product.setProductPrice(rs.getDouble("productPrice"));
                product.setProductAmount(rs.getInt("productAmount"));
                product.setProductDescription(rs.getString("productDescription"));
                product.setProductStatus(rs.getString("productStatus"));
                product.setCategoryId(rs.getInt("categoryId"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public boolean checkProductExists(int id) {
        String callSql = "{CALL CheckProductExists(?)}";
        
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(callSql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("exists") > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ProductStatistics getProductStatistics() {
        String callSql = "{CALL GetProductStatistics()}";
        
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(callSql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                ProductStatistics stats = new ProductStatistics();
                stats.setTotalProducts(rs.getInt("totalProducts"));
                stats.setTotalQuantity(rs.getInt("totalQuantity"));
                stats.setAveragePrice(rs.getDouble("averagePrice"));
                stats.setInStock(rs.getInt("inStock"));
                stats.setOutOfStock(rs.getInt("outOfStock"));
                return stats;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ProductStatistics();
    }
}
