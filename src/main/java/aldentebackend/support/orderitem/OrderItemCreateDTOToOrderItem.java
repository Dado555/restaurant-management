package aldentebackend.support.orderitem;

import aldentebackend.dto.orderitem.OrderItemCreateDTO;
import aldentebackend.exception.BadRequestException;
import aldentebackend.model.MenuItem;
import aldentebackend.model.Order;
import aldentebackend.model.OrderItem;
import aldentebackend.model.Price;
import aldentebackend.model.enums.OrderItemStatus;
import aldentebackend.service.MenuItemService;
import aldentebackend.service.OrderService;
import aldentebackend.service.PriceService;
import aldentebackend.support.AbstractConverter;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class OrderItemCreateDTOToOrderItem extends AbstractConverter<OrderItemCreateDTO, OrderItem> {

    private final OrderService orderService;
    private final MenuItemService menuItemService;
    private final PriceService priceService;

    @Override
    public OrderItem convert(@NonNull OrderItemCreateDTO source) {
        Order order = orderService.findOne(source.getOrderId());
        MenuItem menuItem = menuItemService.findOne(source.getMenuItemId());

        if (!source.getStatus().equals(OrderItemStatus.FOR_PREPARATION) && !source.getStatus().equals(OrderItemStatus.AWAITING_APPROVAL))
            throw new BadRequestException("Status for order item is not valid: " + source.getStatus());

        Price price = priceService.findCurrentPriceForMenuItem(menuItem.getId());
        return new OrderItem(order, menuItem, price.getExpense(), price.getValue(), source.getAmount(), source.getStatus(), source.getDescription());
    }
}
