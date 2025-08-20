package loipt.example.product.repository;

import loipt.example.product.model.Category;
import loipt.example.product.dto.CategoryDTO;
import loipt.example.product.dto.PageResultDTO;
import loipt.example.product.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepositoryImpl implements CategoryRepository {

    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        String callSql = "{CALL GetAllCategories()}";
        
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(callSql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("Id"));
                category.setCategoryCode(rs.getString("categoryCode"));
                category.setCategoryName(rs.getString("categoryName"));
                category.setDescription(rs.getString("description"));
                category.setStatus(rs.getString("status"));
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public Category findById(int id) {
        String callSql = "{CALL GetCategoryById(?)}";
        
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(callSql)) {
            
            stmt.setInt(1, id);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(Category category) throws Exception {
        String callSql = "{CALL AddCategory(?, ?, ?, ?)}";
        
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(callSql)) {
            
            stmt.setString(1, category.getCategoryCode());
            stmt.setString(2, category.getCategoryName());
            stmt.setString(3, category.getDescription());
            stmt.setString(4, category.getStatus());
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                int newId = rs.getInt("newId");
                String message = rs.getString("message");
                
                if (newId == -1) {
                    throw new Exception("Mã danh mục '" + category.getCategoryCode() + "' đã tồn tại trong hệ thống!");
                }
            }
        } catch (SQLException e) {
            throw new Exception("Lỗi khi thêm danh mục: " + e.getMessage());
        }
    }

    @Override
    public void update(int id, Category category) {
        String callSql = "{CALL UpdateCategory(?, ?, ?, ?, ?)}";
        
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(callSql)) {
            
            stmt.setInt(1, id);
            stmt.setString(2, category.getCategoryCode());
            stmt.setString(3, category.getCategoryName());
            stmt.setString(4, category.getDescription());
            stmt.setString(5, category.getStatus());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String callSql = "{CALL DeleteCategory(?)}";
        
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(callSql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ========= Các method mới: phân trang, tìm kiếm, DTO =========

    @Override
    public List<Category> findAllWithPagination(int offset, int limit) {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories ORDER BY Id LIMIT ? OFFSET ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, limit);
            stmt.setInt(2, offset);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Category c = new Category();
                c.setId(rs.getInt("Id"));
                c.setCategoryCode(rs.getString("categoryCode"));
                c.setCategoryName(rs.getString("categoryName"));
                c.setDescription(rs.getString("description"));
                c.setStatus(rs.getString("status"));
                categories.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public List<Category> searchByName(String keyword, int offset, int limit) {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories WHERE categoryName LIKE ? ORDER BY Id LIMIT ? OFFSET ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            stmt.setInt(2, limit);
            stmt.setInt(3, offset);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Category c = new Category();
                c.setId(rs.getInt("Id"));
                c.setCategoryCode(rs.getString("categoryCode"));
                c.setCategoryName(rs.getString("categoryName"));
                c.setDescription(rs.getString("description"));
                c.setStatus(rs.getString("status"));
                categories.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public int getTotalCategories() {
        String sql = "SELECT COUNT(*) AS total FROM categories";
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
    public int getTotalCategoriesBySearch(String keyword) {
        String sql = "SELECT COUNT(*) AS total FROM categories WHERE categoryName LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("total");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<CategoryDTO> findAllWithProductCount() {
        List<CategoryDTO> results = new ArrayList<>();
        String sql = "SELECT c.*, COUNT(p.Id) AS productCount FROM categories c " +
                "LEFT JOIN products p ON p.categoryId = c.Id GROUP BY c.Id ORDER BY c.Id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) results.add(mapCategoryDTO(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    @Override
    public CategoryDTO findByIdWithProductCount(int id) {
        String sql = "SELECT c.*, COUNT(p.Id) AS productCount FROM categories c " +
                "LEFT JOIN products p ON p.categoryId = c.Id WHERE c.Id = ? GROUP BY c.Id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return mapCategoryDTO(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PageResultDTO<CategoryDTO> findAllWithProductCountPaged(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        List<CategoryDTO> data = new ArrayList<>();
        String sql = "SELECT c.*, COUNT(p.Id) AS productCount FROM categories c " +
                "LEFT JOIN products p ON p.categoryId = c.Id GROUP BY c.Id ORDER BY c.Id LIMIT ? OFFSET ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pageSize);
            stmt.setInt(2, offset);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) data.add(mapCategoryDTO(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int total = getTotalCategories();
        return new PageResultDTO<>(data, page, pageSize, total);
    }

    @Override
    public PageResultDTO<CategoryDTO> searchCategoriesWithProductCount(String keyword, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        List<CategoryDTO> data = new ArrayList<>();
        String sql = "SELECT c.*, COUNT(p.Id) AS productCount FROM categories c " +
                "LEFT JOIN products p ON p.categoryId = c.Id WHERE c.categoryName LIKE ? GROUP BY c.Id ORDER BY c.Id LIMIT ? OFFSET ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            stmt.setInt(2, pageSize);
            stmt.setInt(3, offset);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) data.add(mapCategoryDTO(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int total = getTotalCategoriesBySearch(keyword);
        return new PageResultDTO<>(data, page, pageSize, total);
    }

    @Override
    public int getTotalCategoriesWithProductCount() {
        return getTotalCategories();
    }

    @Override
    public int getTotalCategoriesBySearchWithProductCount(String keyword) {
        return getTotalCategoriesBySearch(keyword);
    }

    private CategoryDTO mapCategoryDTO(ResultSet rs) throws SQLException {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(rs.getInt("Id"));
        dto.setCategoryCode(rs.getString("categoryCode"));
        dto.setCategoryName(rs.getString("categoryName"));
        dto.setDescription(rs.getString("description"));
        dto.setStatus(rs.getString("status"));
        try { dto.setProductCount(rs.getInt("productCount")); } catch (SQLException ignored) {}
        return dto;
    }
}
