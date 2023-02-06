package aldentebackend.service;

import aldentebackend.constants.PriceConstants;
import aldentebackend.exception.BadRequestException;
import aldentebackend.model.Price;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PriceServiceIntegrationTest {

    @Autowired
    private PriceService priceService;

    @ParameterizedTest
    @MethodSource("invalidLongs")
    public void findCurrentPriceForMenuItem_calledWithInvalidMenuItemId_throwsBadRequestException(Long id) {
        assertThrows(BadRequestException.class, () -> priceService.findCurrentPriceForMenuItem(id));
    }

    private static Stream<Long> invalidLongs() {
        return Stream.of(null, PriceConstants.MENU_ITEM_INVALID_ID_1, PriceConstants.MENU_ITEM_INVALID_ID_2);
    }

    @Test
    public void findCurrentPriceForMenuItem_calledWithValidMenuItemId_isSuccess() {
        Price price = priceService.findCurrentPriceForMenuItem(PriceConstants.MENU_ITEM_VALID);

        assertThat(price.getDate()).isEqualTo(PriceConstants.MENU_ITEM_VALID_DATE);
        assertThat(price.getValue()).isEqualTo(PriceConstants.MENU_ITEM_VALID_VALUE);
        assertThat(price.getExpense()).isEqualTo(PriceConstants.MENU_ITEM_VALID_EXPENSE);
    }
}
