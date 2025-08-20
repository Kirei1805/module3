package loipt.example.product.service;

import loipt.example.product.model.Product;
import loipt.example.product.model.ProductStatistics;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(int id);
    void addProduct(Product product) throws Exception;
    void updateProduct(int id, Product product);
    void deleteProduct(int id);
    List<Product> searchProducts(String name);
    boolean checkProductExists(int id);
    ProductStatistics getProductStatistics();
}
