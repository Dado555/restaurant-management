package aldentebackend.dto.salary;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor @Getter @Setter
public class SalaryInfoDTO {
    @NotNull
    private Double value;

    @NotNull
    private Long date;
}
