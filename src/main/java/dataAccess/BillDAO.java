package dataAccess;

import databaseConnection.ConnectionClass;
import model.Bill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class provides DAO functionality for the immutable class Bill.
 * It includes methods to insert, find by ID, and find all Bill records in the database.
 * Queries are manually constructed based on the fields in the Bill record.
 */
public class BillDAO {

    /**
     * Inserts a Bill record into the database.
     * @param bill The Bill object to be inserted.
     */
    public void insert(Bill bill) {
        String sql = "INSERT INTO Log (order_id, clientName, clientEmail, clientPhoneNumber, productName, quantity) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionClass.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, bill.orderId());
            statement.setString(2, bill.clientName());
            statement.setString(3, bill.clientEmail());
            statement.setString(4, bill.clientPhoneNumber());
            statement.setString(5, bill.productName());
            statement.setInt(6, bill.quantity());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a Bill record from the database by its ID.
     * @param id The ID of the Bill record to retrieve.
     * @return An Optional containing the Bill object if found, or empty if not found.
     */
    public Optional<Bill> findById(int id) {
        String sql = "SELECT order_id, clientName, clientEmail, clientPhoneNumber, productName, quantity " +
                "FROM Log WHERE logId = ?";
        try (Connection connection = ConnectionClass.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int orderId = resultSet.getInt("order_id");
                    String clientName = resultSet.getString("clientName");
                    String clientEmail = resultSet.getString("clientEmail");
                    String clientPhoneNumber = resultSet.getString("clientPhoneNumber");
                    String productName = resultSet.getString("productName");
                    Integer quantity = resultSet.getInt("quantity");
                    return Optional.of(new Bill(orderId, clientName, clientEmail, clientPhoneNumber, productName, quantity));
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * Retrieves all Bill records from the database.
     * @return A list containing all Bill objects found in the database.
     */
    public List<Bill> findAll() {
        List<Bill> billList = new ArrayList<>();
        String sql = "SELECT order_id, clientName, clientEmail, clientPhoneNumber, productName, quantity " +
                "FROM Log";
        try (Connection connection = ConnectionClass.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int orderId = resultSet.getInt("order_id");
                String clientName = resultSet.getString("clientName");
                String clientEmail = resultSet.getString("clientEmail");
                String clientPhoneNumber = resultSet.getString("clientPhoneNumber");
                String productName = resultSet.getString("productName");
                Integer quantity = resultSet.getInt("quantity");
                billList.add(new Bill(orderId, clientName, clientEmail, clientPhoneNumber, productName, quantity));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return billList;
    }
}
