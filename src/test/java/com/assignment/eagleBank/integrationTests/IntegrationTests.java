package com.assignment.eagleBank.IntegrationTests;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class IntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    private final static String RANDOM_USER_EMAIL = UUID.randomUUID().toString().substring(0, 8) + "@test.com";
    private static String TOKEN;
    private static String ACCOUNT;

    @Test
    @Order(1)
    void whenAllRequiredDetailsAreProvided_userIsSuccessfullyRegistered() throws Exception {
        String registrationDetails = "{\n" +
                "    \"email\": \"" + RANDOM_USER_EMAIL + "\",\n" +
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

        MvcResult registrationResult = this.mockMvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registrationDetails))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String resultContent = registrationResult.getResponse().getContentAsString();
        assertThat(resultContent).contains("\"id\":\"usr-");
    }

    @Test
    @Order(2)
    void whenAllRequiredDetailsAreProvided_userIsSuccessfullyLoggedIn() throws Exception {
        String loginDetails = "{\n" +
                "    \"email\": \"" + RANDOM_USER_EMAIL + "\",\n" +
                "    \"password\": \"password\"\n" +
                "}";

        MvcResult loginResult = this.mockMvc.perform(post("/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginDetails))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String resultLoginResponse = loginResult.getResponse().getContentAsString();
        assertThat(resultLoginResponse).contains("token");

        String tokenUnstripped = resultLoginResponse.split(":")[1].split(",")[0];
        TOKEN = tokenUnstripped.substring(1, tokenUnstripped.length() - 1);
        System.out.println(TOKEN);
    }

    //TODO fetch user details

    @Test
    @Order(3)
    void whenAllRequiredDetailsAreProvided_userCanCreateAnAccount() throws Exception {
        String createAccountDetails = "{\n" +
                "    \"type\": \"PERSONAL\",\n" +
                "    \"name\": \"peronsal-account\"\n" +
                "}";

        MvcResult createAccountResult = this.mockMvc.perform(post("/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createAccountDetails)
                        .header("Authorization", "Bearer " + TOKEN))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String createAccountResultString = createAccountResult.getResponse().getContentAsString();
        assertThat(createAccountResultString).contains("\"accountNumber\":\"01");

        ACCOUNT = createAccountResultString.split(",")[0].split(":")[1].substring(1,9);
    }

    //TODO fetch account details

    @Test
    @Order(4)
    void whenAllRequiredDetailsAreProvided_userCanCreateATransaction() throws Exception {

        String createTransactionDetails = "{\n" +
                "    \"amount\": \"30.00\",\n" +
                "    \"type\": \"DEPOSIT\",\n" +
                "    \"currency\": \"GBP\"\n" +
                "}";

        String createTransactionUrl = "/v1/accounts/" + ACCOUNT + "/transactions";
        MvcResult createTransactionResult = this.mockMvc.perform(post(createTransactionUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createTransactionDetails)
                        .header("Authorization", "Bearer " + TOKEN))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String createTransactionResultString = createTransactionResult.getResponse().getContentAsString();
        assertThat(createTransactionResultString).contains("\"transactionId\":\"tan-");
    }

    //TODO fetch transaction details

}