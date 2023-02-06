package aldentebackend.support.order;

import aldentebackend.dto.order.OrderCreateDTO;
import aldentebackend.exception.BadRequestException;
import aldentebackend.model.Order;
import aldentebackend.model.RestaurantTable;
import aldentebackend.model.Waiter;
import aldentebackend.model.enums.OrderStatus;
import aldentebackend.service.OrderService;
import aldentebackend.service.TableService;
import aldentebackend.service.UserService;
import aldentebackend.support.AbstractConverter;
import aldentebackend.support.orderitem.OrderItemInOrderCreateDTOToOrderItem;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class OrderCreateDTOToOrder extends AbstractConverter<OrderCreateDTO, Order> {

    private final OrderItemInOrderCreateDTOToOrderItem toOrderItem;

    private final UserService userService;
    private final TableService tableService;
    private final OrderService orderService;

    @Override
    public Order convert(@NonNull OrderCreateDTO source) {

        Waiter waiter = (Waiter) userService.findOne(source.getWaiterId());
        RestaurantTable table = tableService.findOne(source.getTableId());

        List<Order> orders = (List<Order>) orderService.findOrdersForTable(table.getId());
        if (orders.size() > 1)
            throw new BadRequestException("");

        if (orders.size() == 1) {
            var o = orders.get(0);
            o.setOrderItems(source.getOrderItems().stream().map(toOrderItem::convert).collect(Collectors.toSet()));
            o.setStatus(OrderStatus.FOR_PREPARATION);
            o.setDate(System.currentTimeMillis());
            return o;
        }

        Order order = new Order(table, waiter);
        order.setOrderItems(source.getOrderItems().stream().map(toOrderItem::convert).collect(Collectors.toSet()));
        order.setStatus(OrderStatus.FOR_PREPARATION);
        order.setDate(System.currentTimeMillis());
        return order;
    }
}
