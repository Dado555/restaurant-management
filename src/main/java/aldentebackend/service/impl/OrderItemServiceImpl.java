package aldentebackend.service.impl;

import aldentebackend.exception.BadRequestException;
import aldentebackend.model.*;
import aldentebackend.model.enums.OrderItemStatus;
import aldentebackend.model.enums.OrderStatus;
import aldentebackend.repository.OrderItemRepository;
import aldentebackend.service.AuthorityService;
import aldentebackend.service.OrderItemService;
import aldentebackend.service.OrderService;
import aldentebackend.service.UserService;
import aldentebackend.service.socket.SocketService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OrderItemServiceImpl extends JPAServiceImpl<OrderItem> implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    private final SocketService socketService;
    private final AuthorityService authorityService;
    private final UserService userService;
    private final OrderService orderService;

    @Override
    protected JpaRepository<OrderItem, Long> getEntityRepository() {
        return orderItemRepository;
    }

    @Transactional
    @Override
    public OrderItem create(OrderItem orderItem) {
        orderItem = save(orderItem);

        socketService.notifyFormMe("ALL", "Order Item Added" + orderItem.getId(), "COOK");
        return orderItem;
    }

    @Transactional
    @Override
    public OrderItem update(Long id, Integer amount, String description) {
        OrderItem orderItem = findOne(id);

        if (amount != null) {
            if (amount < 1)
                throw new BadRequestException("Amount must be greater then 0");

            orderItem.setAmount(amount);
        }

        if (description != null && !(description.isBlank()))
            orderItem.setDescription(description);

        save(orderItem);
        socketService.notifyFormMe("HIDDEN", "Order Item status changed|ORDER_ITEM|" + orderItem.getId(), null);
        return orderItem;
    }

    @Transactional
    @Override
    public OrderItem markOrderItemAsAwaitingApproval(Long id) {
        OrderItem orderItem = findOne(id);

        if (!orderItem.getStatus().equals(OrderItemStatus.FOR_PREPARATION))
            throw new BadRequestException("Order item can change status to AWAITING APPROVAL only if current status is FOR PREPARATION");

        orderItem.setStatus(OrderItemStatus.AWAITING_APPROVAL);
        save(orderItem);

//        socketService.notifyFormMe("ALL", "Order Item Changed|ORDER_ITEM|" + orderItem.getId());
        return orderItem;
    }

    @Transactional
    @Override
    public OrderItem markOrderItemAsForPreparation(Long id) {
        OrderItem orderItem = findOne(id);

        if (!orderItem.getStatus().equals(OrderItemStatus.AWAITING_APPROVAL))
            throw new BadRequestException("Order item can change status to FOR_PREPARATION only if current status is AWAITING_APPROVAL");

        orderItem.setStatus(OrderItemStatus.FOR_PREPARATION);
        save(orderItem);

        if (orderItem.getMenuItem().getItem() instanceof Food)
            socketService.notifyFormMe("ALL", String.format("New Item %s is requested for preparation", orderItem.getMenuItem().getItem().getName()), "COOK");
        else
            socketService.notifyFormMe("ALL", String.format("New Item %s is requested for preparation", orderItem.getMenuItem().getItem().getName()), "BARTENDER");

        return orderItem;
    }

    @Transactional
    @Override
    public OrderItem cancelOrderItem(Long id) {
        OrderItem orderItem = findOne(id);

        if (!orderItem.getStatus().equals(OrderItemStatus.FOR_PREPARATION))
            throw new BadRequestException("Invalid current order item status for cancellation");

        orderItem.setStatus(OrderItemStatus.CANCELED);
        save(orderItem);

        if (orderItem.getMenuItem().getItem() instanceof Food)
            socketService.notifyFormMe("HIDDEN", "Order Item Changed|ORDER_ITEM|" + orderItem.getId(), "COOK");
        else
            socketService.notifyFormMe("HIDDEN", "Order Item Changed|ORDER_ITEM|" + orderItem.getId(), "BARTENDER");

        return orderItem;
    }

    @Transactional
    @Override
    public OrderItem takeOrderItem(Long id) {
        OrderItem orderItem = findOne(id);

        if (!orderItem.getStatus().equals(OrderItemStatus.FOR_PREPARATION))
            throw new BadRequestException("Invalid current order item status for item to be taken");

        String username = authorityService.getCurrentUsername();
        orderItem.setWorker(userService.findByUsernameWithAuthorities(username));
        orderItem.setStatus(OrderItemStatus.IN_PROGRESS);
        save(orderItem);

        Order order = orderItem.getOrder();
        if (!order.getStatus().equals(OrderStatus.IN_PROGRESS)) {
            order.setStatus(OrderStatus.IN_PROGRESS);
            orderService.save(order);
        }

        if (orderItem.getMenuItem().getItem() instanceof Food)
            socketService.notifyFormMe("HIDDEN", "Order Item Changed|ORDER_ITEM|" + orderItem.getId(), "COOK");
        else
            socketService.notifyFormMe("HIDDEN", "Order Item Changed|ORDER_ITEM|" + orderItem.getId(), "BARTENDER");
        return orderItem;
    }

    @Transactional
    @Override
    public OrderItem markOrderItemAsReady(Long id) {
        OrderItem orderItem = findOne(id);

        if (!orderItem.getStatus().equals(OrderItemStatus.IN_PROGRESS))
            throw new BadRequestException("Invalid current order item status for item to be ready");

        orderItem.setStatus(OrderItemStatus.READY);
        save(orderItem);

        Order order = orderItem.getOrder();
        int readyOrDeliveredItemsSize = (order.getOrderItems().stream().filter(i -> i.getStatus().equals(OrderItemStatus.READY) || i.getStatus().equals(OrderItemStatus.DELIVERED)).collect(Collectors.toSet())).size();
        if (readyOrDeliveredItemsSize == order.getOrderItems().size()) {
            order.setStatus(OrderStatus.READY);
            orderService.save(order);
            socketService.notifyFormMe(orderItem.getOrder().getWaiter().getUsername(), String.format("Order for table %s is ready", order.getTable().getTableNumber()), "WAITER");
        }

        socketService.notifyFormMe(orderItem.getOrder().getWaiter().getUsername(), String.format("Item %s is ready", orderItem.getMenuItem().getItem().getName()), "WAITER");
        return orderItem;
    }

    @Transactional
    @Override
    public OrderItem markOrderItemAsDelivered(Long id) {
        OrderItem orderItem = findOne(id);

        if (!orderItem.getStatus().equals(OrderItemStatus.READY))
            throw new BadRequestException("Invalid current order item status for item to be delivered");

        orderItem.setStatus(OrderItemStatus.DELIVERED);
        save(orderItem);

        Order order = orderItem.getOrder();
        int deliveredItemsSize = (order.getOrderItems().stream().filter(i -> i.getStatus().equals(OrderItemStatus.DELIVERED)).collect(Collectors.toSet())).size();
        if (deliveredItemsSize == order.getOrderItems().size()) {
            order.setStatus(OrderStatus.DELIVERED);
            orderService.save(order);
        }

        socketService.notifyFormMe("HIDDEN", "Order Item Changed|ORDER_ITEM|" + orderItem.getId());
        return orderItem;
    }

    @Override
    @Transactional
    public Collection<Order> findNewOrderItemsGroupedInOrders() {
        Authority authority = new ArrayList<>(userService.findByUsernameWithAuthorities(authorityService.getCurrentUsername()).getAuthorities()).get(0);

        Collection<Order> orders = orderService.findOrdersByStatusForPreparationOrStatusInProgress();

        if (authority.getName().equals("BARTENDER"))
            orders.forEach(o -> o.setOrderItems((o.getOrderItems().stream().filter(item -> item.getStatus().equals(OrderItemStatus.FOR_PREPARATION) && item.getMenuItem().getItem() instanceof Drink)).collect(Collectors.toSet())));
        else if (authority.getName().equals("COOK"))
            orders.forEach(o -> o.setOrderItems((o.getOrderItems().stream().filter(item -> item.getStatus().equals(OrderItemStatus.FOR_PREPARATION) && item.getMenuItem().getItem() instanceof Food)).collect(Collectors.toSet())));

        return orders.stream().filter(o -> o.getOrderItems().size() > 0).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Collection<Order> findAcceptedOrderItemsGroupedInOrders() {
        User user = userService.findByUsernameWithAuthorities(authorityService.getCurrentUsername());
        Authority authority = new ArrayList<>(user.getAuthorities()).get(0);

        Collection<Order> orders = orderService.findOrdersByStatusStatusInProgress();

        orders.forEach(o -> System.out.println(o.getId()));

        if (authority.getName().equals("BARTENDER")) {
            orders.forEach(o -> {
                o.setOrderItems((o.getOrderItems().stream().filter(item ->
                        (item.getWorker() != null && item.getWorker().getId().equals(user.getId())) &&
                        item.getStatus().equals(OrderItemStatus.IN_PROGRESS) &&
                        item.getMenuItem().getItem() instanceof Drink)
                ).collect(Collectors.toSet()));
            });
        }

        else if (authority.getName().equals("COOK")) {
            orders.forEach(o -> {
                o.setOrderItems((o.getOrderItems().stream().filter(item ->
                        (item.getWorker() != null && item.getWorker().getId().equals(user.getId())) &&
                        item.getStatus().equals(OrderItemStatus.IN_PROGRESS) &&
                        item.getMenuItem().getItem() instanceof Food)
                ).collect(Collectors.toSet()));
            });
        }

        return orders.stream().filter(o -> o.getOrderItems().size() > 0).collect(Collectors.toList());
    }

    @Override
    public Collection<OrderItem> findOrderFinished() {
        return orderItemRepository.findDeliveredAndOrderFinished();
    }

    @Override
    public Double getProfit() {
        Collection<OrderItem> orderItems = orderItemRepository.findDeliveredAndOrderFinished();
        double profit = 0.0;
        for(OrderItem oi : orderItems) {
            profit += (oi.getCurrentPrice() - oi.getCurrentExpense()) * oi.getAmount();
        }
        return profit;
    }

    @Override
    public Collection<Collection<Collection<Double>>> getIncomesAndExpensesForNDays(Integer days) {
        Date date = new Date();
        List<Collection<Collection<Double>>> ret = new ArrayList<>();
        for(int i = days; i >= 1; i--) {
            Collection<OrderItem> orderItems = orderItemRepository.findAllInRange(date.getTime()-(86400000L * i), date.getTime()-(86400000L * (i-1)));

            List<Collection<Double>> temp = new ArrayList<>();
            for (OrderItem item: orderItems){
                if(item.getStatus().name().equals("DELIVERED")) {
                    temp.add(new ArrayList<>() {{ add(item.getCurrentPrice() * item.getAmount()); add(item.getCurrentExpense() * item.getAmount()); }});
                }
            }
            ret.add(temp);
        }
        return ret;
    }

    @Override
    public void delete(Long id) {
        OrderItem orderItem = findOne(id);

        Order order = orderItem.getOrder();
        order.getOrderItems().removeIf(item -> item.getId().equals(id));

        orderItemRepository.delete(orderItem);
        orderService.save(order);
    }
}
