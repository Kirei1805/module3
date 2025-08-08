package loipt.example.product.repository;

import loipt.example.product.model.Product;
import java.util.*;

public class ProductRepositoryImpl implements ProductRepository {
    private static Map<Integer, Product> products = new HashMap<>();

    static {
        products.put(1, new Product(1, "Laptop Dell", 1500, "Laptop văn phòng", "Dell"));
        products.put(2, new Product(2, "iPhone 14", 1200, "Điện thoại Apple", "Apple"));
        products.put(3, new Product(3, "Samsung TV", 800, "Smart TV 55 inch", "Samsung"));
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    @Override
    public Product findById(int id) {
        return products.get(id);
    }

    @Override
    public void save(Product product) {
        products.put(product.getId(), product);
    }

    @Override
    public void update(int id, Product product) {
        products.put(id, product);
    }

    @Override
    public void delete(int id) {
        products.remove(id);
    }

    @Override
    public List<Product> searchByName(String name) {
        List<Product> result = new ArrayList<>();
        for (Product p : products.values()) {
            if (p.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(p);
            }
        }
        return result;
    }
}
