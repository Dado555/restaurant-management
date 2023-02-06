package aldentebackend.service;

import aldentebackend.constants.MenuItemCategoryConstants;
import aldentebackend.exception.NotFoundException;
import aldentebackend.model.MenuItemCategory;
import aldentebackend.repository.MenuItemCategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MenuItemCategoryServiceUnitTest {
    @Autowired
    private MenuItemCategoryService menuItemCategoryService;

    @MockBean
    private MenuItemCategoryRepository menuItemCategoryRepository;

    @Test
    public void findOneByName_calledWithInvalidName_throwsNotFoundException() {
        assertThrows(NotFoundException.class, () -> menuItemCategoryService.findOneByName(null));
    }

    @Test
    public void findOneByName_calledWithValidName_isSuccess() {
        MenuItemCategory menuItemCategory = new MenuItemCategory(MenuItemCategoryConstants.MENU_ITEM_CATEGORY_NAME);
        menuItemCategory.setId(MenuItemCategoryConstants.MENU_ITEM_CATEGORY_ID);

        doReturn(menuItemCategory).when(menuItemCategoryRepository).findByName(MenuItemCategoryConstants.MENU_ITEM_CATEGORY_NAME);

        assertThat(menuItemCategoryService.findOneByName(MenuItemCategoryConstants.MENU_ITEM_CATEGORY_NAME)
                .getId()).isEqualTo(MenuItemCategoryConstants.MENU_ITEM_CATEGORY_ID);
        assertThat(menuItemCategoryService.findOneByName(MenuItemCategoryConstants.MENU_ITEM_CATEGORY_NAME)
                .getName()).isEqualTo(MenuItemCategoryConstants.MENU_ITEM_CATEGORY_NAME);
    }
}
