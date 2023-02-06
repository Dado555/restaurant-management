package aldentebackend.service;

import aldentebackend.constants.SalaryConstants;
import aldentebackend.model.Salary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SalaryServiceIntegrationTest {

    @Autowired
    private SalaryService salaryService;

    @ParameterizedTest
    @ValueSource(longs = {SalaryConstants.BIG, SalaryConstants.SMALL})
    public void getUserCurrentSalary_calledWithInvalidId_throwsNullPointerException(Long userId) {
        assertThrows(NullPointerException.class, () -> salaryService.getUserCurrentSalary(userId));
    }

    @Test
    public void getUserCurrentSalary_calledWithNullId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> salaryService.getUserCurrentSalary(null));
    }

    @Test
    public void getUserCurrentSalary_calledWithValidId_isSuccess() {
        Salary salary = salaryService.getUserCurrentSalary(SalaryConstants.VALID_USER_ID);

        assertThat(salary.getValue()).isEqualTo(SalaryConstants.VALID_VALUE);
        assertThat(salary.getUser().getId()).isEqualTo(SalaryConstants.VALID_USER_ID);
        assertThat(salary.getId()).isEqualTo(SalaryConstants.VALID_SALARY);
    }

    @ParameterizedTest
    @ValueSource(longs = {SalaryConstants.BIG, SalaryConstants.SMALL})
    public void getUserSalaryAt_calledWithInvalidId_throwsNullPointerException(Long userId) {
        assertThrows(NullPointerException.class, () -> salaryService.getUserSalaryAt(userId, SalaryConstants.VALID_EPOCH));
    }

    @ParameterizedTest
    @ValueSource(longs = {SalaryConstants.INVALID_EPOCH_BIG, SalaryConstants.INVALID_EPOCH_SMALL})
    public void getUserSalaryAt_calledWithInvalidEpoch_throwsNullPointerException(Long epoch) {
        assertThrows(NullPointerException.class, () -> salaryService.getUserSalaryAt(SalaryConstants.VALID_USER_ID, epoch));
    }

    @ParameterizedTest
    @MethodSource("provideParameters")
    public void getUserSalaryAt_calledWithInvalidIdAndInvalidEpoch_throwsNullPointerException(Long id, Long epoch) {
        assertThrows(NullPointerException.class, () -> salaryService.getUserSalaryAt(id, epoch));
    }

    private static Stream<Arguments> provideParameters() {
        return Stream.of(
                Arguments.of(SalaryConstants.BIG, SalaryConstants.INVALID_EPOCH_BIG),
                Arguments.of(SalaryConstants.BIG, SalaryConstants.INVALID_EPOCH_SMALL),
                Arguments.of(SalaryConstants.SMALL, SalaryConstants.INVALID_EPOCH_SMALL),
                Arguments.of(SalaryConstants.SMALL, SalaryConstants.INVALID_EPOCH_BIG)
        );
    }

    @Test
    public void getUserSalaryAt_calledWithNullId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> salaryService.getUserSalaryAt(null, SalaryConstants.VALID_EPOCH));
    }

    @Test
    public void getUserSalaryAt_calledWithNullEpoch_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> salaryService.getUserSalaryAt(SalaryConstants.VALID_USER_ID, null));
    }

    @Test
    public void getUserSalaryAt_calledWithNullIdAndNullEpoch_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> salaryService.getUserSalaryAt(null, null));
    }

    @Test
    public void getUserSalaryAt_calledWithValidIdAndValidEpoch_isSuccess() {
        Salary salary = salaryService.getUserSalaryAt(SalaryConstants.VALID_USER_ID, SalaryConstants.VALID_EPOCH);

        assertThat(salary.getValue()).isEqualTo(SalaryConstants.VALID_VALUE_AT);
        assertThat(salary.getUser().getId()).isEqualTo(SalaryConstants.VALID_USER_ID);
        assertThat(salary.getId()).isEqualTo(SalaryConstants.VALID_SALARY_AT);
    }

    @Test
    public void getAllCurrentSalaries_calledWith_isSuccess() {
        List<Salary> salaries = (List<Salary>) salaryService.getAllCurrentSalaries();

        assertThat(salaries.size()).isEqualTo(2);
        assertThat(salaries.get(0).getId()).isEqualTo(SalaryConstants.VALID_SALARY);
        assertThat(salaries.get(0).getValue()).isEqualTo(SalaryConstants.VALID_VALUE);
        assertThat(salaries.get(0).getDate()).isEqualTo(SalaryConstants.VALID_DATE_AT);
        assertThat(salaries.get(0).getUser().getId()).isEqualTo(SalaryConstants.VALID_USER_ID);

        assertThat(salaries.get(1).getId()).isEqualTo(SalaryConstants.PRICE_ID_2ND);
    }

    @Test
    public void getAllSalariesAt_calledWithInvalidEpoch_isSuccessWithEmptyCollection() {
        Collection<Salary> salaries = salaryService.getAllSalariesAt(SalaryConstants.INVALID_EPOCH_SMALL);

        assertThat(salaries.size()).isEqualTo(0);
    }

    @Test
    public void getAllSalariesAt_calledWithValidEpoch_isSuccess() {
        List<Salary> salaries = (List<Salary>) salaryService.getAllSalariesAt(SalaryConstants.VALID_EPOCH);

        assertThat(salaries.size()).isEqualTo(2);
        assertThat(salaries.get(0).getId()).isEqualTo(SalaryConstants.VALID_SALARY_AT);
        assertThat(salaries.get(0).getValue()).isEqualTo(SalaryConstants.VALID_VALUE_AT);
        assertThat(salaries.get(0).getDate()).isEqualTo(1637227604L);
        assertThat(salaries.get(0).getUser().getId()).isEqualTo(SalaryConstants.VALID_USER_ID);

        assertThat(salaries.get(1).getId()).isEqualTo(SalaryConstants.PRICE_ID_EPOCH);
    }
}
