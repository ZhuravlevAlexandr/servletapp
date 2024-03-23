package mappers;


import dto.CustomerDto;
import dto.OrderDto;
import entities.Customer;

import java.util.List;

public class CustomerMapper {

    public static Customer fromDto(CustomerDto dto) {

        return new Customer(dto.getName());
    }

    public static CustomerDto toDto(Customer customer, List<OrderDto> list) {
        if(customer == null){
            return null;
        }
        return new CustomerDto(
                customer.getId(),
                customer.getName(),
                customer.getCreatedAt(),
                list
        );
    }
}

