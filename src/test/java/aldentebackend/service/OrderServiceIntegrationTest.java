package aldentebackend.service;

import aldentebackend.constants.OrderConstants;
import aldentebackend.constants.TablesConstants;
import aldentebackend.exception.NotFoundException;
import aldentebackend.model.Order;
import aldentebackend.model.enums.OrderItemStatus;
import aldentebackend.model.enums.OrderStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OrderServiceIntegrationTest {

    @Autowired
    private OrderService orderService;


    @Test
    public void findOrderItemsForOrder_calledWithNonExistingId_throwsNotFoundException() {
        assertThrows(NotFoundException.class, () -> orderService.findOrderItemsForOrder(-1L));
    }

    @Test
    public void findOrderItemsForOrder_calledWithValidData_throwsNotFoundException() {
        assertEquals(orderService.findOrderItemsForOrder(OrderConstants.ID).size(), 2);
    }

    @Test
    public void findOrdersForTable_calledWithNonExistingId_returnsEmptyList() {
        assertEquals(orderService.findOrdersForTable(-1L).size(), 0);
    }

    @Test
    public void findOrdersForTable_calledWithMoreThenZeroOrders_isSuccess() {
        assertEquals(orderService.findOrdersForTable(TablesConstants.TABLE_ID).size(), 1);
    }

    @Test
    public void checkoutOrder_calledWithNonExistingId_throwsNotFoundException() {
        assertThrows(NotFoundException.class, () -> orderService.checkoutOrder(-1L));
    }

    @Test
    public void checkoutOrder_calledWithValidId_isSuccess() {

        Order order = orderService.findOne(OrderConstants.CHECKOUT_ORDER_ID);
        order.setStatus(OrderStatus.DELIVERED);

        order = orderService.checkoutOrder(OrderConstants.CHECKOUT_ORDER_ID);

        assertEquals(order.getStatus(), OrderStatus.FINISHED);
    }
}
