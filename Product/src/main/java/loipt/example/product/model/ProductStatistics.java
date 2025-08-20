package loipt.example.product.model;

public class ProductStatistics {
    private int totalProducts;
    private int totalQuantity;
    private double averagePrice;
    private int inStock;
    private int outOfStock;

    public ProductStatistics() {}

    public ProductStatistics(int totalProducts, int totalQuantity, double averagePrice, int inStock, int outOfStock) {
        this.totalProducts = totalProducts;
        this.totalQuantity = totalQuantity;
        this.averagePrice = averagePrice;
        this.inStock = inStock;
        this.outOfStock = outOfStock;
    }

    // Getters and Setters
    public int getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(int totalProducts) {
        this.totalProducts = totalProducts;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public int getOutOfStock() {
        return outOfStock;
    }

    public void setOutOfStock(int outOfStock) {
        this.outOfStock = outOfStock;
    }

    @Override
    public String toString() {
        return "ProductStatistics{" +
                "totalProducts=" + totalProducts +
                ", totalQuantity=" + totalQuantity +
                ", averagePrice=" + averagePrice +
                ", inStock=" + inStock +
                ", outOfStock=" + outOfStock +
                '}';
    }
}
