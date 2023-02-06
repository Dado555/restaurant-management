package aldentebackend.support.drink;

import aldentebackend.dto.item.DrinkInfoDTO;
import aldentebackend.model.Drink;
import aldentebackend.support.AbstractConverter;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class DrinkToDrinkInfoDTO extends AbstractConverter<Drink, DrinkInfoDTO> {


    @Override
    public DrinkInfoDTO convert(@NonNull Drink source) {
        return getModelMapper().map(source, DrinkInfoDTO.class);
    }
}
