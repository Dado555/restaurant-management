package aldentebackend.service;

import aldentebackend.model.Order;
import aldentebackend.model.OrderItem;

import java.util.Collection;

public interface OrderItemService extends JPAService<OrderItem>{

    OrderItem create(OrderItem orderItem);

    OrderItem update(Long id, Integer amount, String description);

    OrderItem markOrderItemAsAwaitingApproval(Long id);

    OrderItem markOrderItemAsForPreparation(Long id);

    OrderItem cancelOrderItem(Long id);

    OrderItem takeOrderItem(Long id);

    OrderItem markOrderItemAsReady(Long id);

    OrderItem markOrderItemAsDelivered(Long id);

    Collection<Order> findNewOrderItemsGroupedInOrders();

    Collection<Order> findAcceptedOrderItemsGroupedInOrders();

    Collection<OrderItem> findOrderFinished();

    Double getProfit();

    Collection<Collection<Collection<Double>>> getIncomesAndExpensesForNDays(Integer days);
}
