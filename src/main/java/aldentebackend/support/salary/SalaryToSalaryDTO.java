package aldentebackend.support.salary;

import aldentebackend.dto.salary.SalaryInfoDTO;
import aldentebackend.model.Salary;
import aldentebackend.support.AbstractConverter;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SalaryToSalaryDTO extends AbstractConverter<Salary, SalaryInfoDTO> {

    @Override
    public SalaryInfoDTO convert(@NonNull Salary source) {
        return getModelMapper().map(source, SalaryInfoDTO.class);
    }
}
