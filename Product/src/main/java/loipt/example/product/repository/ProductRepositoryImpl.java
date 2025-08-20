package loipt.example.product.repository;

import loipt.example.product.model.Product;
import loipt.example.product.dto.PageResultDTO;
import loipt.example.product.dto.ProductDTO;
import loipt.example.product.dto.ProductSearchDTO;
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

    // ========= Các method mới: phân trang, tìm kiếm nâng cao, và DTO =========

    @Override
    public List<Product> findAllWithPagination(int offset, int limit) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY Id LIMIT ? OFFSET ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, limit);
            stmt.setInt(2, offset);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                products.add(mapProduct(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public List<Product> searchProducts(String keyword, int categoryId, double minPrice, double maxPrice, int offset, int limit) {
        List<Product> products = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM products WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND productName LIKE ?");
            params.add("%" + keyword.trim() + "%");
        }
        if (categoryId > 0) {
            sql.append(" AND categoryId = ?");
            params.add(categoryId);
        }
        if (minPrice > 0) {
            sql.append(" AND productPrice >= ?");
            params.add(minPrice);
        }
        if (maxPrice < Double.MAX_VALUE) {
            sql.append(" AND productPrice <= ?");
            params.add(maxPrice);
        }
        sql.append(" ORDER BY Id LIMIT ? OFFSET ?");
        params.add(limit);
        params.add(offset);

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                products.add(mapProduct(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public int getTotalProducts() {
        String sql = "SELECT COUNT(*) AS total FROM products";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getInt("total");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getTotalProductsBySearch(String keyword, int categoryId, double minPrice, double maxPrice) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) AS total FROM products WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND productName LIKE ?");
            params.add("%" + keyword.trim() + "%");
        }
        if (categoryId > 0) {
            sql.append(" AND categoryId = ?");
            params.add(categoryId);
        }
        if (minPrice > 0) {
            sql.append(" AND productPrice >= ?");
            params.add(minPrice);
        }
        if (maxPrice < Double.MAX_VALUE) {
            sql.append(" AND productPrice <= ?");
            params.add(maxPrice);
        }
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("total");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<ProductDTO> findAllWithCategoryInfo() {
        List<ProductDTO> results = new ArrayList<>();
        String sql = "SELECT p.*, c.categoryCode, c.categoryName, c.description AS categoryDescription, c.status AS categoryStatus " +
                "FROM products p LEFT JOIN categories c ON p.categoryId = c.Id ORDER BY p.Id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                results.add(mapProductDTO(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    @Override
    public ProductDTO findByIdWithCategoryInfo(int id) {
        String sql = "SELECT p.*, c.categoryCode, c.categoryName, c.description AS categoryDescription, c.status AS categoryStatus " +
                "FROM products p LEFT JOIN categories c ON p.categoryId = c.Id WHERE p.Id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return mapProductDTO(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PageResultDTO<ProductDTO> findAllWithCategoryInfoPaged(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        List<ProductDTO> data = new ArrayList<>();
        String sql = "SELECT p.*, c.categoryCode, c.categoryName, c.description AS categoryDescription, c.status AS categoryStatus " +
                "FROM products p LEFT JOIN categories c ON p.categoryId = c.Id ORDER BY p.Id LIMIT ? OFFSET ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pageSize);
            stmt.setInt(2, offset);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) data.add(mapProductDTO(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int total = getTotalProducts();
        return new PageResultDTO<>(data, page, pageSize, total);
    }

    @Override
    public PageResultDTO<ProductDTO> searchProductsWithCategoryInfo(ProductSearchDTO searchDTO) {
        List<ProductDTO> data = searchProductsWithCategoryInfo(
                searchDTO.getKeyword(),
                searchDTO.getCategoryId(),
                searchDTO.getMinPrice(),
                searchDTO.getMaxPrice(),
                searchDTO.getOffset(),
                searchDTO.getLimit()
        );
        int total = getTotalProductsBySearchWithCategoryInfo(
                searchDTO.getKeyword(),
                searchDTO.getCategoryId(),
                searchDTO.getMinPrice(),
                searchDTO.getMaxPrice()
        );
        return new PageResultDTO<>(data, searchDTO.getPage(), searchDTO.getPageSize(), total);
    }

    @Override
    public List<ProductDTO> searchProductsWithCategoryInfo(String keyword, int categoryId, double minPrice, double maxPrice, int offset, int limit) {
        List<ProductDTO> results = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT p.*, c.categoryCode, c.categoryName, c.description AS categoryDescription, c.status AS categoryStatus " +
                "FROM products p LEFT JOIN categories c ON p.categoryId = c.Id WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND p.productName LIKE ?");
            params.add("%" + keyword.trim() + "%");
        }
        if (categoryId > 0) {
            sql.append(" AND p.categoryId = ?");
            params.add(categoryId);
        }
        if (minPrice > 0) {
            sql.append(" AND p.productPrice >= ?");
            params.add(minPrice);
        }
        if (maxPrice < Double.MAX_VALUE) {
            sql.append(" AND p.productPrice <= ?");
            params.add(maxPrice);
        }
        sql.append(" ORDER BY p.Id LIMIT ? OFFSET ?");
        params.add(limit);
        params.add(offset);

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) results.add(mapProductDTO(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    @Override
    public int getTotalProductsWithCategoryInfo() {
        return getTotalProducts();
    }

    @Override
    public int getTotalProductsBySearchWithCategoryInfo(String keyword, int categoryId, double minPrice, double maxPrice) {
        return getTotalProductsBySearch(keyword, categoryId, minPrice, maxPrice);
    }

    // ========= Helpers =========
    private Product mapProduct(ResultSet rs) throws SQLException {
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

    private ProductDTO mapProductDTO(ResultSet rs) throws SQLException {
        ProductDTO dto = new ProductDTO();
        dto.setId(rs.getInt("Id"));
        dto.setProductCode(rs.getString("productCode"));
        dto.setProductName(rs.getString("productName"));
        dto.setProductPrice(rs.getDouble("productPrice"));
        dto.setProductAmount(rs.getInt("productAmount"));
        dto.setProductDescription(rs.getString("productDescription"));
        dto.setProductStatus(rs.getString("productStatus"));
        dto.setCategoryId(rs.getInt("categoryId"));
        // category info (may be null)
        dto.setCategoryCode(getStringSafe(rs, "categoryCode"));
        dto.setCategoryName(getStringSafe(rs, "categoryName"));
        dto.setCategoryDescription(getStringSafe(rs, "categoryDescription"));
        dto.setCategoryStatus(getStringSafe(rs, "categoryStatus"));
        return dto;
    }

    private String getStringSafe(ResultSet rs, String column) {
        try {
            String value = rs.getString(column);
            return value;
        } catch (SQLException e) {
            return null;
        }
    }
}
