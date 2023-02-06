package aldentebackend.controller;

import aldentebackend.constants.FoodConstants;
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
public class FoodControllerIntegrationTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private static final String URL_PREFIX = "/api/food";


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
    public void getAllFood_isOk() throws Exception {
        login(UserConstants.VALID_ADMIN_USERNAME, UserConstants.VALID_PASSWORD);
        mockMvc.perform(get(URL_PREFIX))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$", Matchers.hasSize(FoodConstants.FOOD_COUNT))
                );
    }

    @Test
    public void getFood_calledWithNonExistingId_isNotFound() throws Exception {
        login(UserConstants.VALID_ADMIN_USERNAME, UserConstants.VALID_PASSWORD);
        mockMvc.perform(get(URL_PREFIX + "/" + FoodConstants.NON_EXISTING_ID))
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @Test
    public void getFood_calledWithValidId_isOk() throws Exception {
        login(UserConstants.VALID_ADMIN_USERNAME, UserConstants.VALID_PASSWORD);
        mockMvc.perform(get(URL_PREFIX + "/" + FoodConstants.ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(FoodConstants.ID),
                        jsonPath("$.name").value(FoodConstants.NAME),
                        jsonPath("$.description").value(FoodConstants.DESCRIPTION),
                        jsonPath("$.ingredients").value(FoodConstants.INGREDIENTS),
                        jsonPath("$.preparationTime").value(FoodConstants.PREPARATION_TIME)
                );
    }

    @Test
    public void deleteFood_calledWithNonExistingId_isNotFound() throws Exception {
        login(UserConstants.VALID_ADMIN_USERNAME, UserConstants.VALID_PASSWORD);
        mockMvc.perform(delete(URL_PREFIX + "/" + FoodConstants.NON_EXISTING_ID))
                .andExpectAll(
                        status().isNotFound()
                );
    }

}
