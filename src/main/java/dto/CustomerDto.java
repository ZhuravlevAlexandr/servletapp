package dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class CustomerDto {

        private Integer id;

        private String name;

        private LocalDateTime createdAt;

        private List<OrderDto> orders;

        public CustomerDto(String name){
                this.name = name;
        }
}
