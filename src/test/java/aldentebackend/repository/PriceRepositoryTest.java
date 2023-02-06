package aldentebackend.repository;

import aldentebackend.constants.PriceConstants;
import aldentebackend.model.Price;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class PriceRepositoryTest {
    @Autowired
    private PriceRepository priceRepository;

    @ParameterizedTest
    @MethodSource("invalidData")
    public void findFirstByMenuItemIdAndDateLessThanEqualOrderByDateDesc_calledWithInvalidIdOrDate_returnsNullPrice(
            Long menuItemId, Long dateMillis) {
        Price price = priceRepository.findFirstByMenuItemIdAndDateLessThanEqualOrderByDateDesc(menuItemId, dateMillis);

        assertNull(price);
    }

    private static Stream<Arguments> invalidData() {
        return Stream.of(
                Arguments.of(null, PriceConstants.MENU_ITEM_VALID_DATE),
                Arguments.of(PriceConstants.MENU_ITEM_INVALID_ID_1, PriceConstants.MENU_ITEM_VALID_DATE),
                Arguments.of(PriceConstants.MENU_ITEM_INVALID_ID_2, PriceConstants.MENU_ITEM_VALID_DATE),
                Arguments.of(PriceConstants.MENU_ITEM_VALID, PriceConstants.MENU_ITEM_VALID_DATE-1000L)
        );
    }

    @Test
    public void findFirstByMenuItemIdAndDateLessThanEqualOrderByDateDesc_calledWithInvalidDate_throwsInvalidDataAccessApiUsageException() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> priceRepository.findFirstByMenuItemIdAndDateLessThanEqualOrderByDateDesc(
                PriceConstants.MENU_ITEM_VALID, null));
    }

    @Test
    public void findFirstByMenuItemIdAndDateLessThanEqualOrderByDateDesc_calledWithValidData_isSuccess() {
        Price price = priceRepository.findFirstByMenuItemIdAndDateLessThanEqualOrderByDateDesc(
                PriceConstants.MENU_ITEM_VALID, PriceConstants.MENU_ITEM_VALID_DATE);

        assertNotNull(price);
        assertEquals(PriceConstants.MENU_ITEM_VALID_EXPENSE, price.getExpense());
        assertEquals(PriceConstants.MENU_ITEM_VALID_VALUE, price.getValue());
    }
}
