package aldentebackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("COOK")
@Getter @Setter
public class Cook extends User {

    @OneToMany(mappedBy = "cook", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<FoodSuggestion> foodSuggestions = new HashSet<>();

    public Cook() {
        super();
    }

    public Cook(String firstName, String lastName, String phoneNumber, String email, String password, Authority authority) {
        super(firstName, lastName, phoneNumber, email, password, authority);
    }
}
