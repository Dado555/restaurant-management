package aldentebackend.service;

import aldentebackend.constants.ReportConstants;
import aldentebackend.exception.BadRequestException;
import aldentebackend.model.*;
import aldentebackend.repository.OrderItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ReportServiceUnitTest {
    @Autowired
    private ReportService reportService;

    @MockBean
    private OrderItemRepository orderItemRepository;

    @Test
    public void getReportForEveryItem_calledWithInvalidDateRange_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> reportService.getReportForEveryItem(ReportConstants.START_LESS, ReportConstants.END_LESS));
    }

    @Test
    public void getReportForEveryItem_calledWithValidDateRange_isSuccess() {
        Collection<OrderItem> items = new ArrayList<>();
        MenuItem menuItem1 = new MenuItem();
        menuItem1.setId(1L);
        Item item1 = new Food();
        item1.setId(1L);
        MenuItemCategory category1 = new MenuItemCategory();
        category1.setId(1L);
        menuItem1.setCategory(category1);
        menuItem1.setItem(item1);
        menuItem1.setPrice(new Price(300.0, 230.0, System.currentTimeMillis()));
        items.add(new OrderItem(null, menuItem1, 100.0, 140.0, null, null, null));

        doReturn(items).when(orderItemRepository).findAllInRange(ReportConstants.START.toEpochSecond(LocalTime.MIN, ZoneOffset.UTC) * 1000,
                ReportConstants.END.toEpochSecond(LocalTime.MIN, ZoneOffset.UTC) * 1000);
        Map<Long, Map<String, Object>> map = reportService.getReportForEveryItem(ReportConstants.START, ReportConstants.END);

        assertThat(map.size()).isEqualTo(1);
        assertThat(map.get(1L).get("income")).isEqualTo(140.0);
        assertThat(map.get(1L).get("expense")).isEqualTo(100.0);
    }

    @Test
    public void getReportForEveryItem_calledWithValidDateRangeAndFindAllInRangeReturnsNull_isSuccess() {
        Collection<OrderItem> items = new ArrayList<>();

        doReturn(items).when(orderItemRepository).findAllInRange(ReportConstants.START.toEpochSecond(LocalTime.MIN, ZoneOffset.UTC),
                ReportConstants.END.toEpochSecond(LocalTime.MIN, ZoneOffset.UTC));
        Map<Long, Map<String, Object>> map = reportService.getReportForEveryItem(ReportConstants.START, ReportConstants.END);

        assertThat(map.size()).isEqualTo(0);
    }
}
