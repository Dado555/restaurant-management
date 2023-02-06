package aldentebackend.repository;

import aldentebackend.constants.SalaryConstants;
import aldentebackend.model.Salary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class SalaryRepositoryTest {
    @Autowired
    private SalaryRepository salaryRepository;

    @ParameterizedTest
    @MethodSource("invalidData")
    public void findFirstByUserIdAndDateLessThanEqualOrderByDateDesc_calledWithInvalidIdOrDate_returnsNullSalary(
            Long userId, Long dateMillis) {
        Salary salary = salaryRepository.findFirstByUserIdAndDateLessThanEqualOrderByDateDesc(userId, dateMillis);

        assertNull(salary);
    }

    private static Stream<Arguments> invalidData() {
        return Stream.of(
                Arguments.of(null, SalaryConstants.VALID_EPOCH),
                Arguments.of(SalaryConstants.USER_ID, SalaryConstants.VALID_EPOCH),
                Arguments.of(SalaryConstants.VALID_USER_ID, SalaryConstants.INVALID_EPOCH_SMALL)
        );
    }

    @Test
    public void findFirstByUserIdAndDateLessThanEqualOrderByDateDesc_calledWithInvalidDate_throwsInvalidDataAccessApiUsageException() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> salaryRepository.findFirstByUserIdAndDateLessThanEqualOrderByDateDesc(
                SalaryConstants.VALID_USER_ID, null));
    }

    @Test
    public void findFirstByMenuItemIdAndDateLessThanEqualOrderByDateDesc_calledWithValidData_isSuccess() {
        Salary salary = salaryRepository.findFirstByUserIdAndDateLessThanEqualOrderByDateDesc(
                SalaryConstants.VALID_USER_ID, SalaryConstants.VALID_EPOCH
        );

        assertNotNull(salary);
        assertEquals(SalaryConstants.VALID_VALUE_AT, salary.getValue());
        assertEquals(SalaryConstants.VALID_SALARY_AT, salary.getId());
    }

    @ParameterizedTest
    @MethodSource("invalidDates")
    public void getUsersSalariesAt_calledWithInvalidDate_returnsEmptyCollection(Long epoch) {
        Collection<Salary> salaries = salaryRepository.getUsersSalariesAt(epoch);

        assertEquals(salaries.size(), 0);
    }

    private static Stream<Long> invalidDates() {
        return Stream.of(null, -651135168489L);
    }

    @Test
    public void getUsersSalariesAt_calledWithValidDate_isSuccess() {
        Collection<Salary> salaries = salaryRepository.getUsersSalariesAt(SalaryConstants.VALID_DATE_AT);

        assertEquals(salaries.size(), 2);
    }
}
