package com.assignment.eagleBank.controllers;

import com.assignment.eagleBank.entity.User;
import com.assignment.eagleBank.dtos.RegisterUserDto;
import com.assignment.eagleBank.exceptions.BadRequestException;
import com.assignment.eagleBank.services.AuthenticationService;
import com.assignment.eagleBank.services.JwtService;
import com.assignment.eagleBank.services.utils.InputValidation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/v1/users")
@RestController
@AllArgsConstructor
public class UserController {
    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<Object> register(@RequestBody RegisterUserDto registerUserDto) throws BadRequestException {
        if (!InputValidation.isValidEMail(registerUserDto.getEmail())) {
            return ResponseEntity.badRequest().body("Invalid email supplied");
        }

        if (InputValidation.isEmptyInput(registerUserDto.getAddress().getLine1()) ||
                InputValidation.isEmptyInput(registerUserDto.getAddress().getTown()) ||
                InputValidation.isEmptyInput(registerUserDto.getAddress().getCounty()) ||
                InputValidation.isEmptyInput(registerUserDto.getAddress().getPostcode())) {
            return ResponseEntity.badRequest().body("Invalid details supplied");
        }

        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> fetchUserDetails(@PathVariable String userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        //TODO there's a requirement to return a user not found error if the user exists, however it is considered a
        // bad practice as it discloses information about a user other than the one which is logged in.
        if (!currentUser.getUsername().equals(userId)) {
            throw new AccessDeniedException("Access denied");
        }

        return ResponseEntity.ok(currentUser);
    }

}