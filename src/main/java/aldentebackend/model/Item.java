package aldentebackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter @Setter
public abstract class Item extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "ingredients", nullable = false)
    private String ingredients;

    @Column(name = "preparation_time", nullable = false)
    private Long preparationTime;

    @Column(name = "description")
    private String description;

    public Item() {
        super();
    }

    public Item(String name, String ingredients, Long prepTime, String description) {
        this.name = name;
        this.ingredients = ingredients;
        this.preparationTime = prepTime;
        this.description = description;
    }
}
