package aldentebackend.service;

import aldentebackend.constants.MenuItemConstants;
import aldentebackend.exception.BadRequestException;
import aldentebackend.model.*;
import aldentebackend.repository.MenuItemRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MenuItemServiceUnitTest {

    @Autowired
    private MenuItemService menuItemService;

    @MockBean
    private MenuItemRepository menuItemRepositoryMock;

    @Test
    public void updateMenuItem_calledForFoodUpdateWithDrinkAsParameterOfMenuItem_throwsBadRequestException() {
        Food food = new Food();
        MenuItem menuItem = new MenuItem();
        menuItem.setId(MenuItemConstants.ID);
        menuItem.setItem(food);

        Drink newDrink = new Drink(MenuItemConstants.NAME, MenuItemConstants.INGREDIENTS, MenuItemConstants.PREPARATION_TIME, MenuItemConstants.DESCRIPTION, MenuItemConstants.IS_ALCOHOLIC);
        MenuItem newMenuItem = new MenuItem();
        newMenuItem.setItem(newDrink);
        newMenuItem.setCategory(new MenuItemCategory(MenuItemConstants.MENU_ITEM_CATEGORY_NAME));

        doReturn(Optional.of(menuItem)).when(menuItemRepositoryMock).findById(MenuItemConstants.ID);
        doReturn(newMenuItem).when(menuItemRepositoryMock).save(menuItem);

        assertThrows(BadRequestException.class, () -> menuItemService.update(MenuItemConstants.ID, newMenuItem));
    }

    @Test
    public void updateMenuItem_calledForDrinkUpdateWithFoodAsParameterOfMenuItem_throwsBadRequestException() {
        Drink drink = new Drink();
        MenuItem menuItem = new MenuItem();
        menuItem.setId(MenuItemConstants.ID);
        menuItem.setItem(drink);

        Food newFood = new Food(MenuItemConstants.NAME, MenuItemConstants.INGREDIENTS, MenuItemConstants.PREPARATION_TIME, MenuItemConstants.DESCRIPTION);
        MenuItem newMenuItem = new MenuItem();
        newMenuItem.setItem(newFood);
        newMenuItem.setCategory(new MenuItemCategory(MenuItemConstants.MENU_ITEM_CATEGORY_NAME));

        doReturn(Optional.of(menuItem)).when(menuItemRepositoryMock).findById(MenuItemConstants.ID);
        doReturn(newMenuItem).when(menuItemRepositoryMock).save(menuItem);

        assertThrows(BadRequestException.class, () -> menuItemService.update(MenuItemConstants.ID, newMenuItem));
    }

    @Test
    public void updateMenuItem_calledWithValidFoodItem_isSuccess() {
        Food food = new Food();
        MenuItem menuItem = new MenuItem();
        menuItem.setId(MenuItemConstants.ID);
        menuItem.setItem(food);

        Food newFood = new Food(MenuItemConstants.NAME, MenuItemConstants.INGREDIENTS, MenuItemConstants.PREPARATION_TIME, MenuItemConstants.DESCRIPTION);
        MenuItem newMenuItem = new MenuItem();
        newMenuItem.setItem(newFood);
        newMenuItem.setCategory(new MenuItemCategory("new"));

        doReturn(Optional.of(menuItem)).when(menuItemRepositoryMock).findById(MenuItemConstants.ID);
        doReturn(newMenuItem).when(menuItemRepositoryMock).save(menuItem);

        menuItemService.update(MenuItemConstants.ID, newMenuItem);

        assertThat(menuItem.getItem().getName()).isEqualTo(MenuItemConstants.NAME);
        assertThat(menuItem.getItem().getIngredients()).isEqualTo(MenuItemConstants.INGREDIENTS);
        assertThat(menuItem.getItem().getPreparationTime()).isEqualTo(MenuItemConstants.PREPARATION_TIME);
        assertThat(menuItem.getItem().getDescription()).isEqualTo(MenuItemConstants.DESCRIPTION);
        assertThat(menuItem.getCategory().getName()).isEqualTo("new");
    }

    @Test
    public void updateMenuItem_calledWithDrinkItem_isSuccess() {
        Drink drink = new Drink();
        MenuItem menuItem = new MenuItem();
        menuItem.setId(MenuItemConstants.ID);
        menuItem.setItem(drink);

        Drink newDrink = new Drink(MenuItemConstants.NAME, MenuItemConstants.INGREDIENTS, MenuItemConstants.PREPARATION_TIME, MenuItemConstants.DESCRIPTION, MenuItemConstants.IS_ALCOHOLIC);
        MenuItem newMenuItem = new MenuItem();
        newMenuItem.setItem(newDrink);
        newMenuItem.setCategory(new MenuItemCategory("new"));

        doReturn(Optional.of(menuItem)).when(menuItemRepositoryMock).findById(MenuItemConstants.ID);
        doReturn(newMenuItem).when(menuItemRepositoryMock).save(menuItem);

        menuItemService.update(MenuItemConstants.ID, newMenuItem);

        assertThat(menuItem.getItem().getName()).isEqualTo(MenuItemConstants.NAME);
        assertThat(menuItem.getItem().getIngredients()).isEqualTo(MenuItemConstants.INGREDIENTS);
        assertThat(menuItem.getItem().getPreparationTime()).isEqualTo(MenuItemConstants.PREPARATION_TIME);
        assertThat(menuItem.getItem().getDescription()).isEqualTo(MenuItemConstants.DESCRIPTION);
        assertThat(((Drink)menuItem.getItem()).getIsAlcoholic()).isEqualTo(MenuItemConstants.IS_ALCOHOLIC);
        assertThat(menuItem.getCategory().getName()).isEqualTo("new");
    }
}
