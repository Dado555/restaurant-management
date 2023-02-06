package aldentebackend.support.orderitem;

import aldentebackend.dto.menuitem.MenuItemInfoDTO;
import aldentebackend.dto.orderitem.OrderItemInfoDTO;
import aldentebackend.dto.price.PriceInfoDTO;
import aldentebackend.model.MenuItem;
import aldentebackend.model.OrderItem;
import aldentebackend.support.AbstractConverter;
import aldentebackend.support.IConverter;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class OrderItemToOrderItemInfoDTO extends AbstractConverter<OrderItem, OrderItemInfoDTO> {

    private final IConverter<MenuItem, MenuItemInfoDTO> toMenuItemInfoDTO;

    @Override
    public OrderItemInfoDTO convert(@NonNull OrderItem source) {

        return new OrderItemInfoDTO(source.getId(),
                                    source.getOrder().getId(),
                                    toMenuItemInfoDTO.convert(source.getMenuItem()),
                                    source.getAmount(),
                                    source.getStatus(),
                                    new PriceInfoDTO(source.getCurrentPrice()),
                                    source.getDescription());
    }
}
