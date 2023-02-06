package aldentebackend.controller;

import aldentebackend.constants.OrderConstants;
import aldentebackend.constants.UserConstants;
import aldentebackend.dto.authentication.AuthenticationRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerIntegrationTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private static final String URL_PREFIX = "/api/orders";


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
    public void getOrders_isOk() throws Exception {
        login(UserConstants.VALID_ADMIN_USERNAME, UserConstants.VALID_PASSWORD);
        mockMvc.perform(get(URL_PREFIX))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$", Matchers.hasSize(OrderConstants.ORDERS_COUNT))
                );
    }

    @Test
    public void getOrder_calledWithNonExistingId_isNotFound() throws Exception {
        login(UserConstants.VALID_ADMIN_USERNAME, UserConstants.VALID_PASSWORD);
        mockMvc.perform(get(URL_PREFIX + "/" + OrderConstants.NON_EXISTING_ID))
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @Test
    public void getOrder_calledWithValidId_isOk() throws Exception {
        login(UserConstants.VALID_ADMIN_USERNAME, UserConstants.VALID_PASSWORD);
        mockMvc.perform(get(URL_PREFIX + "/" + OrderConstants.ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(OrderConstants.ID),
                        jsonPath("$.status").value(OrderConstants.STATUS),
                        jsonPath("$.table.id").value(OrderConstants.TABLE_ID),
                        jsonPath("$.waiter.id").value(OrderConstants.WAITER_ID)
                );
    }

    @ParameterizedTest
    @ValueSource(longs = {-10, -1, 420, 69})
    public void getOrderItemsForOrder_calledWithNonExistingOrderId(Long nonExistingId) throws Exception {
        login(UserConstants.VALID_ADMIN_USERNAME, UserConstants.VALID_PASSWORD);
        mockMvc.perform(get(URL_PREFIX + "/" + nonExistingId + "/order-items"))
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @ParameterizedTest
    @ValueSource(longs = {10035} )
    public void getOrderItemsForOrder_calledWithValidOrderId(Long orderId) throws Exception {
        login(UserConstants.VALID_ADMIN_USERNAME, UserConstants.VALID_PASSWORD);
        mockMvc.perform(get(URL_PREFIX + "/" + orderId + "/order-items"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$", hasSize(2)),
                        jsonPath("$[0].orderId").value(orderId),
                        jsonPath("$[1].orderId").value(orderId)
                );
    }
}
