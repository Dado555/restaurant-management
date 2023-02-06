package aldentebackend.controller;


import aldentebackend.constants.DrinkSuggestionConstants;
import aldentebackend.constants.FoodSuggestionConstants;
import aldentebackend.constants.OrderItemConstants;
import aldentebackend.constants.UserConstants;
import aldentebackend.dto.authentication.AuthenticationRequestDTO;
import aldentebackend.dto.item.DrinkCreateDTO;
import aldentebackend.dto.item.FoodCreateDTO;
import aldentebackend.dto.suggestion.DrinkSuggestionCreateDTO;
import aldentebackend.dto.suggestion.FoodSuggestionCreateDTO;
import aldentebackend.model.DrinkSuggestion;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.validation.constraints.NotNull;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DrinkSuggestionControllerIntegrationTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private static final String URL_PREFIX = "/api/drink-suggestions";


    private void login(String username, String password) throws Exception {
        String body = mapper.writeValueAsString(new AuthenticationRequestDTO(username, password));

        MvcResult result = mockMvc.perform(post("/api/authentication/login")
                        .contentType("application/json")
                        .content(body))
                        .andExpect(status().isOk())
                        .andReturn();

        String jwtStringJson = result.getResponse().getContentAsString();
        JSONParser parser = new JSONParser();
        JSONObject jwtObject = (JSONObject) parser.parse(jwtStringJson);

        System.out.println(jwtObject.getAsString("jwt"));
    }

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getAllDrinkSuggestions_isOk() throws Exception {
        login(UserConstants.VALID_ADMIN_USERNAME, UserConstants.VALID_PASSWORD);
        mockMvc.perform(get(URL_PREFIX))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$", Matchers.hasSize(DrinkSuggestionConstants.DRINK_SUGGESTION_COUNT))
                );
    }

    @Test
    public void getDrinkSuggestion_calledWithNonExistingId_isNotFound() throws Exception {
        login(UserConstants.VALID_ADMIN_USERNAME, UserConstants.VALID_PASSWORD);
        mockMvc.perform(get(URL_PREFIX + "/" + DrinkSuggestionConstants.NON_EXISTENT_ID))
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @Test
    public void getDrinkSuggestion_calledWithValidId_isOk() throws Exception {
        login(UserConstants.VALID_ADMIN_USERNAME, UserConstants.VALID_PASSWORD);
        mockMvc.perform(get(URL_PREFIX + "/" + DrinkSuggestionConstants.ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(DrinkSuggestionConstants.ID),
                        jsonPath("$.bartenderId").value(DrinkSuggestionConstants.BARTENDER_ID),
                        jsonPath("$.drink.id").value(DrinkSuggestionConstants.DRINK_ID)
                );
    }

    @ParameterizedTest
    @MethodSource("provideDrinkSuggestionCreateDTOWithNonExistingBartenderId")
    public void createDrinkdSuggestion_calledWithNonExistingCookId_isNotFound(DrinkSuggestionCreateDTO drinkSuggestion) throws Exception {
        login(UserConstants.VALID_BARTENDER_USERNAME, UserConstants.VALID_MASTER_PASSWORD);

        mockMvc.perform(post(URL_PREFIX )
                .contentType("application/json")
                .content(mapper.writeValueAsString(drinkSuggestion)))
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @ParameterizedTest
    @MethodSource("provideValidDrinkSuggestionCreateDTO")
    public void createDrinkSuggestion_calledWithValidData_isOk(DrinkSuggestionCreateDTO drinkSuggestion) throws Exception {
        login(UserConstants.VALID_BARTENDER_USERNAME, UserConstants.VALID_MASTER_PASSWORD);

        mockMvc.perform(post(URL_PREFIX )
                .contentType("application/json")
                .content(mapper.writeValueAsString(drinkSuggestion)))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.bartenderId").value(DrinkSuggestionConstants.BARTENDER_ID),
                        jsonPath("$.drink.name").value(DrinkSuggestionConstants.NEW_DRINK_NAME),
                        jsonPath("$.drink.description").value(DrinkSuggestionConstants.NEW_DRINK_DESCRIPTION),
                        jsonPath("$.drink.ingredients").value(DrinkSuggestionConstants.NEW_DRINK_INGREDIENTS),
                        jsonPath("$.drink.preparationTime").value(DrinkSuggestionConstants.NEW_DRINK_PREPARATION_TIME),
                        jsonPath("$.drink.isAlcoholic").value(DrinkSuggestionConstants.NEW_DRINK_IS_ALCOHOLIC)

                );
    }

    private static Stream<Arguments> provideValidDrinkSuggestionCreateDTO() {
        DrinkSuggestionCreateDTO drinkSuggestionCreateDTO = new DrinkSuggestionCreateDTO();
        drinkSuggestionCreateDTO.setBartenderId(DrinkSuggestionConstants.BARTENDER_ID);
        drinkSuggestionCreateDTO.setDescription(DrinkSuggestionConstants.DESCRIPTION);

        DrinkCreateDTO drinkCreateDTO = new DrinkCreateDTO();
        drinkCreateDTO.setName(DrinkSuggestionConstants.NEW_DRINK_NAME);
        drinkCreateDTO.setDescription(DrinkSuggestionConstants.NEW_DRINK_DESCRIPTION);
        drinkCreateDTO.setIngredients(DrinkSuggestionConstants.NEW_DRINK_INGREDIENTS);
        drinkCreateDTO.setPreparationTime(DrinkSuggestionConstants.NEW_DRINK_PREPARATION_TIME);
        drinkCreateDTO.setIsAlcoholic(DrinkSuggestionConstants.NEW_DRINK_IS_ALCOHOLIC);

        drinkSuggestionCreateDTO.setDrink(drinkCreateDTO);

        return Stream.of(Arguments.of(drinkSuggestionCreateDTO));
    }

    private static Stream<Arguments> provideDrinkSuggestionCreateDTOWithNonExistingBartenderId() {
        DrinkSuggestionCreateDTO drinkSuggestionCreateDTO = new DrinkSuggestionCreateDTO();
        drinkSuggestionCreateDTO.setBartenderId(DrinkSuggestionConstants.NON_EXISTENT_ID);
        drinkSuggestionCreateDTO.setDescription(DrinkSuggestionConstants.DESCRIPTION);

        DrinkCreateDTO drinkCreateDTO = new DrinkCreateDTO();
        drinkCreateDTO.setName(DrinkSuggestionConstants.NEW_DRINK_NAME);
        drinkCreateDTO.setDescription(DrinkSuggestionConstants.NEW_DRINK_DESCRIPTION);
        drinkCreateDTO.setIngredients(DrinkSuggestionConstants.NEW_DRINK_INGREDIENTS);
        drinkCreateDTO.setPreparationTime(DrinkSuggestionConstants.NEW_DRINK_PREPARATION_TIME);
        drinkCreateDTO.setIsAlcoholic(DrinkSuggestionConstants.NEW_DRINK_IS_ALCOHOLIC);

        drinkSuggestionCreateDTO.setDrink(drinkCreateDTO);

        return Stream.of(Arguments.of(drinkSuggestionCreateDTO));
    }
}
