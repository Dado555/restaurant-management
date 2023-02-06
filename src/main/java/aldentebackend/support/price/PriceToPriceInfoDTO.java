package aldentebackend.support.price;

import aldentebackend.dto.price.PriceInfoDTO;
import aldentebackend.model.Price;
import aldentebackend.support.AbstractConverter;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class PriceToPriceInfoDTO extends AbstractConverter<Price, PriceInfoDTO> {

    @Override
    public PriceInfoDTO convert(@NonNull Price source) {
        PriceInfoDTO priceInfoDTO = new PriceInfoDTO(source.getValue());
        priceInfoDTO.setExpense(source.getExpense());
        priceInfoDTO.setDate(source.getDate());
        return priceInfoDTO;
    }
}
