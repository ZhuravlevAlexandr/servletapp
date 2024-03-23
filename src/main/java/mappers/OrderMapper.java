package mappers;


import dto.OrderDto;
import entities.Order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class OrderMapper {

    public static Order fromDto(OrderDto dto) {

        return new Order(
                dto.getName(),
                dto.getQuantity()
        );
    }

    public static OrderDto toDto(Order order) {

        return new OrderDto(
                order.getId(),
                order.getName(),
                order.getQuantity(),
                order.getOrderedAt(),
                order.getFinishedAt()
        );
    }

    public static List<OrderDto> toDtoList(List<Order> orders) {
        List<OrderDto> orderDtoList = new ArrayList<>();

        for (Order order: orders) {
            orderDtoList.add(OrderMapper.toDto(order));
        }

        return orderDtoList;
    }
}
