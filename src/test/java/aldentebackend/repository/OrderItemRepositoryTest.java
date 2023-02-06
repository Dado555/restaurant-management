package aldentebackend.repository;

import aldentebackend.constants.OrderItemConstants;
import aldentebackend.model.OrderItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class OrderItemRepositoryTest {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @ParameterizedTest
    @MethodSource("invalidDateRange")
    public void findAllInRange_calledWithInvalidRange_returnsEmptyCollection(long start, long end) {
        Collection<OrderItem> orderItems = orderItemRepository.findAllInRange(start, end);

        assertEquals(orderItems.size(), 0);
    }

    private static Stream<Arguments> invalidDateRange() {
        return Stream.of(
                Arguments.of(OrderItemConstants.DATE2, OrderItemConstants.DATE1),
                Arguments.of(OrderItemConstants.DATE4, OrderItemConstants.DATE2)
        );
    }

    @Test
    public void findAllInRange_calledWithValidRange_isSuccess() {
        Collection<OrderItem> orderItems = orderItemRepository.findAllInRange(OrderItemConstants.DATE2, OrderItemConstants.DATE3);

        assertEquals(orderItems.size(), OrderItemConstants.ALL_DATE3_DATE4);
    }
}
