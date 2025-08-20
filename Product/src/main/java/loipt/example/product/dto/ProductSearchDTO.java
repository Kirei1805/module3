package loipt.example.product.dto;

public class ProductSearchDTO {
    private String keyword;
    private int categoryId;
    private double minPrice;
    private double maxPrice;
    private String status;
    private int page;
    private int pageSize;
    private String sortBy;
    private String sortOrder;

    public ProductSearchDTO() {
        this.page = 1;
        this.pageSize = 10;
        this.sortBy = "id";
        this.sortOrder = "ASC";
        this.categoryId = 0; // 0 = tất cả categories
        this.minPrice = 0;
        this.maxPrice = Double.MAX_VALUE;
    }

    public ProductSearchDTO(String keyword, int categoryId, double minPrice, double maxPrice, 
                           String status, int page, int pageSize, String sortBy, String sortOrder) {
        this.keyword = keyword;
        this.categoryId = categoryId;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.status = status;
        this.page = page;
        this.pageSize = pageSize;
        this.sortBy = sortBy;
        this.sortOrder = sortOrder;
    }

    // Getters and Setters
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    // Helper methods
    public int getOffset() {
        return (page - 1) * pageSize;
    }

    public int getLimit() {
        return pageSize;
    }

    @Override
    public String toString() {
        return "ProductSearchDTO{" +
                "keyword='" + keyword + '\'' +
                ", categoryId=" + categoryId +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", status='" + status + '\'' +
                ", page=" + page +
                ", pageSize=" + pageSize +
                ", sortBy='" + sortBy + '\'' +
                ", sortOrder='" + sortOrder + '\'' +
                '}';
    }
}
