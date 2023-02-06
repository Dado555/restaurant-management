package aldentebackend.service;

import aldentebackend.constants.MenuItemCategoryConstants;
import aldentebackend.exception.NotFoundException;
import aldentebackend.model.MenuItemCategory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MenuItemCategoryServiceIntegrationTest {
    @Autowired
    private MenuItemCategoryService menuItemCategoryService;

    @ParameterizedTest
    @MethodSource("blankStrings")
    public void findOneByName_calledWithNullName_throwsNotFoundException(String name) {
        assertThrows(NotFoundException.class, () -> menuItemCategoryService.findOneByName(name));
    }

    private static Stream<String> blankStrings() {
        return Stream.of("", " ", null, "\t", "\n");
    }

    @Test
    public void findOneByName_calledWithInvalidName_throwsNotFoundException() {
        assertThrows(NotFoundException.class, () -> menuItemCategoryService
                .findOneByName(MenuItemCategoryConstants.MENU_ITEM_CATEGORY_INVALID_NAME));
    }

    @Test
    public void findOneByName_calledWithValidName_isSuccess() {
        MenuItemCategory menuItemCategory = menuItemCategoryService
                .findOneByName(MenuItemCategoryConstants.MENU_ITEM_CATEGORY_VALID_NAME);

        assertThat(menuItemCategory.getName()).isEqualTo(MenuItemCategoryConstants.MENU_ITEM_CATEGORY_VALID_NAME);
        assertThat(menuItemCategory.getId()).isEqualTo(MenuItemCategoryConstants.MENU_ITEM_CATEGORY_VALID_ID);
    }
}
