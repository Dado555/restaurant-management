package aldentebackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter @Setter
public class Price extends BaseEntity {

    @Column(name = "value", nullable = false)
    private Double value;

    @Column(name = "expense", nullable = false)
    private Double expense;

    @Column(name = "date", nullable = false)
    private Long date;

    @ManyToOne
    @JoinColumn(name = "menu_item_id", nullable = false)
    private MenuItem menuItem;

    public Price() {
        super();
    }

    public Price(Double value, Double expense, Long date) {
        this.value = value;
        this.expense = expense;
        this.date = date;
    }
}
