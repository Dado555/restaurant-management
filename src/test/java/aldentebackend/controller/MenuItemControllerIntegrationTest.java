package aldentebackend.controller;

import aldentebackend.constants.UserConstants;
import aldentebackend.dto.authentication.AuthenticationRequestDTO;
import aldentebackend.dto.meniitemcategory.MenuItemCategoryInfoDTO;
import aldentebackend.dto.menuitem.MenuItemCreateDTO;
import aldentebackend.dto.price.PriceCreateDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MenuItemControllerIntegrationTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private static final String URL_PREFIX = "/api/menu-items";

    private String adminToken;
    private String managerToken;
    private String waiterToken;
    private String cookToken;
    private String bartenderToken;

    private String login(String username, String password) throws Exception {
        String body = mapper.writeValueAsString(new AuthenticationRequestDTO(username, password));

        MvcResult result = mockMvc.perform(post("/api/authentication/login")
                                    .contentType("application/json")
                                    .content(body))
                                    .andExpect(status().isOk())
                                    .andReturn();

        return result.getResponse().getContentAsString();
    }

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        this.adminToken = login(UserConstants.VALID_ADMIN_USERNAME, UserConstants.VALID_PASSWORD);
        this.managerToken = login(UserConstants.VALID_MANAGER_USERNAME, UserConstants.VALID_PASSWORD);
        this.waiterToken = login(UserConstants.VALID_WAITER_USERNAME, UserConstants.VALID_MASTER_PASSWORD);
        this.cookToken = login(UserConstants.VALID_COOK_USERNAME, UserConstants.VALID_MASTER_PASSWORD);
        this.bartenderToken = login(UserConstants.VALID_BARTENDER_USERNAME, UserConstants.VALID_MASTER_PASSWORD);
    }

    @ParameterizedTest
    @MethodSource("provideMenuItemCreateDTOWithNullValueForNonNullAttribute")
    public void createMenuItem_calledWithNullValueForNonNullAttribute_isBadRequest(MenuItemCreateDTO menuItemCreateDTO) throws Exception {
        mockMvc.perform(post(URL_PREFIX)
                .header("Authorization", "Bearer " + waiterToken)
                .contentType("application/json")
                .content(mapper.writeValueAsString(menuItemCreateDTO)))
                .andExpectAll(
                        status().isBadRequest()
                );
    }

    private static Stream<Arguments> provideMenuItemCreateDTOWithNullValueForNonNullAttribute() {
        return Stream.of(
                Arguments.of(new MenuItemCreateDTO(null, new PriceCreateDTO(), new MenuItemCategoryInfoDTO(1L, ""), null, "food"))
        );
    }
}
