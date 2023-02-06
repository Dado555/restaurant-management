package aldentebackend.repository;

import aldentebackend.constants.OrderConstants;
import aldentebackend.model.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    @ParameterizedTest
    @MethodSource("invalidDates")
    public void getOrdersByDateAfterAndDateBefore_calledWithInvalidDates_returnsEmptyList(Long start, Long end) {
        List<Order> orders = orderRepository.getOrdersByDateAfterAndDateBefore(start, end);

        assertTrue(orders.isEmpty());
    }

    private static Stream<Arguments> invalidDates() {
        return Stream.of(
                Arguments.of(OrderConstants.DATE_FROM, OrderConstants.DATE_TO),
                Arguments.of(OrderConstants.DATE_FROM + OrderConstants.DAY_MILLISECONDS*15,
                             OrderConstants.DATE_TO + OrderConstants.DAY_MILLISECONDS*25),
                Arguments.of(LocalDateTime.of(2021,11,1,14,0).atZone(ZoneId.systemDefault()).toEpochSecond(),
                             LocalDateTime.of(2021, 10, 1, 14,0).atZone(ZoneId.systemDefault()).toEpochSecond()),
                Arguments.of(LocalDateTime.of(1800, 1,1, 14,0).atZone(ZoneId.systemDefault()).toEpochSecond(),
                             LocalDateTime.of(1801,1,1, 14, 0).atZone(ZoneId.systemDefault()).toEpochSecond())
        );
    }

    @Test
    public void getOrdersByDateAfterAndDateBefore_calledWithValidDate_isSuccess() {
        List<Order> orders = orderRepository.getOrdersByDateAfterAndDateBefore(
                LocalDateTime.of(2021,11,1,8,0).atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
                LocalDateTime.of(2022,1,28,20,0).atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);

        assertFalse(orders.isEmpty());
    }
}
