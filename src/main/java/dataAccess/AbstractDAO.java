package dataAccess;

import databaseConnection.ConnectionClass;

import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.ParameterizedType;

/**
 * A class providing CRUD operations for data access objects.
 * @param <T>
 */

public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    /**
     *Constructs an AbstractDAO object.
     */
    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    /**
     * Creates a SELECT query for a specific field.
     * @param field The field to search for.
     * @return The generated SELECT query as a string.
     */
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    /**
     * Retrieves all entities of type T from the database.
     * @return A list of all entities retrieved from the database.
     */
    public List<T> findAll() {
        List<T> resultList = new ArrayList<>();
        String query = "SELECT * FROM " + type.getSimpleName();

        try (Connection connection = ConnectionClass.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            resultList = createObjects(resultSet);

        } catch (SQLException | IntrospectionException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        }
        return resultList;
    }

    /**
     * Finds an entity by its ID.
     * @param id The ID of the entity to retrieve.
     * @return The entity with the specified ID, or null if no matching entity is found.
     */
    public T findById(int id) {
        //using parametrizedType to get the actual type from the caller DAO
        ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
        Class<T> type = (Class<T>) superclass.getActualTypeArguments()[0];

        String query = createSelectQuery(type.getSimpleName() + "Id");
        try (Connection connection = ConnectionClass.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<T> objects = createObjects(resultSet);
                if (!objects.isEmpty()) {
                    return objects.get(0);
                }
            }
        } catch (SQLException | IntrospectionException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        }
        return null;
    }

    /**
     *<p>
     * This method searches for a constructor with no parameters, iterates through the resultSet and builds instances of type T
     * using the constructor found earlier, it then invokes the setter method of the class, it then adds the object created to the
     * list that has to be returned.
     * <p>
     * Creates objects of type T from a ResultSet.
     * @param resultSet The ResultSet containing data to be converted into objects.
     * @return A list of objects of type T.
     * @throws IntrospectionException If an introspection error occurs.
     */
    private List<T> createObjects(ResultSet resultSet) throws IntrospectionException {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T) ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * This method acts like a setter method for the object T's ID.
     * Sets the ID of an object.
     * @param object The object whose ID is to be set.
     * @param id The ID value to set.
     */
    protected void setId(T object, Integer id) {
        try {
            Method setIdMethod = object.getClass().getMethod("set" + object.getClass().getSimpleName() + "Id", Integer.class);
            setIdMethod.invoke(object, id);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            LOGGER.log(Level.WARNING, "Unable to set ID for object: " + e.getMessage());
        }
    }

    /**
     * Inserts a new record into the database.
     * @param t The object representing the record to be inserted.
     * @return The object representing the inserted record, with any generated ID set.
     * @throws SQLException If an SQL exception occurs during the insertion process.
     */
    public T insert(T t) {
        try (Connection connection = ConnectionClass.getConnection();
             PreparedStatement statement = createInsertStatement(connection, t)) {

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Inserting record failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    setId(t, id);
                } else {
                    throw new SQLException("Inserting failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        }
        return t;
    }

    /**
     * Dynamically creates an SQL INSERT query for the given object.
     * @param connection The database connection.
     * @param t The object for which the query is built.
     * @return The prepared statement for the INSERT query.
     * @throws SQLException If a SQL exception occurs.
     *
     */
    private PreparedStatement createInsertStatement(Connection connection, T t) throws SQLException {
        StringBuilder queryBuilder = new StringBuilder("INSERT INTO ");
        queryBuilder.append(type.getSimpleName());
        queryBuilder.append(" (");

        List<String> fieldsToInsert = new ArrayList<>();
        List<Object> valuesToInsert = new ArrayList<>();
        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(t);
                if (!field.getName().equals("id")) {
                    fieldsToInsert.add(field.getName());
                    valuesToInsert.add(value);
                }
            } catch (IllegalAccessException e) {
                LOGGER.log(Level.WARNING, "Error accessing field: " + e.getMessage());
            }
        }
        queryBuilder.append(String.join(", ", fieldsToInsert));

        queryBuilder.append(") VALUES (");
        for (int i = 0; i < fieldsToInsert.size(); i++) {
            if (i > 0) {
                queryBuilder.append(", ");
            }
            queryBuilder.append("?");
        }
        queryBuilder.append(")");

        PreparedStatement statement = connection.prepareStatement(queryBuilder.toString(), Statement.RETURN_GENERATED_KEYS);

        int paramIndex = 1;
        try {
            for (Object value : valuesToInsert) {
                statement.setObject(paramIndex, value);
                paramIndex++;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error setting parameters: " + e.getMessage());
        }

        return statement;
    }

    /**
     * Updates an object in the database.
     * @param t The object to be updated.
     * @return The object given as parameter.
     *
     */
    public T update(T t) {
        try (Connection connection = ConnectionClass.getConnection()) {
            String tableName = type.getSimpleName();
            List<String> columnsToUpdate = new ArrayList<>();
            List<Object> valuesToUpdate = new ArrayList<>();

            StringBuilder setClause = new StringBuilder();
            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = field.getName();
                if (!fieldName.equalsIgnoreCase("id")) {
                    Object value = field.get(t);
                    columnsToUpdate.add(fieldName);
                    valuesToUpdate.add(value);
                    setClause.append(fieldName).append("=?, ");
                }
            }
            setClause.delete(setClause.length() - 2, setClause.length());
            String whereClause = type.getSimpleName() + "Id=?";
            String query = "UPDATE " + tableName + " SET " + setClause.toString() + " WHERE " + whereClause;

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                int paramIndex = 1;
                for (Object value : valuesToUpdate) {
                    statement.setObject(paramIndex, value);
                    paramIndex++;
                }
                Method getIdMethod = t.getClass().getMethod("get" + t.getClass().getSimpleName() + "Id");
                statement.setInt(paramIndex, (int) getIdMethod.invoke(t));

                int affectedRows = statement.executeUpdate();
                if (affectedRows == 0) {
                    LOGGER.log(Level.WARNING, "Updating record failed, no rows affected.");
                }
            }
        } catch (SQLException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        }
        return t;
    }

    /**
     * Deletes an object from the database.
     * @param t The object to be deleted.
     * @return The object given as parameter.
     */
    public T delete(T t) {
        try (Connection connection = ConnectionClass.getConnection()) {
            String tableName = type.getSimpleName();
            String idColumnName = tableName + "Id";
            String query = "DELETE FROM " + tableName + " WHERE " + idColumnName + "=?";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                Method getIdMethod = t.getClass().getMethod("get" + t.getClass().getSimpleName() + "Id");
                int id = (int) getIdMethod.invoke(t);
                statement.setInt(1, id);

                int affectedRows = statement.executeUpdate();
                if (affectedRows == 0) {
                    LOGGER.log(Level.WARNING, "Deleting record failed, no rows affected.");
                }
            }
        } catch (SQLException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
        }
        return t;
    }


}


