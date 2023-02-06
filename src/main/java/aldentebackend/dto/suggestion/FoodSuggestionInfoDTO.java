package aldentebackend.dto.suggestion;

import aldentebackend.dto.item.FoodInfoDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor @Getter @Setter
public class FoodSuggestionInfoDTO {

    @NotBlank
    private Long id;

    @NotNull
    private Long cookId;

    @NotNull
    private FoodInfoDTO food;
}
