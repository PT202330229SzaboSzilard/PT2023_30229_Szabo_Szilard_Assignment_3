package ro.tuc.dao;

import ro.tuc.connection.ConnectionFactory;
import ro.tuc.start.Reflection;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * AbstractDAO implementeaza metodele createSelectQuery, findAll, findById, createObjects, createInsertQuery,
 * createDeleteQuery, insert, delete, update generalizate pentru clase DAO de clienti, produse si comenzi
 * @param <T>
 */

public class AbstractDAO<T> {

    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE ").append(field).append(" = ?");
        return sb.toString();
    }

    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + type.getSimpleName();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }


    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } catch(IndexOutOfBoundsException e)
        {
            return null;
        }
        finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (Constructor constructor : ctors) {
            ctor = constructor;
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
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IntrospectionException e) {
                e.printStackTrace();
            }
            return list;
    }

    public T insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createInsertQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            Object[] objects = new Object[type.getDeclaredFields().length];
            Reflection.retrieveProperties(t,objects);
            for(int  i = 1; i < objects.length; i++)
            {
                statement.setObject(i,objects[i]);
            }
            statement.executeUpdate();


            return t;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return null;
    }

    private String createInsertQuery() {
        StringBuilder stringCreator = new StringBuilder();
        stringCreator.append("INSERT INTO ");
        if(type.getSimpleName().equals("Order"))
        {
            stringCreator.append("`").append(type.getSimpleName()).append("`");
        }
        else
            stringCreator.append(type.getSimpleName());
        stringCreator.append(" (");
        Field[] fields = type.getDeclaredFields();
        for (int i = 1; i < fields.length; i++) {
            stringCreator.append(fields[i].getName());
            if (i < fields.length - 1) {
                stringCreator.append(", ");
            }
        }
        stringCreator.append(") VALUES (");
        for (int i = 1; i < fields.length; i++) {
            stringCreator.append("?");
            if (i < fields.length - 1) {
                stringCreator.append(", ");
            }
        }
        stringCreator.append(")");
        return stringCreator.toString();
    }

    private void setId(T t, int id) {
        try {
            Field idField = type.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(t, id);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private String createDeleteQuery() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DELETE FROM ");
        stringBuilder.append(type.getSimpleName());
        stringBuilder.append(" WHERE id = ?");
        return stringBuilder.toString();
    }
    public void delete(int id) {
        // TODO:
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createDeleteQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }
    public T update(T t, int id,  String columnName, Object columnValue) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = "UPDATE " + type.getSimpleName().toLowerCase() + " SET " + columnName + " = ? WHERE id = ?";
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setObject(1, columnValue);

            statement.setInt(2, id);
            statement.executeUpdate();
            return t;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    private String getColumnName(Field field) {
        // Assuming column names are the same as field names
        return field.getName();
    }

}
