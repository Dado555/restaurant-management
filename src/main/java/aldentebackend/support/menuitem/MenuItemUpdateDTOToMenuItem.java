package aldentebackend.support.menuitem;

import aldentebackend.dto.menuitem.MenuItemUpdateDTO;
import aldentebackend.model.*;
import aldentebackend.service.DrinkService;
import aldentebackend.service.FoodService;
import aldentebackend.service.MenuItemCategoryService;
import aldentebackend.service.PriceService;
import aldentebackend.support.AbstractConverter;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class MenuItemUpdateDTOToMenuItem extends AbstractConverter<MenuItemUpdateDTO, MenuItem> {

    private final MenuItemCategoryService menuItemCategoryService;
    private final FoodService foodService;
    private final DrinkService drinkService;
    private final PriceService priceService;

//    private final IConverter<FoodUpdateDTO, Food> toFood;
//    private final IConverter<DrinkUpdateDTO, Drink> toDrink;

    @Override
    public MenuItem convert(@NonNull MenuItemUpdateDTO source) {
        MenuItem menuItem = new MenuItem();

        var itemUpdateDTO = source.getItem();
        if (source.getType().equals("food")) {
            Food food = foodService.findOne(itemUpdateDTO.getId());
            food.setDescription(itemUpdateDTO.getDescription());
            food.setIngredients(itemUpdateDTO.getIngredients());
            food.setName(itemUpdateDTO.getName());
            food.setPreparationTime(itemUpdateDTO.getPreparationTime());
            menuItem.setItem(food);
        } else {
            Drink drink = drinkService.findOne(itemUpdateDTO.getId());
            drink.setIngredients(itemUpdateDTO.getIngredients());
            drink.setDescription(itemUpdateDTO.getDescription());
            drink.setPreparationTime(itemUpdateDTO.getPreparationTime());
            drink.setIsAlcoholic(source.getIsAlcoholic());
            menuItem.setItem(drink);
        }

        Price old = priceService.findCurrentPriceForMenuItem(source.getId());

        if(source.getPrice().getPrice() != null && source.getPrice().getExpense() != null &&
                (old.getValue().doubleValue() != source.getPrice().getPrice().doubleValue()
                        || old.getExpense().doubleValue() != source.getPrice().getExpense().doubleValue())) {
            Price price = new Price();
            if (source.getPrice().getPrice() != null) {
                price.setValue(source.getPrice().getPrice());
                price.setDate(System.currentTimeMillis());
            }
            if (source.getPrice().getExpense() != null) {
                price.setExpense(source.getPrice().getExpense());
                price.setDate(System.currentTimeMillis());
            }
            price.setMenuItem(menuItem);
            menuItem.setPrice(price);
        }

        if (source.getCategory().getId() != null)
            menuItem.setCategory(menuItemCategoryService.findOne(source.getCategory().getId()));
        else {
            MenuItemCategory category = new MenuItemCategory();
            // category.addMenuItem(menuItem);
            category.setName(source.getCategory().getName());
            menuItem.setCategory(category);
        }

        if (source.getImageUrl() != null)
            menuItem.setImageUrl(source.getImageUrl());

        return menuItem;
    }
}
