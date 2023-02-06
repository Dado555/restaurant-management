package aldentebackend.support.drink;

import aldentebackend.dto.item.DrinkInfoDTO;
import aldentebackend.dto.item.DrinkUpdateDTO;
import aldentebackend.model.Drink;
import aldentebackend.support.AbstractConverter;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class DrinkUpdateDTOToDrink extends AbstractConverter<DrinkUpdateDTO, Drink> {

    @Override
    public Drink convert(@NonNull DrinkUpdateDTO source) {
        return getModelMapper().map(source, Drink.class);
    }
}
