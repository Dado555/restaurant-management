package aldentebackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class FoodSuggestion extends Suggestion {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "food_id", referencedColumnName = "id")
    private Food food;

    @ManyToOne
    @JoinColumn(name = "cook_id", nullable = false)
    private Cook cook;

    public FoodSuggestion() {
        super();
    }

    public FoodSuggestion(Food food, Cook cook, Long date, String description) {
        this.food = food;
        this.cook = cook;
        this.setDate(date);
        this.setDescription(description);
    }
}
