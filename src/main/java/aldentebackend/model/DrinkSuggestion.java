package aldentebackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class DrinkSuggestion extends Suggestion {

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "drink_id", referencedColumnName = "id")
    private Drink drink;

    @ManyToOne
    @JoinColumn(name = "bartender_id", nullable = false)
    private Bartender bartender;

    public DrinkSuggestion() {
        super();
    }

    public DrinkSuggestion(Drink drink, Bartender bartender, Long date, String description) {
        this.drink = drink;
        this.bartender = bartender;
        this.setDate(date);
        this.setDescription(description);
    }
}
