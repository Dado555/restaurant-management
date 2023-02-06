package aldentebackend.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
@Where(clause = "active = true")
public class MenuItem extends BaseEntity {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false)
    private Item item;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private MenuItemCategory category;

    @OneToMany(mappedBy = "menuItem", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Price> prices = new HashSet<>();

    @Column(name = "image_url", nullable = true )
    private String imageUrl;

    @Version
    @ColumnDefault("0")
    private Short version;

    public MenuItem() {
        super();
    }

    @Deprecated
    public MenuItem(Item item, MenuItemCategory category, Price price) {
        this.item = item;
        this.category = category;
        if(price != null)
            this.prices.add(price);
    }


    public MenuItem(Item item, MenuItemCategory category, Price price, String imageUrl) {
        this.item = item;
        this.category = category;
        this.imageUrl = imageUrl;
        if(price != null)
            this.prices.add(price);
    }

    public void setPrice(Price price) {
        this.prices.add(price);
    }
}
