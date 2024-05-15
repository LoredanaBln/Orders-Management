package model;

/**
 * Model class for Product entities.
 */
public class Product {
    private Integer productId;
    private String name;
    private Integer quantity;
    private Float price;

    /**
     * Retrieves the product ID.
     * @return The product ID.
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * Sets the product ID.
     * @param productId The product ID to set.
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * Retrieves the name of the product.
     * @return The name of the product.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the product.
     * @param name The name of the product to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the quantity of the product.
     * @return The quantity of the product.
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the product.
     * @param quantity The quantity of the product to set.
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * Retrieves the price of the product.
     * @return The price of the product.
     */
    public Float getPrice() {
        return price;
    }

    /**
     * Sets the price of the product.
     * @param price The price of the product to set.
     */
    public void setPrice(Float price) {
        this.price = price;
    }

    /**
     * Returns a string representation of the product.
     * @return The string representation of the product.
     */
    @Override
    public String toString() {
        return name + " Quantity=" + quantity + " Price=" + price;
    }
}
