package loipt.example.product.service;

import loipt.example.product.model.Product;
import loipt.example.product.dto.PageResultDTO;
import loipt.example.product.dto.ProductDTO;
import loipt.example.product.dto.ProductSearchDTO;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(int id);
    void addProduct(Product product) throws Exception;
    void updateProduct(int id, Product product) throws Exception;
    void deleteProduct(int id) throws Exception;
    List<Product> searchProducts(String name);
    boolean checkProductExists(int id);

    // Các phương thức dùng DTO
    List<ProductDTO> getAllProductsWithCategoryInfo();
    ProductDTO getProductWithCategoryInfoById(int id);
    PageResultDTO<ProductDTO> getProductsPaged(int page, int pageSize);
    PageResultDTO<ProductDTO> searchProducts(ProductSearchDTO searchDTO);
}
