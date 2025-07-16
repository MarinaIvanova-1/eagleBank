package com.assignment.eagleBank.controllers;

import com.assignment.eagleBank.dtos.NewAccountDto;
import com.assignment.eagleBank.entity.Account;
import com.assignment.eagleBank.entity.User;
import com.assignment.eagleBank.services.AccountService;
import com.assignment.eagleBank.services.utils.InputValidation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.sasl.AuthenticationException;
import java.util.List;
import java.util.Optional;

@RequestMapping("/v1/accounts")
@RestController
@AllArgsConstructor
@Slf4j
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody NewAccountDto newAccountDto) throws AuthenticationException, BadRequestException {
        if (InputValidation.isEmptyInput(newAccountDto.getName())||
                InputValidation.isEmptyInput(newAccountDto.getType())) {
            throw new BadRequestException("Invalid details supplied");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal().equals("anonymousUser")) {
            throw new AuthenticationException("Access token is missing or invalid");
        }
        User currentUser = (User) authentication.getPrincipal();
        Account account = accountService.createAccount(newAccountDto, currentUser);
        return ResponseEntity.ok(account);

    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllUserAccounts() throws AuthenticationException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal().equals("anonymousUser")) {
            throw new AuthenticationException("Access token is missing or invalid");
        }
        User currentUser = (User) authentication.getPrincipal();

        List<Account> accounts = accountService.getAllUserAccounts(currentUser);
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<Optional<Account>> getAccountById(@PathVariable("accountNumber") String accountNumber) throws AuthenticationException, BadRequestException {
        if (InputValidation.isEmptyInput(accountNumber)) {
            throw new BadRequestException("The request didn't supply all the necessary data");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal().equals("anonymousUser")) {
            log.error("Invalid request, incorrect authentication details were supplied for accountNumber " + accountNumber + " and current user");
            throw new AuthenticationException("The user was not authenticated");
        }
        User currentUser = (User) authentication.getPrincipal();

        Optional<Account> account = accountService.getUserAccountById(currentUser, accountNumber);

        return ResponseEntity.ok(account);
    }
}