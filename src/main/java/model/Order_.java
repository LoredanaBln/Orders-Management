package model;

public class Order_ {
    private Integer order_Id;
    private Integer clientId;
    private Integer productId;
    private Integer productQuantity;

    public Integer getOrder_Id() {
        return order_Id;
    }

    public void setOrder_Id(Integer orderId) {
        this.order_Id = orderId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }
}
