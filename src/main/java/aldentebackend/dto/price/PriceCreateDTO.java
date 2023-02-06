package aldentebackend.dto.price;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PriceCreateDTO {

    @NotNull
    private Double price;

    @NotNull
    private Double expense;
}
