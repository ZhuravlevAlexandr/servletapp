package entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Order {

    private Integer id;

    private int customerId;

    private String name;

    private int quantity;

    private LocalDateTime orderedAt;

    private LocalDateTime finishedAt;

    public Order(String name, int quantity){
        this.name = name;
        this.quantity = quantity;
    }

    public Order() {}
}

