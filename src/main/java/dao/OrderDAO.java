package dao;

import entities.Order;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    private static final String URL = "jdbc:postgresql://localhost:5433/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String DRIVER_NAME = "org.postgresql.Driver";
    private static OrderDAO dao;

    private OrderDAO() {
    }

    public static OrderDAO getInstance() {
        if (dao == null) {
            dao = new OrderDAO();
        }
        return dao;
    }

    public static Connection connectDB() {
        Connection connection = null;
        try {
            Class.forName(DRIVER_NAME);

            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception message) {
            System.out.println(message);
        }
        return connection;
    }

    public List<Order> getAllByCustomerId(int customerId) throws SQLException {
        List<Order> orders = new ArrayList<>();

        Connection connect = OrderDAO.connectDB();

        PreparedStatement preparedStatement = connect.prepareStatement(
                "select * from orders where customer_id=?"
        );

        preparedStatement.setInt(1, customerId);

        ResultSet resultSet = preparedStatement.executeQuery();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        while (resultSet.next()) {
            Order order = new Order();

            order.setId(resultSet.getInt(1));
            order.setCustomerId(resultSet.getInt(2));
            order.setName(resultSet.getString(3));
            order.setQuantity(resultSet.getInt(4));

            String orderedAtStr = resultSet.getString(5);
            String finishedAtStr = resultSet.getString(6);
            orderedAtStr = orderedAtStr.substring(0, orderedAtStr.lastIndexOf("."));
            order.setOrderedAt(LocalDateTime.parse(orderedAtStr, formatter));
            if (finishedAtStr != null) {
                finishedAtStr = finishedAtStr.substring(0, finishedAtStr.lastIndexOf("."));
                order.setFinishedAt(LocalDateTime.parse(finishedAtStr, formatter));
            }

            orders.add(order);
        }

        connect.close();

        return orders;
    }

    public int createOrder(Order order, int customerId) throws SQLException {
        Connection connect = OrderDAO.connectDB();
        PreparedStatement preparedStatement = connect.prepareStatement(
                "insert into orders (customer_id, name, quantity) values (?, ?, ?)"
        );

        preparedStatement.setInt(1, customerId);
        preparedStatement.setString(2, order.getName());
        preparedStatement.setInt(3, order.getQuantity());

        int result = preparedStatement.executeUpdate();
        connect.close();

        return result;
    }

    public int finishOrder(int orderId) throws SQLException {
        Connection connect = OrderDAO.connectDB();
        PreparedStatement preparedStatement = connect.prepareStatement(
                "update orders set finished_at = now() where id = ?"
        );

        preparedStatement.setInt(1, orderId);

        int result = preparedStatement.executeUpdate();
        connect.close();

        return result;
    }
}
