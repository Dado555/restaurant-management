package aldentebackend.controller;

import aldentebackend.constants.TablesConstants;
import aldentebackend.constants.UserConstants;
import aldentebackend.dto.authentication.AuthenticationRequestDTO;
import aldentebackend.dto.sector.SectorCreateDTO;
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

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TableControllerIntegrationTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private static final String URL_PREFIX = "/api/tables";

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
    public void getTables_isOk() throws Exception {
        login(UserConstants.VALID_WAITER_USERNAME, UserConstants.VALID_MASTER_PASSWORD);
        mockMvc.perform(get(URL_PREFIX))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$", hasSize(TablesConstants.TABLES_COUNT))
                );
    }


    @Test
    public void getTablesInSector_calledWithValidSector_isOk() throws Exception {
        login(UserConstants.VALID_WAITER_USERNAME, UserConstants.VALID_MASTER_PASSWORD);
        mockMvc.perform(get(URL_PREFIX + "/sector/" + TablesConstants.SECTOR_1_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$", hasSize(TablesConstants.TABLES_IN_S1))
                );
    }

    @Test
    public void getTablesInSector_calledWithInvalidSector_isEmpty() throws Exception {
        login(UserConstants.VALID_WAITER_USERNAME, UserConstants.VALID_MASTER_PASSWORD);
        mockMvc.perform(get(URL_PREFIX + "/sector/" + TablesConstants.SECTOR_NV_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$", hasSize(0))
                );
    }

    @Test
    public void deleteSector_calledWithExistingId_isNoContent() throws Exception {
        login(UserConstants.VALID_WAITER_USERNAME, UserConstants.VALID_MASTER_PASSWORD);
        mockMvc.perform(delete(URL_PREFIX + "/sector/" + TablesConstants.SECTOR_1_ID))
                .andExpectAll(
                        status().isNoContent()
                );
    }

    @Test
    public void deleteSector_calledWithNonExistingId_isNotFound() throws Exception {
        login(UserConstants.VALID_WAITER_USERNAME, UserConstants.VALID_MASTER_PASSWORD);
        mockMvc.perform(delete(URL_PREFIX + "/sector/" + TablesConstants.SECTOR_NV_ID))
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @Test
    public void getAllSectors_isOk() throws Exception {
        login(UserConstants.VALID_WAITER_USERNAME, UserConstants.VALID_MASTER_PASSWORD);
        mockMvc.perform(get(URL_PREFIX + "/all-sectors"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$", hasSize(2))
                );
    }

    @Test
    public void addSector_isOk() throws Exception {
        login(UserConstants.VALID_WAITER_USERNAME, UserConstants.VALID_MASTER_PASSWORD);
        SectorCreateDTO sectorDTO = new SectorCreateDTO("sector");

        mockMvc.perform(get(URL_PREFIX + "/all-sectors"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$", hasSize(TablesConstants.SECTORS_COUNT))
                );

        MvcResult result = mockMvc.perform(post(URL_PREFIX + "/sector")
                .contentType("application/json")
                .content(mapper.writeValueAsString(sectorDTO)))
                .andExpectAll(
                        status().isOk()
                ).andReturn();

        mockMvc.perform(get(URL_PREFIX + "/all-sectors"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$", hasSize(TablesConstants.SECTORS_COUNT + 1))
                )
                .andExpect(jsonPath("$[*].name", Matchers.hasItem(sectorDTO.getName())))
            ;
    }

    @Test
    public void addSector_badRequest() throws Exception {
        login(UserConstants.VALID_WAITER_USERNAME, UserConstants.VALID_MASTER_PASSWORD);
        SectorCreateDTO sectorDTO = new SectorCreateDTO("");

        MvcResult result = mockMvc.perform(post(URL_PREFIX + "/sector")
                .contentType("application/json")
                .content(mapper.writeValueAsString(sectorDTO)))
                .andExpectAll(
                        status().isBadRequest()
                ).andReturn();


    }

    @Test
    public void updateSector_isOk() throws Exception {
        login(UserConstants.VALID_WAITER_USERNAME, UserConstants.VALID_MASTER_PASSWORD);
        SectorCreateDTO sectorDTO = new SectorCreateDTO("sector");

        mockMvc.perform(get(URL_PREFIX + "/all-sectors"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$", hasSize(TablesConstants.SECTORS_COUNT))
                );

        MvcResult result = mockMvc.perform(post(URL_PREFIX + "/sector")
                .contentType("application/json")
                .content(mapper.writeValueAsString(sectorDTO)))
                .andExpectAll(
                        status().isOk()
                ).andReturn();

        mockMvc.perform(get(URL_PREFIX + "/all-sectors"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$", hasSize(TablesConstants.SECTORS_COUNT + 1))
                )
                .andExpect(jsonPath("$[*].name", Matchers.hasItem(sectorDTO.getName())))
        ;
    }




}
