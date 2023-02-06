package aldentebackend.service.impl;

import aldentebackend.exception.BadRequestException;
import aldentebackend.model.*;
import aldentebackend.repository.MenuItemRepository;
import aldentebackend.service.MenuItemCategoryService;
import aldentebackend.service.MenuItemService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@AllArgsConstructor
@Service
public class MenuItemServiceImpl extends JPAServiceImpl<MenuItem> implements MenuItemService {

    private final MenuItemRepository menuItemRepository;

    private final MenuItemCategoryService menuItemCategoryService;

    @Override
    protected JpaRepository<MenuItem, Long> getEntityRepository() {
        return menuItemRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuItem> findAllByCategoryName(String name) {
        MenuItemCategory category = menuItemCategoryService.findOneByName(name);
        return new ArrayList<>(category.getMenuItems());
    }


    @Override
    @Transactional(readOnly = true)
    public List<MenuItem> findAllByCategoryId(Long categoryId) {
        MenuItemCategory category = menuItemCategoryService.findOneById(categoryId);
        return new ArrayList<>(category.getMenuItems());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MenuItem create(MenuItem menuItem) {
//         Price price = menuItem.getPrices().stream().findFirst().orElse(null);
//        MenuItem temp = new MenuItem(menuItem.getItem(), menuItem.getCategory(), null);
        menuItem = save(menuItem);
//        save(temp);
//        temp.setPrices(menuItem.getPrices());
//        menuItem = save(temp);
        return menuItem;
    }

    @Override
    public Collection<MenuItem> findAllMenuItemsWithPrices() {
        return menuItemRepository.getAllMenuItemsWithPrices();
    }

    @Override
    public Integer getMenuItemsCount() {
        return findAllMenuItemsWithPrices().size();
    }

    @Override
    @Transactional
    public MenuItem update(Long id, MenuItem newMenuItem) {
        MenuItem menuItem = findOne(id);

        if (newMenuItem.getCategory() != null)
            menuItem.setCategory(newMenuItem.getCategory());

        if (newMenuItem.getImageUrl() != null)
            menuItem.setImageUrl(newMenuItem.getImageUrl());

        // Update Price

        var item = menuItem.getItem();
        var newItem = newMenuItem.getItem();

        if ((item instanceof Food && !(newItem instanceof Food)) || (item instanceof Drink && !(newItem instanceof Drink)))
            throw new BadRequestException("Drink/Food");

        if (newItem.getName() != null && !newItem.getName().isBlank())
            item.setName(newItem.getName());

        if (newItem.getIngredients() != null && !newItem.getIngredients().isBlank())
            item.setIngredients(newItem.getIngredients());

        if (newItem.getPreparationTime() != null)
            item.setPreparationTime(newItem.getPreparationTime());

        if (newItem.getDescription() != null && !newItem.getDescription().isBlank())
            item.setDescription(newItem.getDescription());

        if (newItem instanceof Drink) {
            assert item instanceof Drink;
            ((Drink) item).setIsAlcoholic(((Drink) newItem).getIsAlcoholic());
            if (((Drink) newItem).getIsAlcoholic() != null)
                ((Drink) item).setIsAlcoholic(((Drink) newItem).getIsAlcoholic());
        }

        menuItem.setItem(item);
        if(newMenuItem.getPrices().size() > 0) {
            System.out.println("NOVA CIJENAA");
            System.out.println(newMenuItem.getPrices().stream().findFirst().get().getValue());
            Price price = newMenuItem.getPrices().stream().findFirst().get();
            price.setMenuItem(menuItem);
            menuItem.setPrice(price);
        }
        System.out.println("OVDJE");
        System.out.println(newMenuItem.getCategory().getId());
        System.out.println(newMenuItem.getCategory().getName());
        if(newMenuItem.getCategory().getName() == null) {
            MenuItemCategory category = menuItemCategoryService.findOne(newMenuItem.getCategory().getId());
            menuItem.setCategory(category);
            category.addMenuItem(menuItem);
            menuItemCategoryService.save(category);
        } else menuItemCategoryService.save(menuItem.getCategory());

        save(menuItem);

        return menuItem;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        MenuItem menuItem = findOne(id);
        menuItem.setActive(false);
        save(menuItem);
    }
}
