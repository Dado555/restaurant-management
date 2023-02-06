package aldentebackend.dto.suggestion;

import aldentebackend.dto.item.DrinkCreateDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor @Getter @Setter
public class DrinkSuggestionCreateDTO {

    @NotNull
    private Long bartenderId;

    @NotNull
    private DrinkCreateDTO drink;

    private String description;
}
