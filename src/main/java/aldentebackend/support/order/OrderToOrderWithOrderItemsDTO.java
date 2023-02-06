package aldentebackend.support.order;

import aldentebackend.dto.order.OrderWithOrderItemsDTO;
import aldentebackend.dto.orderitem.OrderItemInfoDTO;
import aldentebackend.dto.table.TableInfoDTO;
import aldentebackend.dto.user.UserInfoDTO;
import aldentebackend.model.Order;
import aldentebackend.model.OrderItem;
import aldentebackend.model.RestaurantTable;
import aldentebackend.model.User;
import aldentebackend.support.AbstractConverter;
import aldentebackend.support.IConverter;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class OrderToOrderWithOrderItemsDTO extends AbstractConverter<Order, OrderWithOrderItemsDTO> {

    private final IConverter<User, UserInfoDTO> toUserInfoDTO;
    private final IConverter<RestaurantTable, TableInfoDTO> toTableInfoDTO;
    private final IConverter<OrderItem, OrderItemInfoDTO> toOrderItemInfoDTO;

    @Override
    public OrderWithOrderItemsDTO convert(@NonNull Order source) {
        OrderWithOrderItemsDTO retVal = new OrderWithOrderItemsDTO();
        retVal.setId(source.getId());
        retVal.setStatus(source.getStatus());
        retVal.setDate(source.getDate());
        retVal.setWaiter(toUserInfoDTO.convert(source.getWaiter()));
        retVal.setTable(toTableInfoDTO.convert(source.getTable()));
        retVal.setOrderItems(toOrderItemInfoDTO.convert(source.getOrderItems()));

        return retVal;
    }
}
