package com.assignment.eagleBank.controllers;

import com.assignment.eagleBank.TestUtils;
import com.assignment.eagleBank.dtos.LoginUserDto;
import com.assignment.eagleBank.entity.Address;
import com.assignment.eagleBank.entity.User;
import com.assignment.eagleBank.services.AuthenticationService;
import com.assignment.eagleBank.services.JwtService;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private UserController userController;
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private JwtService jwtService;
    @Mock private AuthenticationService authenticationService;
    private WebTestClient webClient;


    //TODO clean up database after the tests
    @Test
    void whenAllRequiredDetailsAreProvided_userIsSuccessfullyRegistered() throws Exception {
        String randomUserEmail = UUID.randomUUID().toString().substring(0, 8) + "@test.com";

        String registrationDetails = "{\n" +
                "    \"email\": \"" + randomUserEmail + "\",\n" +
                "    \"name\": \"John\",\n" +
                "    \"password\": \"password\",\n" +
                "    \"phoneNumber\": \"+123456789111\",\n" +
                "    \"address\": {\n" +
                "        \"line1\": \"Lline 1 of address\",\n" +
                "        \"town\": \"London\",\n" +
                "        \"county\": \"greater London\",\n" +
                "        \"postcode\": \"WC1A\"\n" +
                "    }\n" +
                "}";

        MvcResult result = this.mockMvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registrationDetails))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        assertThat(resultContent).contains("\"id\":\"usr-");
    }

    //TODO create parametrized test to assert for various incorrect inputs and validations
    @Test
    void whenIncompleteDetailsAreProvided_exceptionIsThrow() throws Exception {
        String registrationDetails = "{\n" +
                "    \"name\": \"John\",\n" +
                "    \"password\": \"password\",\n" +
                "    \"phoneNumber\": \"01234sdasdf56789\",\n" +
                "    \"address\": {\n" +
                "        \"line1\": \"Lline 1 of address\",\n" +
                "        \"town\": \"London\",\n" +
                "        \"county\": \"greater London\",\n" +
                "        \"postcode\": \"WC1A\"\n" +
                "    }\n" +
                "}";

        this.mockMvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registrationDetails))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadRequestException))
                .andExpect(result -> assertEquals("Invalid details supplied", result.getResolvedException().getMessage()));
    }
}
