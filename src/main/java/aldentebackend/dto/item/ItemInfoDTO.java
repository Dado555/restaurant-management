package aldentebackend.dto.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor @Getter @Setter
public abstract class ItemInfoDTO {

    @NotNull
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String ingredients;

    @NotNull
    private Long preparationTime;

    private String description;
}
