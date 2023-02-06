package aldentebackend.service.impl;

import aldentebackend.exception.BadRequestException;
import aldentebackend.model.*;
import aldentebackend.model.enums.OrderStatus;
import aldentebackend.repository.OrderRepository;
import aldentebackend.service.*;
import aldentebackend.service.socket.SocketService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OrderServiceImpl extends JPAServiceImpl<Order> implements OrderService {

    private final OrderRepository orderRepository;

    private final SocketService socketService;
    private final AuthorityService authorityService;
    private final UserService userService;

    @Override
    protected JpaRepository<Order, Long> getEntityRepository() {
        return orderRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<OrderItem> findOrderItemsForOrder(Long id) {
        Order order = findOne(id);
        return new ArrayList<>(order.getOrderItems());
    }
  
    @Override
    @Transactional
    public Order create(Order order) {
        Set<OrderItem> orderItems = order.getOrderItems();

        order.setOrderItems(new HashSet<>());
        order = save(order);

        for (OrderItem oi: orderItems) {
            oi.setOrder(order);
            order.getOrderItems().add(oi);
        }

        save(order);

        if (orderItems.stream().anyMatch(oi -> oi.getMenuItem().getItem() instanceof Food))
            socketService.notifyFormMe("ALL", "New order is requested", "COOK");
        else if (orderItems.stream().anyMatch(oi -> oi.getMenuItem().getItem() instanceof Drink))
            socketService.notifyFormMe("ALL", "New order is requested", "BARTENDER");

        return order;
    }

    @Override
    @Transactional
    public Order checkoutOrder(Long id) {
        Order order = findOne(id);
        if (!order.getStatus().equals(OrderStatus.DELIVERED))
            throw new BadRequestException("Order is not in status DELIVERED");

        order.setStatus(OrderStatus.FINISHED);
        save(order);

        socketService.notifyFormMe("HIDDEN", "Order Changed|ORDER|" + order.getId());
        return order;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Order> findOrdersInPeriod(Long start, Long end) {
        return orderRepository.getOrdersByDateAfterAndDateBefore(start, end);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getOrdersCount() {
        return this.findAll().size();
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getNewOrdersCount() {
        return orderRepository.findOrdersByStatusForPreparation().size();
    }

    @Override
    public Collection<Order> getForLastNDays(Integer days) {
        Date date = new Date();
        return findOrdersInPeriod(date.getTime()-(86400000L * days), date.getTime());
    }

    @Override
    public Integer[] getOrdersCountForNDays(Integer days) {
        Collection<Order> orders = getForLastNDays(days);
        Integer[] stats = new Integer[] {0, 0, 0, 0};
        for(Order o : orders) {
            switch (o.getStatus().name()) {
                case "IN_PROGRESS":
                    stats[0] += 1;
                    break;
                case "READY":
                    stats[1] += 1;
                    break;
                case "DELIVERED":
                    stats[2] += 1;
                    break;
                case "FINISHED":
                    stats[3] += 1;
                    break;
            }
        }
        return stats;
    }

    @Override
    public Collection<Integer> getOrdersCountForLastNDays(Integer days) {
        Date date = new Date();
        List<Integer> retList = new ArrayList<>();
        for(int i = days; i >= 1; i--) {
            int size = orderRepository.findAllInRange(date.getTime()-(86400000L * i), date.getTime()-(86400000L * (i-1))).size();
            retList.add(size);
        }
        return retList;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Order> findAllActiveOrders() {
        return orderRepository.findAllActiveOrders();
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Order> findOrdersForTable(Long tableId) {
        Collection<Order> orders =  orderRepository.getOrdersForTable(tableId);
        orders.forEach(o -> {
            o.setOrderItems(new HashSet<>(o.getOrderItems()));
            o.getOrderItems().forEach(oi -> oi.getMenuItem().setPrices(new HashSet<>(oi.getMenuItem().getPrices())));
        });
        return orders;
    }

    @Override
    public Collection<Order> findOrdersByStatusStatusInProgress() {
        return orderRepository.findOrdersByStatusInProgress();
    }

    @Override
    public Collection<Order> findOrdersByStatusForPreparationOrStatusInProgress() {
        return orderRepository.findOrdersByStatusForPreparationOrStatusInProgress();
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getIncomeAndExpensesStatistic(Long start, Long end) {
        Collection<Order> orders = this.findOrdersInPeriod(start, end);

        double total = 0.0, expenses = 0.0;
        for (Order order : orders){
            for(OrderItem orderItem : order.getOrderItems()) {
                total += orderItem.getCurrentPrice();
                expenses += orderItem.getCurrentExpense();
            }
            // total += order.getOrderItems().stream().mapToDouble(OrderItem::getCurrentPrice).sum();
            // expenses += order.getOrderItems().stream().mapToDouble(OrderItem::getCurrentExpense).sum();
        }

        HashMap<String, Object> report = new HashMap<>();
        report.put("total", total);
        report.put("expenses", expenses);
        return report;
    }
}
