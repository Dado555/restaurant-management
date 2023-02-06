package aldentebackend.support.drinksuggestion;

import aldentebackend.dto.suggestion.DrinkSuggestionCreateDTO;
import aldentebackend.exception.BadRequestException;
import aldentebackend.model.*;
import aldentebackend.service.UserService;
import aldentebackend.support.AbstractConverter;
import aldentebackend.support.drink.DrinkCreateDTOToDrink;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class DrinkSuggestionCreateDTOToDrinkSuggestion extends AbstractConverter<DrinkSuggestionCreateDTO, DrinkSuggestion> {

    private final DrinkCreateDTOToDrink toDrink;

    private final UserService userService;

    @Override
    public DrinkSuggestion convert(@NotNull DrinkSuggestionCreateDTO drinkSuggestionCreateDTO) {
        User user = userService.findOneWithAuthorities(drinkSuggestionCreateDTO.getBartenderId());
        if (!user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toList()).contains("BARTENDER"))
            throw new BadRequestException("Only bartender is allowed to create drink suggestions.");

        Drink drink = toDrink.convert(drinkSuggestionCreateDTO.getDrink());
        return new DrinkSuggestion(drink, (Bartender) user, System.currentTimeMillis(), drinkSuggestionCreateDTO.getDescription());
    }
}
