package aldentebackend.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
@Getter @Setter
@Where(clause = "active = true")
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "active", nullable = false, columnDefinition = "boolean default true")
    private Boolean active;

    public BaseEntity() {
        this.setActive(true);
    }
}
