package entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Customer {

        private Integer id;

        private String name;

        private LocalDateTime createdAt;

        public Customer(String name){
            this.name = name;
        }

        public Customer(){}
}
