package aldentebackend.dto.item;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class ItemCreateDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String ingredients;

    @NotBlank
    private Long preparationTime;

    @NotBlank
    private String description;

    public ItemCreateDTO() {}

    public ItemCreateDTO(String name, String ingredients, Long preparationTime, String description) {
        this.name = name;
        this.ingredients = ingredients;
        this.preparationTime = preparationTime;
        this.description = description;
    }
}
