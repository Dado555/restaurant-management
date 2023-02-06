package aldentebackend.dto.orderitem;

import lombok.*;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class OrderItemUpdateDTO {

    private Integer amount;
    private String description;
}
