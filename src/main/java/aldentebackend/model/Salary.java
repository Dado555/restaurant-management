package aldentebackend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter @Setter
public class Salary extends BaseEntity {

    @Column(name = "value")
    private Double value;

    @Column(name = "date", nullable = false)
    private Long date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Salary() {
        super();
    }

    public Salary(Double value, Long date) {
        this.value = value;
        this.date = date;
    }


}
