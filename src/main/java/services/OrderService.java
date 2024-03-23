package services;


import dao.OrderDAO;
import dto.OrderDto;
import mappers.OrderMapper;

import java.sql.SQLException;
import java.util.List;


public class OrderService {

    private final OrderDAO orderDAO;

    public OrderService() {
        orderDAO = OrderDAO.getInstance();
    }

    public List<OrderDto> getAllByCustomerId(int customerId) {
        try {
            return OrderMapper.toDtoList(
                    orderDAO.getAllByCustomerId(customerId)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int createOrder(int customerId, OrderDto order) {
        try {
            return orderDAO.createOrder(OrderMapper.fromDto(order), customerId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int finishOrder(int orderId) {
        try {
            return orderDAO.finishOrder(orderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
