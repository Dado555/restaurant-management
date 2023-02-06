package aldentebackend.dto.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @Getter @Setter
public class ItemUpdateDTO {

    private Long id;
    private String name;
    private String ingredients;
    private Long preparationTime;
    private String description;
}
