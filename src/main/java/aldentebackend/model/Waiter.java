package aldentebackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("WAITER")
@Getter @Setter
public class Waiter extends User {

    public Waiter() {
        super();
    }

    public Waiter(String firstName, String lastName, String phoneNumber, String email, String password, Authority authority) {
        super(firstName, lastName, phoneNumber, email, password, authority);
    }
}
