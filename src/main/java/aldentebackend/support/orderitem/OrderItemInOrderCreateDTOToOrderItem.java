package aldentebackend.support.orderitem;

import aldentebackend.dto.orderitem.OrderItemInOrderCreateDTO;
import aldentebackend.model.MenuItem;
import aldentebackend.model.OrderItem;
import aldentebackend.model.Price;
import aldentebackend.model.enums.OrderItemStatus;
import aldentebackend.service.MenuItemService;
import aldentebackend.service.PriceService;
import aldentebackend.support.AbstractConverter;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class OrderItemInOrderCreateDTOToOrderItem extends AbstractConverter<OrderItemInOrderCreateDTO, OrderItem> {

    private final MenuItemService menuItemService;
    private final PriceService priceService;

    @Override
    public OrderItem convert(@NonNull OrderItemInOrderCreateDTO source) {
        MenuItem menuItem = menuItemService.findOne(source.getMenuItemId());

        OrderItem orderItem = new OrderItem();
        orderItem.setMenuItem(menuItem);

        if (source.getStatus() == OrderItemStatus.AWAITING_APPROVAL)
            orderItem.setStatus(OrderItemStatus.AWAITING_APPROVAL);
        else
            orderItem.setStatus(OrderItemStatus.FOR_PREPARATION);


        Price price = priceService.findCurrentPriceForMenuItem(menuItem.getId());
        orderItem.setCurrentPrice(price.getValue());
        orderItem.setCurrentExpense(price.getExpense());

        orderItem.setAmount(source.getAmount());
        orderItem.setDescription(source.getDescription());
        return orderItem;
    }
}
