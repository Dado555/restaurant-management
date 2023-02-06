package aldentebackend.dto.order;

import aldentebackend.dto.orderitem.OrderItemInfoDTO;
import aldentebackend.dto.table.TableInfoDTO;
import aldentebackend.dto.user.UserInfoDTO;
import aldentebackend.model.enums.OrderStatus;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Set;

@Data
public class OrderWithOrderItemsDTO {

    @NotNull
    private Long id;

    @NotNull
    private Long date;

    @NotNull
    private TableInfoDTO table;

    @NotNull
    private UserInfoDTO waiter;

    @NotNull
    private OrderStatus status;

    private Collection<OrderItemInfoDTO> orderItems;
}
