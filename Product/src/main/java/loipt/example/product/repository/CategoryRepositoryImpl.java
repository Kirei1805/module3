package loipt.example.product.repository;

import loipt.example.product.model.Category;
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
}
