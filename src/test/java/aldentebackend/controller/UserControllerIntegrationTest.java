package aldentebackend.controller;

import aldentebackend.constants.UserConstants;
import aldentebackend.dto.authentication.AuthenticationRequestDTO;
import aldentebackend.dto.user.UserCreateDTO;
import aldentebackend.dto.user.UserUpdateDTO;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private static final String URL_PREFIX = "/api/users";

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
    public void getAllUsers_isOk() throws Exception {
        login(UserConstants.VALID_MANAGER_USERNAME, UserConstants.VALID_PASSWORD);

        mockMvc.perform(get(URL_PREFIX))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$", Matchers.hasSize(UserConstants.USERS_COUNT))
                );
    }

    @Test
    public void getOneUser_calledWithNonExistingId_isNotFound() throws Exception {
        login(UserConstants.VALID_MANAGER_USERNAME, UserConstants.VALID_PASSWORD);

        mockMvc.perform(get(URL_PREFIX + "/" + UserConstants.NON_EXISTENT_ID))
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @Test
    public void getOneUser_calledWithValidId_isOk() throws Exception {
        login(UserConstants.VALID_MANAGER_USERNAME, UserConstants.VALID_PASSWORD);

        mockMvc.perform(get(URL_PREFIX + "/" + UserConstants.EXISTING_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(UserConstants.EXISTING_ID),
                        jsonPath("$.firstName").value(UserConstants.EXISTING_MANAGER_FIRST_NAME),
                        jsonPath("$.lastName").value(UserConstants.EXISTING_MANAGER_LAST_NAME),
                        jsonPath("$.username").value(UserConstants.EXISTING_MANAGER_USERNAME),
                        jsonPath("$.phoneNumber").value(UserConstants.EXISTING_MANAGER_PHONE_NUMBER)
                );
    }

    @Test
    public void getWorkersCount_isOk() throws Exception {
        login(UserConstants.VALID_MANAGER_USERNAME, UserConstants.VALID_PASSWORD);

        mockMvc.perform(get(URL_PREFIX + "/count-workers"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$", Matchers.equalToObject(UserConstants.WORKERS_COUNT))
                );
    }

    @Test
    public void getAllWorkers_isOk() throws Exception {
        login(UserConstants.VALID_MANAGER_USERNAME, UserConstants.VALID_PASSWORD);

        mockMvc.perform(get(URL_PREFIX + "/workers"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$", Matchers.hasSize(UserConstants.WORKERS_COUNT))
                );
    }

    @Test
    public void deleteUser_calledWithValidId_isOk() throws Exception {
        login(UserConstants.VALID_MANAGER_USERNAME, UserConstants.VALID_PASSWORD);

        mockMvc.perform(delete(URL_PREFIX + "/" + UserConstants.EXISTING_ID))
                .andExpectAll(
                        status().isNoContent()
                );
    }

    @Test
    public void deleteUser_calledWithNonExistingId_isNotFound() throws Exception {
        login(UserConstants.VALID_MANAGER_USERNAME, UserConstants.VALID_PASSWORD);

        mockMvc.perform(delete(URL_PREFIX + "/" + UserConstants.NON_EXISTENT_ID))
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @ParameterizedTest
    @MethodSource("provideUserCreateDTOWithBlankValueOnNotBlankAttribute")
    public void createUser_calledWithInvalidData_isBadRequest(UserCreateDTO userCreateDTO) throws Exception {
        login(UserConstants.VALID_MANAGER_USERNAME, UserConstants.VALID_PASSWORD);

        mockMvc.perform(post(URL_PREFIX)
                .contentType("application/json")
                .content(mapper.writeValueAsString(userCreateDTO)))
                .andExpectAll(
                        status().isBadRequest()
                );
    }

    @Test
    public void createUser_calledWithValidData_isCreated() throws Exception {
        login(UserConstants.VALID_MANAGER_USERNAME, UserConstants.VALID_PASSWORD);
        UserCreateDTO userCreateDTO = new UserCreateDTO("Micko", "Loznica", "06506260", "mickoloz", "mickoloz123", "BARTENDER", "60520");

        mockMvc.perform(post(URL_PREFIX)
                .contentType("application/json")
                .content(mapper.writeValueAsString(userCreateDTO)))
                .andExpectAll(
                        status().isCreated()
                );
    }

    @Test
    public void updateUser_calledWithValidData_isOk() throws Exception {
        login(UserConstants.VALID_MANAGER_USERNAME, UserConstants.VALID_PASSWORD);
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO(UserConstants.EXISTING_ID, "Micko", "Loznica", "06506260", "mickoloz", "mickoloz123", "BARTENDER");

        mockMvc.perform(put(URL_PREFIX + "/" + UserConstants.EXISTING_ID)
                .contentType("application/json")
                .content(mapper.writeValueAsString(userUpdateDTO)))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(UserConstants.EXISTING_ID),
                        jsonPath("$.firstName").value("Micko")
                );
    }

    @Test
    public void updateUser_calledWithInvalidData_isNotFound() throws Exception {
        login(UserConstants.VALID_MANAGER_USERNAME, UserConstants.VALID_PASSWORD);
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO(UserConstants.NON_EXISTENT_ID, "Micko", "Loznica", "06506260", "mickoloz", "mickoloz123", "BARTENDER");

        mockMvc.perform(put(URL_PREFIX + "/" + UserConstants.NON_EXISTENT_ID)
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(userUpdateDTO)))
                .andExpectAll(
                        status().isNotFound()
                );
    }

    private static Stream<Arguments> provideUserCreateDTOWithBlankValueOnNotBlankAttribute() {
        return Stream.of(
                Arguments.of(new UserCreateDTO("", "Last Name", "Phone Number", "Username", "Password", "Role", "Salary")),
                Arguments.of(new UserCreateDTO("First Name", "", "Phone Number", "Username", "Password", "Role", "Salary")),
                Arguments.of(new UserCreateDTO("First Name", "Last Name", "", "Username", "Password", "Role", "Salary")),
                Arguments.of(new UserCreateDTO("First Name", "Last Name", "Phone Number", "", "Password", "Role", "Salary")),
                Arguments.of(new UserCreateDTO("First Name", "Last Name", "Phone Number", "Username", "", "Role", "Salary")),
                Arguments.of(new UserCreateDTO("First Name", "Last Name", "Phone Number", "Username", "Password", "", "Salary")),
                Arguments.of(new UserCreateDTO("First Name", "Last Name", "Phone Number", "Username", "Password", "Role", ""))
        );
    }

}
