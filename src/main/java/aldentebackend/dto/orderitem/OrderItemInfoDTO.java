package aldentebackend.dto.orderitem;

import aldentebackend.dto.menuitem.MenuItemInfoDTO;
import aldentebackend.dto.price.PriceInfoDTO;
import aldentebackend.model.enums.OrderItemStatus;
import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class OrderItemInfoDTO {

    @NotNull
    private Long id;

    @NotNull
    private Long orderId;

    @NotNull
    private MenuItemInfoDTO menuItem;

    @NotNull
    private Integer amount;

    @NotNull
    private OrderItemStatus status;

    @NotNull
    private PriceInfoDTO price;

    private String description;
}
