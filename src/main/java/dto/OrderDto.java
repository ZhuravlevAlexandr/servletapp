package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
public class OrderDto {

    private Integer id;

    private String name;

    private int quantity;

    private LocalDateTime orderedAt;

    private LocalDateTime finishedAt;


}
