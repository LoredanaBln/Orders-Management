package model;

/**
 * Model class for Order entities.
 */
public class Order_ {
    private Integer order_Id;
    private Integer clientId;
    private Integer productId;
    private Integer productQuantity;

    /**
     * Retrieves the order ID.
     * @return The order ID.
     */
    public Integer getOrder_Id() {
        return order_Id;
    }

    /**
     * Sets the order ID.
     * @param orderId The order ID to set.
     */
    public void setOrder_Id(Integer orderId) {
        this.order_Id = orderId;
    }

    /**
     * Retrieves the client ID associated with the order.
     * @return The client ID.
     */
    public Integer getClientId() {
        return clientId;
    }

    /**
     * Sets the client ID associated with the order.
     * @param clientId The client ID to set.
     */
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    /**
     * Retrieves the product ID associated with the order.
     * @return The product ID.
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * Sets the product ID associated with the order.
     * @param productId The product ID to set.
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * Retrieves the quantity of the product in the order.
     * @return The product quantity.
     */
    public Integer getProductQuantity() {
        return productQuantity;
    }

    /**
     * Sets the quantity of the product in the order.
     * @param productQuantity The product quantity to set.
     */
    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }
}
