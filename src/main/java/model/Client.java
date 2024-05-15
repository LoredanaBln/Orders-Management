package model;

/**
 * Model class for Client entities.
 */
public class Client {
    private Integer clientId;
    private String name;
    private String phoneNumber;
    private String email;

    /**
     * Retrieves the client ID.
     * @return The client ID.
     */
    public Integer getClientId() {
        return clientId;
    }

    /**
     * Sets the client ID.
     * @param id The client ID to set.
     */
    public void setClientId(Integer id) {
        this.clientId = id;
    }

    /**
     * Retrieves the client's name.
     * @return The client's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the client's name.
     * @param name The client's name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the client's phone number.
     * @return The client's phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the client's phone number.
     * @param phoneNumber The client's phone number to set.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Retrieves the client's email address.
     * @return The client's email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the client's email address.
     * @param email The client's email address to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the client's name as a string representation of the object.
     * @return The client's name.
     */
    @Override
    public String toString() {
        return name;
    }
}
