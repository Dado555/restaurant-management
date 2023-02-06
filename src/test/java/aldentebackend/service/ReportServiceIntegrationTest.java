package aldentebackend.service;

import aldentebackend.constants.ReportConstants;
import aldentebackend.exception.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ReportServiceIntegrationTest {

    @Autowired
    private ReportService reportService;

    @Test
    public void getReportForEveryItem_calledWithValidDates_isSuccess() {
        Map<Long, Map<String, Object>> map = reportService.getReportForEveryItem(ReportConstants.START, ReportConstants.END);

        System.out.println(map);
        assertThat(Arrays.asList(map.keySet().toArray())).contains(ReportConstants.ITEM_10023L);
        assertThat(map.get(ReportConstants.ITEM_10023L).get("income")).isEqualTo(ReportConstants.ITEM_10023L_INCOME);
        assertThat(map.get(ReportConstants.ITEM_10023L).get("expense")).isEqualTo(ReportConstants.ITEM_10023L_EXPENSE);
    }

    @Test
    public void getReportForEveryItem_calledWithValidDates_isSuccess_returnsEmpty() {
        Map<Long, Map<String, Object>> map = reportService.getReportForEveryItem(ReportConstants.START_EMPTY,
                                                                                 ReportConstants.END_EMPTY);

        assertThat(map.size()).isEqualTo(0);
    }

    @ParameterizedTest
    @MethodSource("provideParameters")
    public void getReportForEveryItem_calledWithInvalidStartAndInvalidEnd_throwsBadRequestException(LocalDate start, LocalDate end) {
        assertThrows(BadRequestException.class, () -> reportService.getReportForEveryItem(start, end));
    }

    private static Stream<Arguments> provideParameters() {
        return Stream.of(
                Arguments.of(ReportConstants.START_INVALID, ReportConstants.END),
                Arguments.of(ReportConstants.START, ReportConstants.END_INVALID),
                Arguments.of(null, ReportConstants.END),
                Arguments.of(ReportConstants.START, null),
                Arguments.of(null, null),
                Arguments.of(ReportConstants.START_INVALID, ReportConstants.END_INVALID)
        );
    }
}
