package aldentebackend.dto.price;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @Getter @Setter
public class PriceUpdateDTO {

    private Long id;
    private Double price;
    private Double expense;
}
