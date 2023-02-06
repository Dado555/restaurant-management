package aldentebackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter @Setter
public class Drink extends Item {

    @Column(name = "is_alcoholic", nullable = false)
    private Boolean isAlcoholic;

    public Drink() {
        super();
    }

    public Drink(String name, String ingredients, Long preparationTime, String description, Boolean isAlcoholic) {
        super(name, ingredients, preparationTime, description);
        this.isAlcoholic = isAlcoholic;
    }
}
