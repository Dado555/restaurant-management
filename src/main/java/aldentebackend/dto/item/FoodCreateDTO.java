package aldentebackend.dto.item;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FoodCreateDTO extends ItemCreateDTO {

    public FoodCreateDTO() {
        super();
    }

    public FoodCreateDTO(String name, String ingredients, Long preparationTime, String description) {
        super(name, ingredients, preparationTime, description);
    }
}

