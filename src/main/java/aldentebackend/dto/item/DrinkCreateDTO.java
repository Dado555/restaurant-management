package aldentebackend.dto.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor @Getter @Setter
public class DrinkCreateDTO extends ItemCreateDTO {

    @NotBlank
    private Boolean isAlcoholic;
}

