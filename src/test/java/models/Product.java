package models;

public class Product {
    private String productName;
    private int productAmount;

    private String productPrice;

    public Product() {
    }

    public Product(String productName, int productAmount, String productPrice) {
        this.productName = productName;
        this.productAmount = productAmount;
        this.productPrice = productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(int productAmount) { this.productAmount = productAmount; }

    public String getProductPrice() { return productPrice; }

    public void setProductPrice(String productPrice) { this.productPrice = productPrice; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;

        return productAmount == product.productAmount
                && productPrice.equals(product.productPrice)
                && productName.equals(product.productName);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(productName, productAmount, productPrice);
    }
}
