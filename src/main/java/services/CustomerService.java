package services;

import dao.CustomerDAO;
import dao.OrderDAO;
import dto.CustomerDto;
import entities.Customer;
import entities.Order;
import mappers.CustomerMapper;
import mappers.OrderMapper;

import java.sql.SQLException;
import java.util.List;

public class CustomerService {

    private final CustomerDAO customerDAO;
    private final OrderDAO orderDAO;

    public CustomerService() {
        customerDAO = CustomerDAO.getInstance();
        orderDAO = OrderDAO.getInstance();
    }

    public int create(CustomerDto dto) {
        try {
            return customerDAO.create(CustomerMapper.fromDto(dto));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int update(int customerId, CustomerDto dto) {
        try {
            return customerDAO.update(customerId, CustomerMapper.fromDto(dto));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int delete(int customerId) {
        try {
            return customerDAO
                    .delete(customerId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public CustomerDto get(int customerId) {
        try {
            Customer entity = customerDAO.get(customerId);
            List<Order> orders = orderDAO.getAllByCustomerId(customerId);

            return CustomerMapper.toDto(entity, OrderMapper.toDtoList(orders));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
