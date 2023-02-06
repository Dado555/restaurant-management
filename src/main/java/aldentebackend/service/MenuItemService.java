package aldentebackend.service;

import aldentebackend.model.MenuItem;
import aldentebackend.model.OrderItem;

import java.util.Collection;
import java.util.List;

public interface MenuItemService extends JPAService<MenuItem> {

    List<MenuItem> findAllByCategoryName(String name);

    List<MenuItem> findAllByCategoryId(Long categoryId);

    MenuItem create(MenuItem menuItem);

    Collection<MenuItem> findAllMenuItemsWithPrices();

    Integer getMenuItemsCount();

    MenuItem update(Long id, MenuItem menuItem);
}
