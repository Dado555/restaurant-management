package aldentebackend.controller;

import aldentebackend.constants.FoodSuggestionConstants;
import aldentebackend.constants.MenuItemConstants;
import aldentebackend.constants.OrderConstants;
import aldentebackend.constants.UserConstants;
import aldentebackend.dto.authentication.AuthenticationRequestDTO;
import aldentebackend.dto.item.FoodCreateDTO;
import aldentebackend.dto.orderitem.OrderItemCreateDTO;
import aldentebackend.dto.suggestion.FoodSuggestionCreateDTO;
import aldentebackend.model.enums.OrderItemStatus;
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

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FoodSuggestionControllerIntegrationTest {
    
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private static final String URL_PREFIX = "/api/food-suggestions";


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
    public void getAllFoodSuggestions_isOk() throws Exception {
        login(UserConstants.VALID_ADMIN_USERNAME, UserConstants.VALID_PASSWORD);
        mockMvc.perform(get(URL_PREFIX))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$", Matchers.hasSize(FoodSuggestionConstants.FOOD_SUGGESTION_COUNT))
                );
    }

    @Test
    public void getFoodSuggestion_calledWithNonExistingId_isNotFound() throws Exception {
        login(UserConstants.VALID_ADMIN_USERNAME, UserConstants.VALID_PASSWORD);
        mockMvc.perform(get(URL_PREFIX + "/" + FoodSuggestionConstants.NON_EXISTING_ID))
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @Test
    public void getFoodSuggestion_calledWithValidId_isOk() throws Exception {
        login(UserConstants.VALID_ADMIN_USERNAME, UserConstants.VALID_PASSWORD);
        mockMvc.perform(get(URL_PREFIX + "/" + FoodSuggestionConstants.ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(FoodSuggestionConstants.ID),
                        jsonPath("$.cookId").value(FoodSuggestionConstants.COOK_ID),
                        jsonPath("$.food.id").value(FoodSuggestionConstants.FOOD_ID)
                );
    }

    @ParameterizedTest
    @MethodSource("provideFoodSuggestionCreateDTOWithNonExistingCookId")
    public void createFoodSuggestion_calledWithNonExistingCookId_isNotFound(FoodSuggestionCreateDTO foodSuggestion) throws Exception {
        login(UserConstants.VALID_COOK_USERNAME, UserConstants.VALID_MASTER_PASSWORD);

        mockMvc.perform(post(URL_PREFIX )
                .contentType("application/json")
                .content(mapper.writeValueAsString(foodSuggestion)))
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @ParameterizedTest
    @MethodSource("provideValidFoodSuggestionCreateDTO")
    public void createFoodSuggestion_calledWithValidData_isOk(FoodSuggestionCreateDTO foodSuggestion) throws Exception {
        login(UserConstants.VALID_COOK_USERNAME, UserConstants.VALID_MASTER_PASSWORD);

        mockMvc.perform(post(URL_PREFIX )
                .contentType("application/json")
                .content(mapper.writeValueAsString(foodSuggestion)))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.cookId").value(FoodSuggestionConstants.COOK_ID),
                        jsonPath("$.food.name").value(FoodSuggestionConstants.NEW_FOOD_NAME),
                        jsonPath("$.food.description").value(FoodSuggestionConstants.NEW_FOOD_DESCRIPTION),
                        jsonPath("$.food.ingredients").value(FoodSuggestionConstants.NEW_FOOD_INGREDIENTS),
                        jsonPath("$.food.preparationTime").value(FoodSuggestionConstants.NEW_FOOD_PREPARATION_TIME)
                );
    }

    private static Stream<Arguments> provideValidFoodSuggestionCreateDTO() {
        FoodSuggestionCreateDTO foodSuggestion = new FoodSuggestionCreateDTO();
        foodSuggestion.setCookId(FoodSuggestionConstants.COOK_ID);
        foodSuggestion.setDescription(FoodSuggestionConstants.DESCRIPTION);

        FoodCreateDTO food = new FoodCreateDTO();
        food.setName(FoodSuggestionConstants.NEW_FOOD_NAME);
        food.setDescription(FoodSuggestionConstants.NEW_FOOD_DESCRIPTION);
        food.setIngredients(FoodSuggestionConstants.NEW_FOOD_INGREDIENTS);
        food.setPreparationTime(FoodSuggestionConstants. NEW_FOOD_PREPARATION_TIME);

        foodSuggestion.setFood(food);

        return Stream.of(Arguments.of(foodSuggestion));
    }

    private static Stream<Arguments> provideFoodSuggestionCreateDTOWithNonExistingCookId() {
        FoodSuggestionCreateDTO foodSuggestion = new FoodSuggestionCreateDTO();
        foodSuggestion.setCookId(FoodSuggestionConstants.NON_EXISTING_ID);
        foodSuggestion.setDescription(FoodSuggestionConstants.DESCRIPTION);

        FoodCreateDTO food = new FoodCreateDTO();
        food.setName(FoodSuggestionConstants.NEW_FOOD_NAME);
        food.setDescription(FoodSuggestionConstants.NEW_FOOD_DESCRIPTION);
        food.setIngredients(FoodSuggestionConstants.NEW_FOOD_INGREDIENTS);
        food.setPreparationTime(FoodSuggestionConstants. NEW_FOOD_PREPARATION_TIME);

        foodSuggestion.setFood(food);

        return Stream.of(Arguments.of(foodSuggestion));
    }
}
