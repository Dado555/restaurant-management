package aldentebackend.dto.price;

import lombok.Data;

@Data
public class PriceInfoDTO {

    private Long id;
    private Double value;
    private Long date;
    private Double expense;

    public PriceInfoDTO(Double value) {
        this.value = value;
    }
}
