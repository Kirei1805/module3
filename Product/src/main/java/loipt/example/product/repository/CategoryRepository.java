package loipt.example.product.repository;

import loipt.example.product.model.Category;
import loipt.example.product.dto.CategoryDTO;
import loipt.example.product.dto.PageResultDTO;

import java.util.List;

public interface CategoryRepository {
    List<Category> findAll();
    Category findById(int id);
    void save(Category category) throws Exception;
    void update(int id, Category category);
    void delete(int id);
    
    // Thêm các method mới cho phân trang và tìm kiếm
    List<Category> findAllWithPagination(int offset, int limit);
    List<Category> searchByName(String keyword, int offset, int limit);
    int getTotalCategories();
    int getTotalCategoriesBySearch(String keyword);
    
    // Các method sử dụng DTO
    List<CategoryDTO> findAllWithProductCount();
    CategoryDTO findByIdWithProductCount(int id);
    PageResultDTO<CategoryDTO> findAllWithProductCountPaged(int page, int pageSize);
    PageResultDTO<CategoryDTO> searchCategoriesWithProductCount(String keyword, int page, int pageSize);
    int getTotalCategoriesWithProductCount();
    int getTotalCategoriesBySearchWithProductCount(String keyword);
}
