package aldentebackend.support.foodsuggestion;

import aldentebackend.dto.suggestion.FoodSuggestionInfoDTO;
import aldentebackend.model.FoodSuggestion;
import aldentebackend.support.AbstractConverter;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class FoodSuggestionToFoodSuggestionInfoDTO extends AbstractConverter<FoodSuggestion, FoodSuggestionInfoDTO> {

    @Override
    public FoodSuggestionInfoDTO convert(@NonNull FoodSuggestion source) {
        return getModelMapper().map(source, FoodSuggestionInfoDTO.class);
    }
}
