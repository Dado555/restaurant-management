package aldentebackend.service;

import aldentebackend.constants.OrderItemConstants;
import aldentebackend.exception.BadRequestException;
import aldentebackend.model.*;
import aldentebackend.model.enums.OrderItemStatus;
import aldentebackend.model.enums.OrderStatus;
import aldentebackend.repository.OrderItemRepository;
import aldentebackend.repository.UserRepository;
import aldentebackend.service.socket.SocketService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OrderItemServiceUnitTest {

    @Autowired
    private OrderItemService orderItemService;

    @MockBean
    private OrderItemRepository orderItemRepositoryMock;

    @MockBean
    private SocketService socketService;

    @MockBean
    private OrderService orderService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AuthorityService authorityService;

    @Test
    public void updateOrder_calledWithValidData_isSuccess() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(OrderItemConstants.ID);
        orderItem.setAmount(OrderItemConstants.AMOUNT);
        orderItem.setDescription(OrderItemConstants.DESCRIPTION);

        doReturn(Optional.of(orderItem)).when(orderItemRepositoryMock).findById(OrderItemConstants.ID);
        doReturn(orderItem).when(orderItemRepositoryMock).save(orderItem);
        doNothing().when(socketService).notifyFormMe("ALL", "message");

        orderItemService.update(OrderItemConstants.ID, OrderItemConstants.AMOUNT + 10, OrderItemConstants.DESCRIPTION + "new");

        assertThat(orderItem.getAmount()).isEqualTo(OrderItemConstants.AMOUNT + 10);
        assertThat(orderItem.getDescription()).isEqualTo(OrderItemConstants.DESCRIPTION + "new");
    }

    @ParameterizedTest
    @EnumSource(value = OrderItemStatus.class, names = {"AWAITING_APPROVAL", "CANCELED", "IN_PROGRESS", "READY", "DELIVERED"})
    public void markOrderItemAsAwaitingApproval_calledWithInvalidData_throwsBadRequest(OrderItemStatus status) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(OrderItemConstants.ID);
        orderItem.setStatus(status);

        doReturn(Optional.of(orderItem)).when(orderItemRepositoryMock).findById(OrderItemConstants.ID);
        doNothing().when(socketService).notifyFormMe("ALL", "message");

        assertThrows(BadRequestException.class, () -> orderItemService.markOrderItemAsAwaitingApproval(OrderItemConstants.ID));
    }

    @Test
    public void markOrderItemAsAwaitingApproval_calledWithValidData_isSuccess() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(OrderItemConstants.ID);
        orderItem.setStatus(OrderItemStatus.FOR_PREPARATION);

        doReturn(Optional.of(orderItem)).when(orderItemRepositoryMock).findById(OrderItemConstants.ID);
        doNothing().when(socketService).notifyFormMe("ALL", "message");

        orderItemService.markOrderItemAsAwaitingApproval(OrderItemConstants.ID);

        assertThat(orderItem.getId()).isEqualTo(OrderItemConstants.ID);
        assertThat(orderItem.getStatus()).isEqualTo(OrderItemStatus.AWAITING_APPROVAL);
    }

    @ParameterizedTest
    @EnumSource(value = OrderItemStatus.class, names = {"FOR_PREPARATION", "CANCELED", "IN_PROGRESS", "READY", "DELIVERED"})
    public void markOrderItemAsForPreparation_calledWithInvalidData_throwsBadRequest(OrderItemStatus status) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(OrderItemConstants.ID);
        orderItem.setStatus(status);

        doReturn(Optional.of(orderItem)).when(orderItemRepositoryMock).findById(OrderItemConstants.ID);
        doNothing().when(socketService).notifyFormMe("ALL", "message");

        assertThrows(BadRequestException.class, () -> orderItemService.markOrderItemAsForPreparation(OrderItemConstants.ID));
    }

    @Test
    public void markOrderItemAsForPreparation_calledWithValidData_isSuccess() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(OrderItemConstants.ID);
        orderItem.setStatus(OrderItemStatus.AWAITING_APPROVAL);

        MenuItem menuItem = new MenuItem();
        Food food = new Food();
        food.setName("hrana");
        menuItem.setItem(food);

        orderItem.setMenuItem(menuItem);

        doReturn(Optional.of(orderItem)).when(orderItemRepositoryMock).findById(OrderItemConstants.ID);
        doNothing().when(socketService).notifyFormMe("ALL", String.format("New Item %s is requested for preparation", orderItem.getMenuItem().getItem().getName()), "COOK");

        orderItemService.markOrderItemAsForPreparation(OrderItemConstants.ID);

        assertThat(orderItem.getId()).isEqualTo(OrderItemConstants.ID);
        assertThat(orderItem.getStatus()).isEqualTo(OrderItemStatus.FOR_PREPARATION);
    }

    @ParameterizedTest
    @EnumSource(value = OrderItemStatus.class, names = {"AWAITING_APPROVAL", "CANCELED", "IN_PROGRESS","READY", "DELIVERED"})
    public void cancelOrderItem_calledWithInvalidData_throwsBadRequest(OrderItemStatus status) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(OrderItemConstants.ID);
        orderItem.setStatus(status);

        doReturn(Optional.of(orderItem)).when(orderItemRepositoryMock).findById(OrderItemConstants.ID);
        doNothing().when(socketService).notifyFormMe("ALL", "message");

        assertThrows(BadRequestException.class, () -> orderItemService.cancelOrderItem(OrderItemConstants.ID));
    }

    @Test
    public void cancelOrderItem_calledWithValidData_isSuccess() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(OrderItemConstants.ID);
        orderItem.setStatus(OrderItemStatus.FOR_PREPARATION);

        MenuItem menuItem = new MenuItem();
        menuItem.setItem(new Food());
        orderItem.setMenuItem(menuItem);

        doReturn(Optional.of(orderItem)).when(orderItemRepositoryMock).findById(OrderItemConstants.ID);
        doNothing().when(socketService).notifyFormMe("ALL", "message");

        orderItemService.cancelOrderItem(OrderItemConstants.ID);

        assertThat(orderItem.getId()).isEqualTo(OrderItemConstants.ID);
        assertThat(orderItem.getStatus()).isEqualTo(OrderItemStatus.CANCELED);
    }

    @ParameterizedTest
    @EnumSource(value = OrderItemStatus.class, names = {"AWAITING_APPROVAL", "CANCELED", "IN_PROGRESS","READY", "DELIVERED"})
    public void takeOrderItem_calledWithInvalidData_throwsBadRequest(OrderItemStatus status) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(OrderItemConstants.ID);
        orderItem.setStatus(status);

        doReturn(Optional.of(orderItem)).when(orderItemRepositoryMock).findById(OrderItemConstants.ID);
        doNothing().when(socketService).notifyFormMe("ALL", "message");

        assertThrows(BadRequestException.class, () -> orderItemService.takeOrderItem(OrderItemConstants.ID));
    }


    @Test
    public void takeOrderItem_calledWithValidData_isSuccess() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(OrderItemConstants.ID);
        orderItem.setStatus(OrderItemStatus.FOR_PREPARATION);

        MenuItem menuItem = new MenuItem();
        Food food = new Food();
        food.setName("burger");

        menuItem.setItem(food);
        orderItem.setMenuItem(menuItem);

        Order order = new Order();
        order.setStatus(OrderStatus.FOR_PREPARATION);
        order.setOrderItems(new HashSet<>(Collections.singleton(orderItem)));

        orderItem.setOrder(order);

        String username = "username";
        User bartender = new Bartender();
        bartender.setUsername(username);

        doReturn(Optional.of(orderItem)).when(orderItemRepositoryMock).findById(OrderItemConstants.ID);
        doReturn(order).when(orderService).save(order);
        doReturn(username).when(authorityService).getCurrentUsername();

        doReturn(Optional.of(bartender)).when(userRepository).findByEmailFetchAuthorities(username);
        doNothing().when(socketService).notifyFormMe("ALL", String.format("New Item %s is requested for preparation", orderItem.getMenuItem().getItem().getName()), "COOK");

        orderItemService.takeOrderItem(OrderItemConstants.ID);

        assertThat(orderItem.getId()).isEqualTo(OrderItemConstants.ID);
        assertThat(orderItem.getStatus()).isEqualTo(OrderItemStatus.IN_PROGRESS);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.IN_PROGRESS);
        assertThat(orderItem.getWorker().getUsername()).isEqualTo(username);
    }

    @ParameterizedTest
    @EnumSource(value = OrderItemStatus.class, names = {"AWAITING_APPROVAL", "FOR_PREPARATION", "CANCELED","READY", "DELIVERED"})
    public void markOrderItemAsReady_calledWithInvalidData_throwsBadRequest(OrderItemStatus status) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(OrderItemConstants.ID);
        orderItem.setStatus(status);

        doReturn(Optional.of(orderItem)).when(orderItemRepositoryMock).findById(OrderItemConstants.ID);
        doNothing().when(socketService).notifyFormMe("ALL", "message");

        assertThrows(BadRequestException.class, () -> orderItemService.markOrderItemAsReady(OrderItemConstants.ID));
    }

    @Test
    public void markOrderItemAsReady_calledWithValidDataAndWithoutChangingOrderStatus_isSuccess() {
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setId(OrderItemConstants.ID);
        orderItem1.setStatus(OrderItemStatus.IN_PROGRESS);

        MenuItem menuItem = new MenuItem();
        Food food = new Food();
        food.setName("vinjak");
        menuItem.setItem(food);
        orderItem1.setMenuItem(menuItem);

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setId(OrderItemConstants.ID + 1);
        orderItem2.setStatus(OrderItemStatus.FOR_PREPARATION);

        Order order = new Order();
        order.setStatus(OrderStatus.IN_PROGRESS);
        order.setOrderItems(new HashSet<>(Arrays.asList(orderItem1, orderItem2)));

        Waiter waiter = new Waiter();
        waiter.setUsername("marko");
        order.setWaiter(waiter);

        orderItem1.setOrder(order);
        orderItem2.setOrder(order);

        doReturn(Optional.of(orderItem1)).when(orderItemRepositoryMock).findById(OrderItemConstants.ID);
        doReturn(order).when(orderService).save(order);
        doNothing().when(socketService).notifyFormMe("marko", String.format("Item %s is ready", "vinjak"));

        orderItemService.markOrderItemAsReady(OrderItemConstants.ID);

        assertThat(orderItem1.getId()).isEqualTo(OrderItemConstants.ID);
        assertThat(orderItem1.getStatus()).isEqualTo(OrderItemStatus.READY);
        assertThat(orderItem1.getStatus()).isEqualTo(OrderItemStatus.READY);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.IN_PROGRESS);
    }

    @Test
    public void markOrderItemAsReady_calledWithValidDataAndWithChangingOrderStatus_isSuccess() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(OrderItemConstants.ID);
        orderItem.setStatus(OrderItemStatus.IN_PROGRESS);

        MenuItem menuItem = new MenuItem();
        Food food = new Food();
        food.setName("hrana");
        menuItem.setItem(food);

        orderItem.setMenuItem(menuItem);

        Waiter waiter = new Waiter();
        waiter.setUsername("marko");

        Order order = new Order();
        order.setStatus(OrderStatus.IN_PROGRESS);
        order.setWaiter(waiter);
        order.setOrderItems(new HashSet<>(Collections.singleton(orderItem)));

        orderItem.setOrder(order);

        RestaurantTable table = new RestaurantTable();
        table.setTableNumber((short)1);
        order.setTable(table);

        doReturn(Optional.of(orderItem)).when(orderItemRepositoryMock).findById(OrderItemConstants.ID);
        doReturn(order).when(orderService).save(order);
        doNothing().when(socketService).notifyFormMe(orderItem.getOrder().getWaiter().getUsername(), String.format("Order for table %s is ready", order.getTable().getTableNumber()), "WAITER");

        orderItemService.markOrderItemAsReady(OrderItemConstants.ID);

        assertThat(orderItem.getId()).isEqualTo(OrderItemConstants.ID);
        assertThat(orderItem.getStatus()).isEqualTo(OrderItemStatus.READY);
        assertThat(orderItem.getStatus()).isEqualTo(OrderItemStatus.READY);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.READY);
    }

    @ParameterizedTest
    @EnumSource(value = OrderItemStatus.class, names = {"AWAITING_APPROVAL", "FOR_PREPARATION", "CANCELED", "IN_PROGRESS", "DELIVERED"})
    public void markOrderItemAsDelivered_calledWithInvalidData_throwsBadRequest(OrderItemStatus status) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(OrderItemConstants.ID);
        orderItem.setStatus(status);

        doReturn(Optional.of(orderItem)).when(orderItemRepositoryMock).findById(OrderItemConstants.ID);
        doNothing().when(socketService).notifyFormMe("ALL", "message");

        assertThrows(BadRequestException.class, () -> orderItemService.markOrderItemAsDelivered(OrderItemConstants.ID));
    }

    @Test
    public void markOrderItemAsDelivered_calledWithValidDataAndWithOrderStatusReady_isSuccess() {
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setId(OrderItemConstants.ID);
        orderItem1.setStatus(OrderItemStatus.READY);

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setId(OrderItemConstants.ID);
        orderItem2.setStatus(OrderItemStatus.READY);

        Order order = new Order();
        order.setStatus(OrderStatus.READY);
        order.setOrderItems(new HashSet<>(Arrays.asList(orderItem1, orderItem2)));

        orderItem1.setOrder(order);
        orderItem2.setOrder(order);

        doReturn(Optional.of(orderItem1)).when(orderItemRepositoryMock).findById(OrderItemConstants.ID);
        doReturn(order).when(orderService).save(order);
        doNothing().when(socketService).notifyFormMe("ALL", "message");

        orderItemService.markOrderItemAsDelivered(OrderItemConstants.ID);

        assertThat(orderItem1.getId()).isEqualTo(OrderItemConstants.ID);
        assertThat(orderItem1.getStatus()).isEqualTo(OrderItemStatus.DELIVERED);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.READY);
    }

    @Test
    public void markOrderItemAsDelivered_calledWithValidDataAndWithOrderStatusInProgress_isSuccess() {
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setId(OrderItemConstants.ID);
        orderItem1.setStatus(OrderItemStatus.READY);

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setId(OrderItemConstants.ID);
        orderItem2.setStatus(OrderItemStatus.IN_PROGRESS);

        Order order = new Order();
        order.setStatus(OrderStatus.IN_PROGRESS);
        order.setOrderItems(new HashSet<>(Arrays.asList(orderItem1, orderItem2)));

        orderItem1.setOrder(order);
        orderItem2.setOrder(order);

        doReturn(Optional.of(orderItem1)).when(orderItemRepositoryMock).findById(OrderItemConstants.ID);
        doReturn(order).when(orderService).save(order);
        doNothing().when(socketService).notifyFormMe("ALL", "message");

        orderItemService.markOrderItemAsDelivered(OrderItemConstants.ID);

        assertThat(orderItem1.getId()).isEqualTo(OrderItemConstants.ID);
        assertThat(orderItem1.getStatus()).isEqualTo(OrderItemStatus.DELIVERED);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.IN_PROGRESS);
    }
}
