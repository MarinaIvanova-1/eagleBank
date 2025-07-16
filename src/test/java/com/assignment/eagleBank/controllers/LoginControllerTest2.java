package com.assignment.eagleBank.controllers;

import com.assignment.eagleBank.dtos.LoginUserDto;
import com.assignment.eagleBank.entity.Address;
import com.assignment.eagleBank.entity.User;
import com.assignment.eagleBank.repositories.UserRepository;
import com.assignment.eagleBank.services.utils.IdGenerator;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Date;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Testcontainers
public class LoginControllerTest2 {

    @Rule
    public MySQLContainer mysql = new MySQLContainer();
    @Container
    static MySQLContainer MY_SQL_CONTAINER;

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    private UserRepository  userRepository;

    static {
        MY_SQL_CONTAINER = new MySQLContainer("mysql:latest");
//                .withDatabaseName("test")
//                .withUsername("root")
//                .withPassword("secret");
        MY_SQL_CONTAINER.start();
    }

    @DynamicPropertySource
    static void configureTestProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url",() -> MY_SQL_CONTAINER.getJdbcUrl());
        registry.add("spring.datasource.username",() -> MY_SQL_CONTAINER.getUsername());
        registry.add("spring.datasource.password",() -> MY_SQL_CONTAINER.getPassword());
        registry.add("spring.jpa.hibernate.ddl-auto",() -> "create");
    }

    @BeforeEach
    public void beforeEach(){
        Address address = new Address();
        address.setLine1("Line 1 of address");
        address.setCounty("test county");
        address.setTown("test town");
        address.setPostcode("test postcode");

        User user = new User();
        user.setId("usr-test1234");
        user.setAddress(address);
        user.setEmail("test1@test.com");
        user.setName("test name");
        user.setPassword("test password");
        user.setCreatedAt(new Date());
        user.setPhoneNumber("0123456789");
        user.setUpdatedAt(new Date());
        userRepository.save(user);
    }

    @AfterEach
    public void afterEach(){
        userRepository.deleteAll();
    }

    @Test
    void saveOrderEntity() {

        Address address = new Address();
        address.setLine1("Line 1 of address");
        address.setCounty("test county");
        address.setTown("test town");
        address.setPostcode("test postcode");

        User user = new User();
        user.setId("usr-test5678");
        user.setAddress(address);
        user.setEmail("test1@test.com");
        user.setName("test name");
        user.setPassword("test password");
        user.setCreatedAt(new Date());
        user.setPhoneNumber("0123456789");
        user.setUpdatedAt(new Date());
        webTestClient.post()
                .uri("/v1/users")
                .bodyValue(user)
                .exchange()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(User.class)
                .consumeWith(returnedUser -> Assertions.assertNotNull(returnedUser.getResponseBody().getId()));
    }
}
