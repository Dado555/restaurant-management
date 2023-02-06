package aldentebackend.service;

import aldentebackend.model.Order;
import aldentebackend.model.OrderItem;

import java.util.Collection;
import java.util.Map;

public interface OrderService extends JPAService<Order> {

    Collection<OrderItem> findOrderItemsForOrder(Long id);

    Order create(Order order);

    Order checkoutOrder(Long id);

    Collection<Order> findOrdersInPeriod(Long start, Long end);

    Collection<Order> findAllActiveOrders();

    Collection<Order> findOrdersForTable(Long tableId);

    Collection<Order> findOrdersByStatusStatusInProgress();

    Collection<Order> findOrdersByStatusForPreparationOrStatusInProgress();

    Map<String, Object> getIncomeAndExpensesStatistic(Long start, Long end);

    Integer getOrdersCount();

    Integer getNewOrdersCount();

    Collection<Order> getForLastNDays(Integer days);

    Integer[] getOrdersCountForNDays(Integer days);

    Collection<Integer> getOrdersCountForLastNDays(Integer days);
}
