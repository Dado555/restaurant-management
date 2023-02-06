package aldentebackend.support.food;

import aldentebackend.dto.item.FoodCreateDTO;
import aldentebackend.model.Food;
import aldentebackend.support.AbstractConverter;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class FoodCreateDTOToFood extends AbstractConverter<FoodCreateDTO, Food> {

    @Override
    public Food convert(@NonNull FoodCreateDTO source) {
        return getModelMapper().map(source, Food.class);
    }
}
