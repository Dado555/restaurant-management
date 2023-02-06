package aldentebackend.support.food;

import aldentebackend.dto.item.FoodUpdateDTO;
import aldentebackend.model.Food;
import aldentebackend.support.AbstractConverter;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class FoodUpdateDTOToFood extends AbstractConverter<FoodUpdateDTO, Food> {

    @Override
    public Food convert(@NonNull FoodUpdateDTO source) {
        return getModelMapper().map(source, Food.class);
    }
}
