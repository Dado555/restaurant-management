package aldentebackend.controller;

import aldentebackend.constants.MenuItemConstants;
import aldentebackend.constants.OrderConstants;
import aldentebackend.constants.OrderItemConstants;
import aldentebackend.constants.UserConstants;
import aldentebackend.dto.authentication.AuthenticationRequestDTO;
import aldentebackend.dto.orderitem.OrderItemCreateDTO;
import aldentebackend.dto.orderitem.OrderItemUpdateDTO;
import aldentebackend.model.enums.OrderItemStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Objects;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class OrderItemControllerIntegrationTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private static final String URL_PREFIX = "/api/order-items";

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
    public void getOrderItems_isOk() throws Exception {
        login(UserConstants.VALID_ADMIN_USERNAME, UserConstants.VALID_PASSWORD);
        mockMvc.perform(get(URL_PREFIX))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$", hasSize(OrderItemConstants.ORDER_ITEMS_COUNT))
                );
    }

    @Test
    public void getOrderItem_calledWithNonExistingId_IsNotFound() throws Exception {
        login(UserConstants.VALID_ADMIN_USERNAME, UserConstants.VALID_PASSWORD);
        mockMvc.perform(get(URL_PREFIX + "/" + -69))
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @Test
    public void getOrderItem_calledWithValidId_IsOk() throws Exception {
        login(UserConstants.VALID_ADMIN_USERNAME, UserConstants.VALID_PASSWORD);

        mockMvc.perform(get(URL_PREFIX + "/" + OrderItemConstants.ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(OrderItemConstants.ID),
                        jsonPath("$.status").value(OrderItemConstants.STATUS),
                        jsonPath("$.menuItem.id").value(OrderItemConstants.MENU_ITEM_ID),
                        jsonPath("$.orderId").value(OrderItemConstants.ORDER_ID),
                        jsonPath("$.price.value").value(OrderItemConstants.CURRENT_PRICE)
                );
    }

    @ParameterizedTest
    @MethodSource("provideOrderItemItemCreateDTOWithNullValueOnNotNullAttribute")
    public void createOrderItem_calledWithNullValueOnNotNullAttribute_isBadRequest(OrderItemCreateDTO orderItemCreateDTO) throws Exception {
        login(UserConstants.VALID_BARTENDER_USERNAME, UserConstants.VALID_MASTER_PASSWORD);

        mockMvc.perform(post(URL_PREFIX)
                .contentType("application/json")
                .content(mapper.writeValueAsString(orderItemCreateDTO)))
                .andExpectAll(
                        status().isBadRequest()
                );
    }

    @Test
    public void createOrderItem_calledWithNonExistingOrderId_isNotFound() throws Exception {
        login(UserConstants.VALID_WAITER_USERNAME, UserConstants.VALID_MASTER_PASSWORD);

        OrderItemCreateDTO orderItemCreateDTO = new OrderItemCreateDTO(69420L, MenuItemConstants.ID, 20, OrderItemStatus.FOR_PREPARATION, "desc");
        mockMvc.perform(post(URL_PREFIX)
                .contentType("application/json")
                .content(mapper.writeValueAsString(orderItemCreateDTO)))
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @Test
    public void createOrderItem_calledWithNonExistingMenuItemId_isNotFound() throws Exception {
        login(UserConstants.VALID_WAITER_USERNAME, UserConstants.VALID_MASTER_PASSWORD);
        OrderItemCreateDTO orderItemCreateDTO = new OrderItemCreateDTO(OrderConstants.ID, 69420L, 20, OrderItemStatus.FOR_PREPARATION,"desc");

        mockMvc.perform(post(URL_PREFIX)
                .contentType("application/json")
                .content(mapper.writeValueAsString(orderItemCreateDTO)))
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @Test
    public void createOrderItem_calledWithNegativeAmount_isBadRequest() throws Exception {
        login(UserConstants.VALID_BARTENDER_USERNAME, UserConstants.VALID_MASTER_PASSWORD);
        OrderItemCreateDTO orderItemCreateDTO = new OrderItemCreateDTO(OrderConstants.ID, MenuItemConstants.ID, -20, OrderItemStatus.FOR_PREPARATION,"desc");

        mockMvc.perform(post(URL_PREFIX)
                .contentType("application/json")
                .content(mapper.writeValueAsString(orderItemCreateDTO)))
                .andExpectAll(
                        status().isBadRequest()
                );
    }

    @Test
    public void updateOrderItem_calledWithNonExistingId_isNotFound() throws Exception {
        login(UserConstants.VALID_WAITER_USERNAME, UserConstants.VALID_MASTER_PASSWORD);
        OrderItemUpdateDTO orderItemUpdateDTO = new OrderItemUpdateDTO(20, "desc");

        mockMvc.perform(put(URL_PREFIX + "/" + OrderItemConstants.NON_EXISTING_ID)
                .contentType("application/json")
                .content(mapper.writeValueAsString(orderItemUpdateDTO)))
                .andExpectAll(
                        status().isNotFound(),
                        result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo("Cannot find entity with id: " + OrderItemConstants.NON_EXISTING_ID)
                );
    }

    @ParameterizedTest
    @ValueSource(ints = {-10, -1, 0})
    public void updateOrderItem_calledWithInvalidAmount_isBadRequest(Integer amount) throws Exception {
        login(UserConstants.VALID_WAITER_USERNAME, UserConstants.VALID_MASTER_PASSWORD);
        OrderItemUpdateDTO orderItemUpdateDTO = new OrderItemUpdateDTO(amount, null);

        mockMvc.perform(put(URL_PREFIX + "/" + OrderItemConstants.ID)
                .contentType("application/json")
                .content(mapper.writeValueAsString(orderItemUpdateDTO)))
                .andExpectAll(
                        status().isBadRequest()
                );
    }

    @Test
    public void updateOrderItem_calledWithValidData_isOk() throws Exception {
        login(UserConstants.VALID_WAITER_USERNAME, UserConstants.VALID_MASTER_PASSWORD);
        OrderItemUpdateDTO orderItemUpdateDTO = new OrderItemUpdateDTO(20, "desc");

        mockMvc.perform(put(URL_PREFIX + "/" + OrderItemConstants.ID)
                .contentType("application/json")
                .content(mapper.writeValueAsString(orderItemUpdateDTO)))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(OrderItemConstants.ID),
                        jsonPath("$.amount").value(orderItemUpdateDTO.getAmount()),
                        jsonPath("$.description").value(orderItemUpdateDTO.getDescription())
                );
    }

    @Test
    public void markOrderItemAsAwaitingApproval_calledWithNonExistingId_isNotFound() throws Exception {
        login(UserConstants.VALID_WAITER_USERNAME, UserConstants.VALID_MASTER_PASSWORD);
        mockMvc.perform(put(URL_PREFIX + "/" + OrderItemConstants.NON_EXISTING_ID + "/awaiting-approval"))
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @Test
    public void markOrderItemAsAwaitingApproval_calledWithValidId_isOk() throws Exception {
        login(UserConstants.VALID_WAITER_USERNAME, UserConstants.VALID_MASTER_PASSWORD);
        mockMvc.perform(put(URL_PREFIX + "/" + OrderItemConstants.ID + "/awaiting-approval"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(OrderItemConstants.ID),
                        jsonPath("$.status").value(OrderItemStatus.AWAITING_APPROVAL.toString())
                );
    }

    @Test
    public void markOrderItemAsForPreparation_calledWithNonExistingId_isNotFound() throws Exception {
        login(UserConstants.VALID_WAITER_USERNAME, UserConstants.VALID_MASTER_PASSWORD);
        mockMvc.perform(put(URL_PREFIX + "/" + OrderItemConstants.NON_EXISTING_ID + "/for-preparation"))
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @Test
    public void markOrderItemAsForPreparation_calledWithValidId_isOk() throws Exception {
        login(UserConstants.VALID_WAITER_USERNAME, UserConstants.VALID_MASTER_PASSWORD);
        mockMvc.perform(put(URL_PREFIX + "/" + OrderItemConstants.ID_AWAITING_APPROVAL + "/for-preparation"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(OrderItemConstants.ID_AWAITING_APPROVAL),
                        jsonPath("$.status").value(OrderItemStatus.FOR_PREPARATION.toString())
                );
    }

    @Test
    public void cancelOrderItem_calledWithNonExistingId_isNotFound() throws Exception {
        login(UserConstants.VALID_WAITER_USERNAME, UserConstants.VALID_MASTER_PASSWORD);
        mockMvc.perform(put(URL_PREFIX + "/" + OrderItemConstants.NON_EXISTING_ID + "/canceled"))
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @Test
    public void takeOrderItem_calledWithNonExistingId_isNotFound() throws Exception {
        login(UserConstants.VALID_COOK_USERNAME, UserConstants.VALID_MASTER_PASSWORD);
        mockMvc.perform(put(URL_PREFIX + "/" + OrderItemConstants.NON_EXISTING_ID + "/in-progress"))
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @Test
    public void takeOrderItem_calledWithValidId_isOk() throws Exception {
        login(UserConstants.VALID_COOK_USERNAME, UserConstants.VALID_MASTER_PASSWORD);
        mockMvc.perform(put(URL_PREFIX + "/" + OrderItemConstants.ID + "/in-progress"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(OrderItemConstants.ID),
                        jsonPath("$.status").value(OrderItemStatus.IN_PROGRESS.toString())
                );
    }

    @Test
    public void markOrderItemAsReady_calledWithNonExistingId_isNotFound() throws Exception {
        login(UserConstants.VALID_COOK_USERNAME, UserConstants.VALID_MASTER_PASSWORD);
        mockMvc.perform(put(URL_PREFIX + "/" + OrderItemConstants.NON_EXISTING_ID + "/ready"))
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @Test
    public void markOrderItemAsReady_calledWithValidId_isOk() throws Exception {
        login(UserConstants.VALID_COOK_USERNAME, UserConstants.VALID_MASTER_PASSWORD);
        mockMvc.perform(put(URL_PREFIX + "/" + OrderItemConstants.ID_IN_PROGRESS + "/ready"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(OrderItemConstants.ID_IN_PROGRESS),
                        jsonPath("$.status").value(OrderItemStatus.READY.toString())
                );
    }

    @Test
    public void markOrderItemAsDelivered_calledWithNonExistingId_isNotFound() throws Exception {
        login(UserConstants.VALID_WAITER_USERNAME, UserConstants.VALID_MASTER_PASSWORD);
        mockMvc.perform(put(URL_PREFIX + "/" + OrderItemConstants.NON_EXISTING_ID + "/delivered"))
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @Test
    public void markOrderItemAsDelivered_calledWithValidId_isOk() throws Exception {
        login(UserConstants.VALID_WAITER_USERNAME, UserConstants.VALID_MASTER_PASSWORD);
        mockMvc.perform(put(URL_PREFIX + "/" + OrderItemConstants.ID_READY + "/delivered"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(OrderItemConstants.ID_READY),
                        jsonPath("$.status").value(OrderItemStatus.DELIVERED.toString())
                );
    }

    @Test
    public void deleteOrderItem_calledWithNonExistingId_isNotFound() throws Exception {
        login(UserConstants.VALID_WAITER_USERNAME, UserConstants.VALID_MASTER_PASSWORD);
        mockMvc.perform(delete(URL_PREFIX + "/" + OrderItemConstants.NON_EXISTING_ID))
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @Test
    public void deleteOrderItem_calledWithValidData_isOk() throws Exception {
        login(UserConstants.VALID_WAITER_USERNAME, UserConstants.VALID_MASTER_PASSWORD);
        mockMvc.perform(delete(URL_PREFIX + "/" + OrderItemConstants.ID_AWAITING_APPROVAL))
                .andExpectAll(
                        status().isOk()
                );
    }

    private static Stream<Arguments> provideOrderItemItemCreateDTOWithNullValueOnNotNullAttribute() {
        return Stream.of(
                Arguments.of(new OrderItemCreateDTO(null, MenuItemConstants.ID, 20, OrderItemStatus.FOR_PREPARATION,"desc")),
                Arguments.of(new OrderItemCreateDTO(OrderConstants.ID, null, -20, OrderItemStatus.FOR_PREPARATION,"desc")),
                Arguments.of(new OrderItemCreateDTO(OrderConstants.ID, MenuItemConstants.ID, null, OrderItemStatus.FOR_PREPARATION,"desc")),
                Arguments.of(new OrderItemCreateDTO(OrderConstants.ID, MenuItemConstants.ID, 20, null,"desc"))
        );
    }
}
