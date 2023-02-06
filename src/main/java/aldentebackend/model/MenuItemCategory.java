package aldentebackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@Entity
public class MenuItemCategory extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<MenuItem> menuItems = new HashSet<>();

    public MenuItemCategory() {
        super();
    }

    public MenuItemCategory(String name) {
        this.name = name;
    }

    public void addMenuItem(MenuItem item) { this.menuItems.add(item); }
}
