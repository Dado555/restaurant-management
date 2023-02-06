package aldentebackend.support.drinksuggestion;

import aldentebackend.dto.suggestion.DrinkSuggestionInfoDTO;
import aldentebackend.model.DrinkSuggestion;
import aldentebackend.support.AbstractConverter;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class DrinkSuggestionToDrinkSuggestionInfoDTO extends AbstractConverter<DrinkSuggestion, DrinkSuggestionInfoDTO> {

    @Override
    public DrinkSuggestionInfoDTO convert(@NonNull DrinkSuggestion source) {
        return getModelMapper().map(source, DrinkSuggestionInfoDTO.class);
    }
}
