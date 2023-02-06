package aldentebackend.model;

import aldentebackend.model.enums.OrderItemStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter @Setter
@Where(clause = "active = true")
public class OrderItem extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "menu_item_id", nullable = false)
    private MenuItem menuItem;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderItemStatus status;

    @Column(name = "description")
    private String description;

    @Column(name = "current_expense")
    private Double currentExpense;

    @Column(name = "current_price")
    private Double currentPrice;

    @ManyToOne
    @JoinColumn(name = "worker_id")
    private User worker;

    @Version
    @ColumnDefault("0")
    private Short version;

    public OrderItem() {
        super();
    }

    public OrderItem(Order order, MenuItem menuItem, Double currentExpense, Double currentPrice, Integer amount, OrderItemStatus status,String description) {
        this.order = order;
        this.menuItem = menuItem;
        this.currentExpense = currentExpense;
        this.currentPrice = currentPrice;
        this.amount = amount;
        this.status = status;
        this.description = description;
    }
}
