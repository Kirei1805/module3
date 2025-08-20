package loipt.example.product.repository;

import loipt.example.product.model.Product;
import loipt.example.product.model.ProductStatistics;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll();
    Product findById(int id);
    void save(Product product) throws Exception;
    void update(int id, Product product);
    void delete(int id);
    List<Product> searchByName(String name);
    boolean checkProductExists(int id);
    ProductStatistics getProductStatistics();
}
