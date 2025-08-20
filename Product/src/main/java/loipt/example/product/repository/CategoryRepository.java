package loipt.example.product.repository;

import loipt.example.product.model.Category;

import java.util.List;

public interface CategoryRepository {
    List<Category> findAll();
    Category findById(int id);
    void save(Category category) throws Exception;
    void update(int id, Category category);
    void delete(int id);
}
