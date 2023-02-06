package aldentebackend.dto.orderitem;

import aldentebackend.model.enums.OrderItemStatus;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class OrderItemCreateDTO {

    @NotNull
    private Long orderId;

    @NotNull
    private Long menuItemId;

    @NotNull
    @Positive
    private Integer amount;

    @NotNull
    private OrderItemStatus status;

    private String description;
}
