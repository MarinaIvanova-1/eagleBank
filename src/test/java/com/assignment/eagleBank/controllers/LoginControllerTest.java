package com.assignment.eagleBank.controllers;

import com.assignment.eagleBank.TestUtils;
import com.assignment.eagleBank.entity.Address;
import com.assignment.eagleBank.entity.User;
import com.assignment.eagleBank.repositories.UserRepository;
import com.assignment.eagleBank.services.utils.IdGenerator;
import org.glassfish.jaxb.core.v2.TODO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//TODO Use sql annotation to populate the test data?
//i.e.@Sql(scripts = {"/employees_schema.sql", "/import_employees.sql"}, executionPhase = BEFORE_TEST_CLASS)
//@Sql(scripts = {"/delete_employees_data.sql"}, executionPhase = AFTER_TEST_CLASS)

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {
    @Autowired
    private LoginController loginController;
    @Autowired
    private MockMvc mockMvc;
//    @Autowired
//    UserRepository userRepository;
//    private static String testEmail = "test@gmail.com";
//
//    @BeforeAll
//    static void setUpData() {
//        var address = new Address()
//                .setLine1("Line 1 of address")
//                .setTown("Test town")
//                .setCounty("Test county")
//                .setPostcode("Test postcode");
//        var user = new User()
//                .setName("test name")
//                .setEmail(testEmail)
//                .setPhoneNumber("+1111111111111")
//                .setAddress(address)
//                .setPassword("password")
//                .setId(IdGenerator.generateUserId());
//
//        userRepository.save(user);

//    }

    @Test
    void whenLoginDetailsAreCorrect_thenLogsInSuccessfully() throws Exception {

//        TestUtils testUtils = new TestUtils();
        String testEmail = UUID.randomUUID().toString().substring(0, 8) + "@test.com";
//        User user = testUtils.saveUserToDb(testEmail);

        MvcResult result = this.mockMvc.perform(post("/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"" + testEmail + "\",\"password\":\"password\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        assertThat(resultContent).contains("token");
    }

    @Test
    void whenLoginDetailsAreInvalid_thenThrowError() throws Exception {
        this.mockMvc.perform(post("/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"non-existent-email@lkj.com\",\"password\":\"wrong-password\"}"))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadCredentialsException));
    }

    @Test
    void contextLoads() throws Exception {
        assertThat(loginController).isNotNull();
    }

//    @Test
//    void databaseTest() {
//        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
//        populator.addScripts(
//                new ClassPathResource("test-schema.sql"),
//                new ClassPathResource("test-data.sql"));
//        populator.setSeparator("@@");
//        populator.execute(this.dataSource);
//        // run code that uses the test schema and data
//    }
}
