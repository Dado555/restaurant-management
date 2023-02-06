package aldentebackend.service;

import aldentebackend.constants.OrderItemConstants;
import aldentebackend.exception.BadRequestException;
import aldentebackend.model.OrderItem;
import aldentebackend.model.enums.OrderItemStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OrderItemServiceIntegrationTest {

    @Autowired
    private OrderItemService orderItemService;

    @Test
    public void updateOrderItem_calledWithNegativeAmount_throwsBadRequestException() {

        assertThrows(BadRequestException.class, () -> orderItemService.update(OrderItemConstants.ID, -10, null));
    }

    @Test
    public void updateOrderItem_calledWithValidData_isSuccess() {

        OrderItem updatedItem = orderItemService.update(OrderItemConstants.ID, OrderItemConstants.AMOUNT + 20, OrderItemConstants.DESCRIPTION + "updated");

        assertThat(updatedItem.getAmount()).isEqualTo(OrderItemConstants.AMOUNT + 20);
        assertThat(updatedItem.getDescription()).isEqualTo(OrderItemConstants.DESCRIPTION + "updated");
    }

}
