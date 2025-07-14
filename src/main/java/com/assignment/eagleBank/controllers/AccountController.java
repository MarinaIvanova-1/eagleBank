package com.assignment.eagleBank.controllers;

import com.assignment.eagleBank.dtos.NewAccountDto;
import com.assignment.eagleBank.entity.Account;
import com.assignment.eagleBank.entity.User;
import com.assignment.eagleBank.services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequestMapping("/v1/accounts")
@RestController
@AllArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody NewAccountDto newAccountDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        //TODO validation of input here

        Account account = accountService.createAccount(newAccountDto, currentUser);

        return ResponseEntity.ok(account);

    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllUserAccounts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        List<Account> accounts = accountService.getAllUserAccounts(currentUser);
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Optional<Account>> getAccountById(@PathVariable("accountId") Integer accountId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        Optional<Account> account = accountService.getUserAccountById(currentUser, accountId);

        return ResponseEntity.ok(account);
    }

}
