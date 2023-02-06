package aldentebackend.repository;

import aldentebackend.model.MenuItemCategory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class MenuItemCategoryRepositoryTest {
    @Autowired
    private MenuItemCategoryRepository menuItemCategoryRepository;

    @ParameterizedTest
    @MethodSource("categoryNotFound")
    public void findByName_calledWithInvalidName_returnsNullCategory(String name) {
        MenuItemCategory category = menuItemCategoryRepository.findByName(name);

        assertNull(category, "Category not found");
    }

    private static Stream<String> categoryNotFound() {
        return Stream.of("", " ", "\n", "\t", null, "CATEGORY");
    }

    @ParameterizedTest
    @MethodSource("categoryValidNames")
    public void findByName_calledWithValidName_returnsValidCategoryObject(String name) {
        MenuItemCategory category = menuItemCategoryRepository.findByName(name);

        assertNotNull(category, "Category found");
        assertTrue(category.getName().equalsIgnoreCase(name));
    }

    private static Stream<String> categoryValidNames() {
        return Stream.of("Appetizer", "Main course", "Desert");
    }
}
