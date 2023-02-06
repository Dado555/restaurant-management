package aldentebackend.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter @Setter
public class RestaurantTable extends BaseEntity {

    @Column(name = "table_number", nullable = false)
    private Short tableNumber;

    @Column(name = "number_of_seats", nullable = false)
    private Short numberOfSeats;

    @Column(name = "position_x")
    private Short positionX;

    @Column(name = "position_y")
    private Short positionY;

    @ColumnDefault("100")
    @Column(name = "table_width", nullable = false)
    private Short tableWidth;

    @ColumnDefault("100")
    @Column(name = "table_height", nullable = false)
    private Short tableHeight;

    @ManyToOne
    @JoinColumn(name = "sector_id")
    private Sector sector;

    public RestaurantTable() {
        super();
    }
}
