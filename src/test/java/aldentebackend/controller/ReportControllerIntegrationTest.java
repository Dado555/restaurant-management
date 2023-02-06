package aldentebackend.controller;

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
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class ReportControllerIntegrationTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private static final String URL_PREFIX = "/api/reports";

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

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

    @Test
    public void getAllSalaries_withoutAuthorizing_throwsNestedServletException() throws Exception {
        assertThrows(NestedServletException.class, () -> mockMvc.perform(get(URL_PREFIX + "/salaries")));
    }

    @Test
    public void getAllSalaries_withAuthorizing_isOk() throws Exception {
        login(UserConstants.VALID_MANAGER_USERNAME, UserConstants.VALID_PASSWORD);

        mockMvc.perform(get(URL_PREFIX + "/salaries"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$", Matchers.hasSize(UserConstants.WORKERS_WITH_SALARIES))
                );
    }

    @Test
    public void getReport_withoutAuthorizing_throwsNestedServletException() throws Exception {
        assertThrows(NestedServletException.class, () -> mockMvc.perform(get(URL_PREFIX + "?start=2021-11-11&end=2022-01-20")));
    }

    @Test
    public void getReport_withAuthorizing_isOk() throws Exception {
        login(UserConstants.VALID_MANAGER_USERNAME, UserConstants.VALID_PASSWORD);

        mockMvc.perform(get(URL_PREFIX + "?start=2021-11-11&end=2022-01-20"))
                .andExpectAll(
                        status().isOk()
                );
    }

}
