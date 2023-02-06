package aldentebackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter @Setter
public abstract class Suggestion extends BaseEntity {

    @Column(name = "date", nullable = false)
    private Long date;

    @Column(name = "description")
    private String description;

    public Suggestion() {
        super();
    }
}
