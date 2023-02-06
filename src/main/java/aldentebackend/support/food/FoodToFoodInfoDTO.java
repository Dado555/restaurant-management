package aldentebackend.support.food;

import aldentebackend.dto.item.FoodInfoDTO;
import aldentebackend.model.Food;
import aldentebackend.support.AbstractConverter;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class FoodToFoodInfoDTO extends AbstractConverter<Food, FoodInfoDTO> {

    @Override
    public FoodInfoDTO convert(@NonNull Food source) {
        return getModelMapper().map(source, FoodInfoDTO.class);
    }
}
