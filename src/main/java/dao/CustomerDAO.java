package dao;

import entities.Customer;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    private static final String URL = "jdbc:postgresql://localhost:5433/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String DRIVER_NAME = "org.postgresql.Driver";
    private static CustomerDAO dao;

    public CustomerDAO() {
    }

    public static CustomerDAO getInstance() {
        if (dao == null) {
            dao = new CustomerDAO();
        }
        return dao;
    }

    public static Connection connectDB() {
        Connection connection = null;
        try {
            // returns the class object
            Class.forName(DRIVER_NAME);

            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception message) {
            System.out.println(message);
        }
        return connection;
    }

    public int create(Customer customer) throws SQLException {
        Connection connect = CustomerDAO.connectDB();
        PreparedStatement preparedStatement = connect.prepareStatement(
                "insert into customers (name) values (?)"
        );

        // set the parameter to the given Java String value
        preparedStatement.setString(1, customer.getName());


        int result = preparedStatement.executeUpdate();

        connect.close();
        return result;
    }

    public int update(int customerId, Customer customer) throws SQLException {

        Connection connect = CustomerDAO.connectDB();

        PreparedStatement preparedStatement = connect.prepareStatement(
                "update customers set name = ? where id = ?"
        );

        preparedStatement.setString(1, customer.getName());
        preparedStatement.setInt(2, customerId);
        int result = preparedStatement.executeUpdate();
        connect.close();
        return result;
    }

    public int delete(int id) throws SQLException {

        Connection connect = CustomerDAO.connectDB();
        PreparedStatement preparedStatement = connect.prepareStatement(
                "delete from customers where id =?"
        );
        preparedStatement.setInt(1, id);

        int result = preparedStatement.executeUpdate();

        // close the database connection
        connect.close();

        return result;
    }

    public Customer get(int id) throws SQLException {
        // create a user object
        Customer customer = new Customer();

        // create connection at the call of the method
        Connection connect = CustomerDAO.connectDB();

        PreparedStatement preparedStatement = connect.prepareStatement(
                "select * from customers where id=?"
        );

        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (resultSet.next()) {
            customer.setId(resultSet.getInt(1));
            customer.setName(resultSet.getString(2));
            String createdAtStr = resultSet.getString(3);
            createdAtStr = createdAtStr.substring(0, createdAtStr.lastIndexOf("."));
            customer.setCreatedAt(LocalDateTime.parse(createdAtStr, formatter));
        }

        // close the database connection
        connect.close();
        return customer;
    }
}