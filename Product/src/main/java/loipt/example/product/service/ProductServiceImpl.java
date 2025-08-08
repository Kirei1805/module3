package loipt.example.product.service;

import loipt.example.product.model.Product;
import loipt.example.product.repository.ProductRepository;
import loipt.example.product.repository.ProductRepositoryImpl;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    private ProductRepository repository = new ProductRepositoryImpl();

    @Override
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    @Override
    public Product getProductById(int id) {
        return repository.findById(id);
    }

    @Override
    public void addProduct(Product product) {
        repository.save(product);
    }

    @Override
    public void updateProduct(int id, Product product) {
        repository.update(id, product);
    }

    @Override
    public void deleteProduct(int id) {
        repository.delete(id);
    }

    @Override
    public List<Product> searchProducts(String name) {
        return repository.searchByName(name);
    }
}

