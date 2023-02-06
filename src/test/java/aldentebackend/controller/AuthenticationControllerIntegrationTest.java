package aldentebackend.controller;

import aldentebackend.constants.OrderItemConstants;
import aldentebackend.constants.UserConstants;
import aldentebackend.dto.authentication.AuthenticationMasterPasswordDTO;
import aldentebackend.dto.authentication.AuthenticationRequestDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.WebApplicationContext;

import javax.validation.Valid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerIntegrationTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private static final String URL_PREFIX = "/api/authentication";

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void login_calledWithInvalidUsername_isNotFound() throws Exception {
        String body = mapper.writeValueAsString(new AuthenticationRequestDTO(UserConstants.INVALID_USERNAME, UserConstants.VALID_PASSWORD));

        mockMvc.perform(post(URL_PREFIX + "/login")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isNotFound());
    }

    @Test
    public void login_calledWithInvalidPassword_isBadCredentials() throws Exception {
        String body = mapper.writeValueAsString(new AuthenticationRequestDTO(UserConstants.VALID_ADMIN_USERNAME, UserConstants.INVALID_PASSWORD));

        mockMvc.perform(post(URL_PREFIX + "/login")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isConflict());
    }

    @Test
    public void login_calledWithValidData_isOk() throws Exception {
        String body = mapper.writeValueAsString(new AuthenticationRequestDTO(UserConstants.VALID_ADMIN_USERNAME, UserConstants.VALID_PASSWORD));

        mockMvc.perform(post(URL_PREFIX + "/login")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isOk());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();
        if (principal instanceof org.springframework.security.core.userdetails.User) {
            org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) principal;

            assertEquals(UserConstants.VALID_ADMIN_USERNAME, user.getUsername());
        }
    }

    @Test
    public void loginMaster_calledWithInvalidPassword_isUnauthorized() throws Exception {
        String body = mapper.writeValueAsString(new AuthenticationMasterPasswordDTO(UserConstants.INVALID_PASSWORD));

        mockMvc.perform(post(URL_PREFIX + "/master-login")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void loginMaster_calledWithValidPassword_isOk() throws Exception {
        String body = mapper.writeValueAsString(new AuthenticationMasterPasswordDTO(UserConstants.VALID_MASTER_PASSWORD));

        mockMvc.perform(post(URL_PREFIX + "/master-login")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isOk());
    }
}