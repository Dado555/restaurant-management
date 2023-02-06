package aldentebackend.controller;

import aldentebackend.constants.DrinkConstants;
import aldentebackend.constants.UserConstants;
import aldentebackend.dto.authentication.AuthenticationRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DrinkControllerIntegrationTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private static final String URL_PREFIX = "/api/drinks";


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
    public void getAllDrinks_isOk() throws Exception {
        login(UserConstants.VALID_ADMIN_USERNAME, UserConstants.VALID_PASSWORD);
        mockMvc.perform(get(URL_PREFIX))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$", Matchers.hasSize(DrinkConstants.DRINK_COUNT))
                );
    }

    @Test
    public void getDrink_calledWithNonExistingId_isNotFound() throws Exception {
        login(UserConstants.VALID_ADMIN_USERNAME, UserConstants.VALID_PASSWORD);
        mockMvc.perform(get(URL_PREFIX + "/" + DrinkConstants.NON_EXISTING_ID))
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @Test
    public void getDrink_calledWithValidId_isOk() throws Exception {
        login(UserConstants.VALID_ADMIN_USERNAME, UserConstants.VALID_PASSWORD);
        mockMvc.perform(get(URL_PREFIX + "/" + DrinkConstants.ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(DrinkConstants.ID),
                        jsonPath("$.name").value(DrinkConstants.NAME),
                        jsonPath("$.description").value(DrinkConstants.DESCRIPTION),
                        jsonPath("$.ingredients").value(DrinkConstants.INGREDIENTS),
                        jsonPath("$.preparationTime").value(DrinkConstants.PREPARATION_TIME),
                        jsonPath("$.isAlcoholic").value(DrinkConstants.IS_ALCOHOLIC)
                );
    }

    @Test
    public void deleteDrink_calledWithNonExistingId_isNotFound() throws Exception {
        login(UserConstants.VALID_ADMIN_USERNAME, UserConstants.VALID_PASSWORD);
        mockMvc.perform(delete(URL_PREFIX + "/" + DrinkConstants.NON_EXISTING_ID))
                .andExpectAll(
                        status().isNotFound()
                );
    }
}
