package loipt.example.product.repository;

import loipt.example.product.model.Product;
import loipt.example.product.dto.ProductDTO;
import loipt.example.product.dto.ProductSearchDTO;
import loipt.example.product.dto.PageResultDTO;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll();
    Product findById(int id);
    void save(Product product) throws Exception;
    void update(int id, Product product);
    void delete(int id);
    List<Product> searchByName(String name);
    boolean checkProductExists(int id);
    
    // Thêm các method mới cho phân trang và tìm kiếm nâng cao
    List<Product> findAllWithPagination(int offset, int limit);
    List<Product> searchProducts(String keyword, int categoryId, double minPrice, double maxPrice, int offset, int limit);
    int getTotalProducts();
    int getTotalProductsBySearch(String keyword, int categoryId, double minPrice, double maxPrice);
    
    // Các method sử dụng DTO
    List<ProductDTO> findAllWithCategoryInfo();
    ProductDTO findByIdWithCategoryInfo(int id);
    PageResultDTO<ProductDTO> findAllWithCategoryInfoPaged(int page, int pageSize);
    PageResultDTO<ProductDTO> searchProductsWithCategoryInfo(ProductSearchDTO searchDTO);
    List<ProductDTO> searchProductsWithCategoryInfo(String keyword, int categoryId, double minPrice, double maxPrice, int offset, int limit);
    int getTotalProductsWithCategoryInfo();
    int getTotalProductsBySearchWithCategoryInfo(String keyword, int categoryId, double minPrice, double maxPrice);
}
