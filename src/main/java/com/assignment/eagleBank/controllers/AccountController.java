package com.assignment.eagleBank.controllers;

import com.assignment.eagleBank.dtos.NewAccountDto;
import com.assignment.eagleBank.entity.Account;
import com.assignment.eagleBank.entity.User;
import com.assignment.eagleBank.services.AccountService;
import com.assignment.eagleBank.services.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/accounts")
@RestController
@AllArgsConstructor
public class AccountController {
    private final AuthenticationService authenticationService;
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody NewAccountDto newAccountDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        //TODO validation of input here

        Account account = accountService.createAccount(newAccountDto, currentUser);

        return ResponseEntity.ok(account);



    }

}
