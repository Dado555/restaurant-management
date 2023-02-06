package aldentebackend.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter @Setter
public class Authority extends BaseEntity implements GrantedAuthority {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    public Authority() {
        super();
    }

    public Authority(String name) {
        this.setName(name);
    }

    @Override
    public String getAuthority() {
        return this.name;
    }
}
