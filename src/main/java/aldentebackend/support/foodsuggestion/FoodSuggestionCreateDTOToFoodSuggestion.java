package aldentebackend.support.foodsuggestion;

import aldentebackend.dto.item.FoodCreateDTO;
import aldentebackend.dto.suggestion.FoodSuggestionCreateDTO;
import aldentebackend.exception.BadRequestException;
import aldentebackend.model.*;
import aldentebackend.service.UserService;
import aldentebackend.support.AbstractConverter;
import aldentebackend.support.IConverter;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class FoodSuggestionCreateDTOToFoodSuggestion extends AbstractConverter<FoodSuggestionCreateDTO, FoodSuggestion> {

    private final UserService userService;

    private final IConverter<FoodCreateDTO, Food> toFood;

    @Override
    public FoodSuggestion convert(@NonNull FoodSuggestionCreateDTO source) {
        User user = userService.findOneWithAuthorities(source.getCookId());
        if (!user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toList()).contains("COOK"))
            throw new BadRequestException("Only cook is allowed to create drink suggestions.");

        Food food = toFood.convert(source.getFood());
        return new FoodSuggestion(food, (Cook) user, System.currentTimeMillis(), source.getDescription());
    }
}
