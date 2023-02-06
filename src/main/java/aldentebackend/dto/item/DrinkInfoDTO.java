package aldentebackend.dto.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor @Getter @Setter
public class DrinkInfoDTO extends ItemInfoDTO {

    @NotNull
    private Boolean isAlcoholic;
}
