package aldentebackend.repository;

import aldentebackend.model.MenuItem;
import aldentebackend.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

//    @Query(value = "select menuItem, (select max(p.date) from Price p where p.menuItem = menuItem.id) from MenuItem menuItem")
//    Collection<Object> getAllMenuItemsWithPrice();

    @Query(value = "select menuItem from MenuItem menuItem join fetch menuItem.prices as p")
    Collection<MenuItem> getAllMenuItemsWithPrices();

}
