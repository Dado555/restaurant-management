package aldentebackend.dto.suggestion;

import aldentebackend.dto.item.DrinkInfoDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor @Getter @Setter
public class DrinkSuggestionInfoDTO {

    @NotBlank
    private Long id;

    @NotNull
    private Long bartenderId;

    @NotNull
    private DrinkInfoDTO drink;
}
