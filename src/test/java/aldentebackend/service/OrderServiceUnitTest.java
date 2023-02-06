package aldentebackend.service;

import aldentebackend.constants.OrderConstants;
import aldentebackend.exception.NotFoundException;
import aldentebackend.model.Order;
import aldentebackend.model.OrderItem;
import aldentebackend.repository.OrderRepository;
import aldentebackend.service.socket.SocketService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OrderServiceUnitTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private SocketService socketService;

    @Test
    public void findOrderItemsForOrder_calledWithInvalidId_throwsNotFoundException() {

        doReturn(Optional.empty()).when(orderRepository).findById(OrderConstants.ID);

        assertThrows(NotFoundException.class, () -> orderService.findOrderItemsForOrder(OrderConstants.ID));
    }

    @Test
    public void findOrderItemsForOrder_calledWithValidId_isSuccess() {
        Order order = new Order();
        order.setId(OrderConstants.ID);

        Set<OrderItem> orderItems = new HashSet<>();
        orderItems.add(new OrderItem());
        orderItems.add(new OrderItem());
        orderItems.add(new OrderItem());

        order.setOrderItems(orderItems);

        doReturn(Optional.of(order)).when(orderRepository).findById(OrderConstants.ID);

        assertThat(order.getOrderItems().size()).isEqualTo(3);
    }

    @Test
    public void getIncomeStatistic_calledWithEmptyList_isSuccess() {

        doReturn(new ArrayList<Order>()).when(orderRepository).getOrdersByDateAfterAndDateBefore(OrderConstants.DATE_FROM, OrderConstants.DATE_TO);

        Map<String, Object> retVal = orderService.getIncomeAndExpensesStatistic(OrderConstants.DATE_FROM, OrderConstants.DATE_TO);
        assertThat(retVal.get("total")).isEqualTo(0D);
    }

    @Test
    public void getIncomeStatistic_calledWithValidData_isSuccess() {
        OrderItem item1 = new OrderItem();
        item1.setCurrentPrice(OrderConstants.CURRENT_PRICE1);
        item1.setCurrentExpense(OrderConstants.CURRENT_PRICE1);

        OrderItem item2 = new OrderItem();
        item2.setCurrentPrice(OrderConstants.CURRENT_PRICE2);
        item2.setCurrentExpense(OrderConstants.CURRENT_PRICE2);

        OrderItem item3 = new OrderItem();
        item3.setCurrentPrice(OrderConstants.CURRENT_PRICE3);
        item3.setCurrentExpense(OrderConstants.CURRENT_PRICE3);

        OrderItem item4 = new OrderItem();
        item4.setCurrentPrice(OrderConstants.CURRENT_PRICE4);
        item4.setCurrentExpense(OrderConstants.CURRENT_PRICE4);

        Order order1 = new Order();
        order1.getOrderItems().add(item1);
        order1.getOrderItems().add(item2);

        Order order2 = new Order();
        order2.getOrderItems().add(item3);

        Order order3 = new Order();
        order3.getOrderItems().add(item4);

        List<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);

        doReturn(orders).when(orderRepository).getOrdersByDateAfterAndDateBefore(OrderConstants.DATE_FROM, OrderConstants.DATE_TO);

        Map<String, Object> retVal = orderService.getIncomeAndExpensesStatistic(OrderConstants.DATE_FROM, OrderConstants.DATE_TO);
        assertThat(retVal.get("total")).isEqualTo(OrderConstants.CURRENT_PRICE1 + OrderConstants.CURRENT_PRICE2 + OrderConstants.CURRENT_PRICE3 + OrderConstants.CURRENT_PRICE4);
    }
}
