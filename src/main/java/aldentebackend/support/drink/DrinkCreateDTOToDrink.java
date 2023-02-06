package aldentebackend.support.drink;

import aldentebackend.dto.item.DrinkCreateDTO;
import aldentebackend.model.Drink;
import aldentebackend.support.AbstractConverter;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class DrinkCreateDTOToDrink extends AbstractConverter<DrinkCreateDTO, Drink> {

    @Override
    public Drink convert(@NonNull DrinkCreateDTO source) {
        return getModelMapper().map(source, Drink.class);
    }
}
