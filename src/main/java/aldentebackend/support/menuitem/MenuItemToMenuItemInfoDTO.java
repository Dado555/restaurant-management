package aldentebackend.support.menuitem;

import aldentebackend.dto.item.ItemInfoDTO;
import aldentebackend.dto.menuitem.MenuItemInfoDTO;
import aldentebackend.dto.price.PriceInfoDTO;
import aldentebackend.exception.BadRequestException;
import aldentebackend.model.Drink;
import aldentebackend.model.Food;
import aldentebackend.model.MenuItem;
import aldentebackend.model.Price;
import aldentebackend.support.AbstractConverter;
import aldentebackend.support.drink.DrinkToDrinkInfoDTO;
import aldentebackend.support.food.FoodToFoodInfoDTO;
import aldentebackend.support.price.PriceToPriceInfoDTO;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;

@AllArgsConstructor
@Component
public class MenuItemToMenuItemInfoDTO extends AbstractConverter<MenuItem, MenuItemInfoDTO> {

    private final DrinkToDrinkInfoDTO toDrinkInfoDTO;
    private final FoodToFoodInfoDTO toFoodInfoDTO;
    private final PriceToPriceInfoDTO toPriceInfoDTO;

    @Override
    public MenuItemInfoDTO convert(@NonNull MenuItem source) {
        ItemInfoDTO itemDTO;
        var item = source.getItem();
        String type = "";
        boolean isAlcoholic = false;
        if (item instanceof Drink) {
            itemDTO = toDrinkInfoDTO.convert((Drink) item);
            isAlcoholic = ((Drink) item).getIsAlcoholic();
            type = "drink";
        }
        else if (item instanceof Food) {
            itemDTO = toFoodInfoDTO.convert((Food) item);
            type = "food";
        }
        else
            throw new BadRequestException("");

        MenuItemInfoDTO menuItemInfoDTO = new MenuItemInfoDTO();
        menuItemInfoDTO.setId(source.getId());
        menuItemInfoDTO.setItem(itemDTO);
        menuItemInfoDTO.setCategoryId(source.getCategory().getId());
        menuItemInfoDTO.setType(type);
        menuItemInfoDTO.setIsAlcoholic(isAlcoholic);
        menuItemInfoDTO.setImageUrl(source.getImageUrl());

        Price price = Collections.max(source.getPrices(), Comparator.comparing(Price::getDate));
        PriceInfoDTO priceInfoDTO = toPriceInfoDTO.convert(price);
        menuItemInfoDTO.setPrice(priceInfoDTO);
        menuItemInfoDTO.setActive(source.getActive());

        return menuItemInfoDTO;
    }
}
