package com.assignment.eagleBank.controllers;

import com.assignment.eagleBank.dtos.LoginUserDto;
import com.assignment.eagleBank.entity.User;
import com.assignment.eagleBank.responses.LoginResponse;
import com.assignment.eagleBank.services.AuthenticationService;
import com.assignment.eagleBank.services.JwtService;
import com.assignment.eagleBank.services.utils.InputValidation;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/login")
@RestController
public class LoginController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public LoginController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) throws BadRequestException {
        if (InputValidation.isEmptyInput(loginUserDto.getEmail())||
                InputValidation.isEmptyInput(loginUserDto.getPassword()))
        {
            throw new BadRequestException("Invalid details supplied");
        }

        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}