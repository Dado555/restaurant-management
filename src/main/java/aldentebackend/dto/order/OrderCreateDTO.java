package aldentebackend.dto.order;

import aldentebackend.dto.orderitem.OrderItemInOrderCreateDTO;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
public class OrderCreateDTO {

    @NotNull
    private Long tableId;

    @NotNull
    private Long waiterId;

    @NotEmpty
    Collection<OrderItemInOrderCreateDTO> orderItems;
}
