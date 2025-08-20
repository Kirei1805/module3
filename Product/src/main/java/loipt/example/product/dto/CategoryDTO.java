package loipt.example.product.dto;

public class CategoryDTO {
    private int id;
    private String categoryCode;
    private String categoryName;
    private String description;
    private String status;
    private int productCount; // Số lượng sản phẩm trong category

    public CategoryDTO() {}

    public CategoryDTO(int id, String categoryCode, String categoryName, String description, String status) {
        this.id = id;
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.description = description;
        this.status = status;
    }

    public CategoryDTO(int id, String categoryCode, String categoryName, String description, String status, int productCount) {
        this.id = id;
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.description = description;
        this.status = status;
        this.productCount = productCount;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
                "id=" + id +
                ", categoryCode='" + categoryCode + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", productCount=" + productCount +
                '}';
    }
}
