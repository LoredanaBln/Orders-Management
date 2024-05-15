package databaseConnection;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * The ConnectionClass class is used for establishing database connection in other classes.
*/
public class ConnectionClass {
    private static final Logger LOGGER = Logger.getLogger(ConnectionClass.class.getName());
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/orders_management";
    private static final String USER = "root";
    private static final String PASS = "ianuarie31";

    private static final ConnectionClass singleInstance = new ConnectionClass();

    private ConnectionClass() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
/**
 * @return A {@link java.sql.Connection} object representing the connection to the database, or {@code null} if the connection could not be established.
 */
    private Connection createConnection() {
        Connection connectionClass = null;
        try {
            connectionClass = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "An error occurred while trying to connect to the database");
            e.printStackTrace();
        }
        return connectionClass;
    }

    /**
     * Retrieves a database connection.
     * <p>
     * This method returns a connection to the database. It utilizes a singleton pattern to ensure that only one
     * instance of the connection is used throughout the application.
     * </p>
     * @return A {@link java.sql.Connection Connection} object representing the connection to the database.
     */
    public static Connection getConnection() {
        return singleInstance.createConnection();
    }


}
