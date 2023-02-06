package aldentebackend.service;

import aldentebackend.constants.PriceConstants;
import aldentebackend.exception.BadRequestException;
import aldentebackend.model.MenuItem;
import aldentebackend.model.Price;
import aldentebackend.repository.PriceRepository;
import jdk.jfr.TransitionTo;
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
public class PriceServiceUnitTest {
    @Autowired
    private PriceService priceService;

    @MockBean
    private PriceRepository priceRepository;

    @Test
    public void findCurrentPriceForMenuItem_calledWithInvalidId_throwsBadRequestException() {
        doReturn(new Price()).when(priceRepository).findFirstByMenuItemIdAndDateLessThanEqualOrderByDateDesc(
                PriceConstants.MENU_ITEM_ID, PriceConstants.CURRENT_TIME);

        assertThrows(BadRequestException.class, () -> priceService.findCurrentPriceForMenuItem(PriceConstants.MENU_ITEM_ID));
    }

    @Test
    public void findCurrentPriceForMenuItem_calledWithValidId_isSuccess() {
        Price price = new Price(PriceConstants.PRICE_VALUE, PriceConstants.PRICE_EXPENSE, PriceConstants.PRICE_DATE);
        MenuItem menuItem = new MenuItem();
        menuItem.setId(PriceConstants.MENU_ITEM_ID);
        price.setMenuItem(menuItem);

        doReturn(price).when(priceRepository).findFirstByMenuItemIdAndDateLessThanEqualOrderByDateDesc(
                PriceConstants.MENU_ITEM_ID, PriceConstants.CURRENT_TIME);

        assertThat(priceService.findCurrentPriceForMenuItem(PriceConstants.MENU_ITEM_ID, PriceConstants.CURRENT_TIME).getMenuItem().getId())
                .isEqualTo(PriceConstants.MENU_ITEM_ID);
        assertThat(priceService.findCurrentPriceForMenuItem(PriceConstants.MENU_ITEM_ID, PriceConstants.CURRENT_TIME).getExpense())
                .isEqualTo(PriceConstants.PRICE_EXPENSE);
        assertThat(priceService.findCurrentPriceForMenuItem(PriceConstants.MENU_ITEM_ID, PriceConstants.CURRENT_TIME).getValue())
                .isEqualTo(PriceConstants.PRICE_VALUE);
        assertThat(priceService.findCurrentPriceForMenuItem(PriceConstants.MENU_ITEM_ID, PriceConstants.CURRENT_TIME).getDate())
                .isEqualTo(PriceConstants.PRICE_DATE);
    }
}
