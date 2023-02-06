package aldentebackend.service;

import aldentebackend.constants.MenuItemCategoryConstants;
import aldentebackend.constants.MenuItemConstants;
import aldentebackend.exception.NotFoundException;
import aldentebackend.model.MenuItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MenuItemServiceIntegrationTest {

    @Autowired
    private MenuItemService menuItemService;

    @Test
    public void findAllByCategoryName_calledWithNullCategoryName_throwsNotFoundException() {
        assertThrows(NotFoundException.class, () -> menuItemService.findAllByCategoryName(null));
    }

    @Test
    public void findAllByCategoryName_calledWithNonExistingCategoryName_throwsNotFoundException() {
        assertThrows(NotFoundException.class, () -> menuItemService.findAllByCategoryName("xD"));
    }

    @Test
    public void findAllByCategoryName_calledWithValidCategoryName_throwsNotFoundException() {
         List<MenuItem> menuItems = menuItemService.findAllByCategoryName(MenuItemCategoryConstants.MENU_ITEM_CATEGORY_NAME);

        assertThat(menuItems.size()).isEqualTo(3);
        assertThat(menuItems.get(0).getCategory().getName()).isEqualTo(MenuItemCategoryConstants.MENU_ITEM_CATEGORY_NAME);
        assertThat(menuItems.get(1).getCategory().getName()).isEqualTo(MenuItemCategoryConstants.MENU_ITEM_CATEGORY_NAME);
        assertThat(menuItems.get(2).getCategory().getName()).isEqualTo(MenuItemCategoryConstants.MENU_ITEM_CATEGORY_NAME);
    }

    @Test
    public void createMenuItem_calledWithInvalidData() {
        MenuItem item = menuItemService.findOne(MenuItemConstants.ID);
        item.setCategory(null);
        item.setItem(null);

        assertThrows(DataIntegrityViolationException.class, () -> menuItemService.create(item));
    }

    @Test
    public void createMenuItem_calledWithValidData() {
        MenuItem item = menuItemService.findOne(MenuItemConstants.ID);

        MenuItem createdItem = menuItemService.create(item);

        assertThat(item.getId()).isEqualTo(createdItem.getId());
    }


}
