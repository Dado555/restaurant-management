package aldentebackend.dto.suggestion;

import aldentebackend.dto.item.FoodCreateDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor @Getter @Setter
public class FoodSuggestionCreateDTO {

    @NotNull
    private Long cookId;

    @NotNull
    private FoodCreateDTO food;

    private String description;
}
