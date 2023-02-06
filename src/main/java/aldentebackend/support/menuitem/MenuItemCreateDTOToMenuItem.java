package aldentebackend.support.menuitem;

import aldentebackend.dto.item.ItemCreateDTO;
import aldentebackend.dto.menuitem.MenuItemCreateDTO;
import aldentebackend.model.*;
import aldentebackend.service.MenuItemCategoryService;
import aldentebackend.support.AbstractConverter;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class MenuItemCreateDTOToMenuItem extends AbstractConverter<MenuItemCreateDTO, MenuItem> {

    private final MenuItemCategoryService menuItemCategoryService;

    @Override
    public MenuItem convert(@NonNull MenuItemCreateDTO source) {
        MenuItemCategory category = menuItemCategoryService.findOne(source.getCategory().getId());

        Item item;
        if (source.getType().equals("food")) {
            ItemCreateDTO foodCreateDTO = source.getItem();
            item = new Food(foodCreateDTO.getName(), foodCreateDTO.getIngredients(), foodCreateDTO.getPreparationTime(), foodCreateDTO.getDescription());
        }
        else {
            ItemCreateDTO drinkCreateDTO = source.getItem();
            item = new Drink(drinkCreateDTO.getName(), drinkCreateDTO.getIngredients(), drinkCreateDTO.getPreparationTime(), drinkCreateDTO.getDescription(), source.getIsAlcoholic());
        }

        Price price = new Price(source.getPrice().getPrice(), source.getPrice().getExpense(), System.currentTimeMillis());
        MenuItem menuItem = new MenuItem(item, category, null, source.getImageUrl());
        price.setMenuItem(menuItem);
        menuItem.setPrice(price);
        return menuItem;
    }
}
