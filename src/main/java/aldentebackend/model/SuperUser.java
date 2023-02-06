package aldentebackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SUPER_USER")
@Getter @Setter
public class SuperUser extends User {

    public SuperUser() {
        super();
    }

    public SuperUser(String firstName, String lastName, String phoneNumber, String email, String password, Authority authority) {
        super(firstName, lastName, phoneNumber, email, password, authority);
    }
}
