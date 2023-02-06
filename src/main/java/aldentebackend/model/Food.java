package aldentebackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter @Setter
public class Food extends Item {

    public Food() {
       super();
    }

    public Food(String name, String ingredients, Long preparationTime, String description) {
        super(name, ingredients, preparationTime, description);
    }
}
