package aldentebackend.dto.orderitem;

import aldentebackend.model.enums.OrderItemStatus;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
public class OrderItemInOrderCreateDTO {

    @NotNull
    private Long menuItemId;

    @NotNull
    private Integer amount;

    @NotNull
    private OrderItemStatus status;

    private String description;
}
