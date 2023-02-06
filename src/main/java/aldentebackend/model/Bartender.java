package aldentebackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("BARTENDER")
@Getter @Setter
public class Bartender extends User {

    @OneToMany(mappedBy = "bartender", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<DrinkSuggestion> drinkSuggestions = new HashSet<>();

    public Bartender() {
        super();
    }

    public Bartender(String firstName, String lastName, String phoneNumber, String email, String password, Authority authority) {
        super(firstName, lastName, phoneNumber, email, password, authority);
    }
}
