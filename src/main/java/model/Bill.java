package model;

import java.util.Objects;

/**
 * Represents a bill for an order placed by a client.
 * <p>
 * This immutable class is used to create instances of Bill for every order placed. It encapsulates information
 * such as the order ID, client details, product details, and quantity.
 * </p>
 *
 * @param orderId The ID of the order.
 * @param clientName The name of the client in the order.
 * @param clientEmail The email address of the client.
 * @param clientPhoneNumber The phone number of the client.
 * @param productName The name of the product in the order.
 * @param quantity The quantity of the product ordered.
 */
public record Bill(Integer orderId, String clientName, String clientEmail, String clientPhoneNumber, String productName, Integer quantity) {
    /**
     * Constructs a Bill instance with the specified attributes.
     * @param orderId The ID of the order.
     * @param clientName The name of the client who placed the order.
     * @param clientEmail The email address of the client.
     * @param clientPhoneNumber The phone number of the client.
     * @param productName The name of the product in the order.
     * @param quantity The quantity of the product ordered.
     * @throws NullPointerException if any of the parameters are null.
     */
    public Bill {
        Objects.requireNonNull(orderId);
        Objects.requireNonNull(clientName);
        Objects.requireNonNull(clientEmail);
        Objects.requireNonNull(clientPhoneNumber);
        Objects.requireNonNull(productName);
        Objects.requireNonNull(quantity);
    }

    /**
     * Retrieves the order ID.
     * @return The order ID.
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * Retrieves the client name.
     * @return The client name.
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * Retrieves the client's email address.
     * @return The client's email address.
     */
    public String getClientEmail() {
        return clientEmail;
    }

    /**
     * Retrieves the client's phone number.
     * @return The client's phone number.
     */
    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    /**
     * Retrieves the product name.
     * @return The product name.
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Retrieves the quantity of the product ordered.
     * @return The quantity of the product ordered.
     */
    public Integer getQuantity() {
        return quantity;
    }
}
