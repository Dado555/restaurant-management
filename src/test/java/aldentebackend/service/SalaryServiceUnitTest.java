package aldentebackend.service;

import aldentebackend.constants.SalaryConstants;
import aldentebackend.exception.BadRequestException;
import aldentebackend.model.Salary;
import aldentebackend.model.Waiter;
import aldentebackend.repository.SalaryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SalaryServiceUnitTest {

    @Autowired
    private SalaryService salaryService;

    @MockBean
    private SalaryRepository salaryRepository;

    @Test
    public void getUserCurrentSalary_calledWithInvalidId_throwsBadRequestException() {
        Salary salary = new Salary();

        doReturn(salary).when(salaryRepository).findFirstByUserIdAndDateLessThanEqualOrderByDateDesc(
                SalaryConstants.USER_ID, SalaryConstants.EPOCH);

        assertThrows(BadRequestException.class, ()-> salaryService.getUserCurrentSalary(SalaryConstants.USER_ID));
    }

    @Test
    public void getUserCurrentSalary_calledWithValidId_isSuccess() {
        Salary salary = new Salary(SalaryConstants.VALUE, SalaryConstants.DATE);
        Waiter waiter = new Waiter();
        waiter.setId(1L);
        salary.setUser(waiter);

        doReturn(salary).when(salaryRepository).findFirstByUserIdAndDateLessThanEqualOrderByDateDesc(
                SalaryConstants.USER_ID, SalaryConstants.EPOCH);

        assertThat(salaryService.getUserCurrentSalary(SalaryConstants.USER_ID).getValue()).isEqualTo(SalaryConstants.VALUE);
    }

    @Test
    public void getUserSalaryAt_calledWithInvalidId_throwsBadRequestException() {
        Salary salary = new Salary();

        doReturn(salary).when(salaryRepository).findFirstByUserIdAndDateLessThanEqualOrderByDateDesc(
                SalaryConstants.USER_ID, SalaryConstants.EPOCH);

        assertThrows(BadRequestException.class, ()-> salaryService.getUserCurrentSalary(SalaryConstants.USER_ID));
    }

    @Test
    public void getUserSalaryAt_calledWithValidId_isSuccess() {
        Salary salary = new Salary(SalaryConstants.VALUE, SalaryConstants.DATE);
        Waiter waiter = new Waiter();
        waiter.setId(1L);
        salary.setUser(waiter);

        doReturn(salary).when(salaryRepository).findFirstByUserIdAndDateLessThanEqualOrderByDateDesc(
                SalaryConstants.USER_ID, SalaryConstants.EPOCH);

        assertThat(salaryService.getUserSalaryAt(SalaryConstants.USER_ID, SalaryConstants.EPOCH)
                .getValue()).isEqualTo(SalaryConstants.VALUE);
    }

    @Test
    public void getAllCurrentSalaries_called_isSuccess() {
        doReturn(new ArrayList<Salary>()).when(salaryRepository).getUsersSalariesAt(SalaryConstants.EPOCH);

        assertThat(salaryService.getAllCurrentSalaries()).isNotNull();
    }

    @Test
    public void getAllSalariesAt_calledWithValidLong_isSuccess() {
        doReturn(new ArrayList<Salary>()).when(salaryRepository).getUsersSalariesAt(SalaryConstants.EPOCH);

        assertThat(salaryService.getAllCurrentSalaries()).isNotNull();
    }
}
