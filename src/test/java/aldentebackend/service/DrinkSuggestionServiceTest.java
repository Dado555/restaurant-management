package aldentebackend.service;

import aldentebackend.constants.DrinkSuggestionConstants;
import aldentebackend.model.Bartender;
import aldentebackend.model.Drink;
import aldentebackend.model.DrinkSuggestion;
import aldentebackend.repository.DrinkSuggestionRepository;
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
import static org.mockito.Mockito.doThrow;


@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DrinkSuggestionServiceTest {

    @Autowired
    private DrinkSuggestionService drinkSuggestionService;

    @MockBean
    private DrinkSuggestionRepository drinkSuggestionRepository;

    @MockBean
    private Bartender bartender;

    @MockBean
    private Drink drink;

    @Test
    public void create_calledWithValidData_isSuccess() {
        DrinkSuggestion drinkSuggestion = new DrinkSuggestion();
        drinkSuggestion.setId(DrinkSuggestionConstants.NON_EXISTENT_ID);
        drinkSuggestion.setDrink(drink);
        drinkSuggestion.setBartender(bartender);
        drinkSuggestion.setDescription(DrinkSuggestionConstants.DRINK_SUGGESTION_DESCRIPTION);

        doReturn(Optional.of(drinkSuggestion)).when(drinkSuggestionRepository).save(drinkSuggestion);

        assertThat(drinkSuggestion.getId()).isEqualTo(DrinkSuggestionConstants.NON_EXISTENT_ID);
    }

    @Test
    public void create_calledWithInvalidData_throwsIllegalArgumentException() {
        DrinkSuggestion drinkSuggestion = null;

        doThrow(IllegalArgumentException.class).when(drinkSuggestionRepository).save(drinkSuggestion);

        assertThrows(IllegalArgumentException.class, () -> drinkSuggestionService.create(drinkSuggestion));
    }
}