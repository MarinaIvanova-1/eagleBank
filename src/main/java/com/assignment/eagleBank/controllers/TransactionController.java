package com.assignment.eagleBank.controllers;

import com.assignment.eagleBank.dtos.NewTransactionDto;
import com.assignment.eagleBank.entity.Account;
import com.assignment.eagleBank.entity.Transaction;
import com.assignment.eagleBank.entity.User;
import com.assignment.eagleBank.services.TransactionService;
import com.assignment.eagleBank.services.utils.InputValidation;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
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

@RestController
@RequestMapping("/v1/accounts/{accountNumber}/transactions")
@AllArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@PathVariable("accountNumber") String accountNumber, @RequestBody NewTransactionDto newTransactionDto) throws BadRequestException, AuthenticationException {
        if (InputValidation.isEmptyInput(String.valueOf(newTransactionDto.getCurrency()))||
                InputValidation.isEmptyInput(String.valueOf(newTransactionDto.getAmount())) ||
                !InputValidation.isValidAmount(String.valueOf(newTransactionDto.getAmount())) ||
                InputValidation.isEmptyInput(String.valueOf(newTransactionDto.getType()))) {
            throw new BadRequestException("Invalid details supplied");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal().equals("anonymousUser")) {
            throw new AuthenticationException("Access token is missing or invalid");
        }

        if (InputValidation.isEmptyInput(accountNumber)) {
            throw new BadRequestException("The request didn't supply all the necessary data");
        }

        User currentUser = (User) authentication.getPrincipal();

        Transaction transaction = transactionService.createTransaction(newTransactionDto, currentUser, accountNumber);

        return ResponseEntity.ok(transaction);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactionsFromAccount(@PathVariable("accountId") String accountId) throws AuthenticationException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal().equals("anonymousUser")) {
            throw new AuthenticationException("Access token is missing or invalid");
        }
        User currentUser = (User) authentication.getPrincipal();
        List<Transaction> transactions = transactionService.getAllTransactionsFromAccount(currentUser, accountId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<Optional<Transaction>> getTransactionById(@PathVariable("accountId") String accountId, @PathVariable("transactionId") String transactionId) throws AuthenticationException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal().equals("anonymousUser")) {
            throw new AuthenticationException("Access token is missing or invalid");
        }
        User currentUser = (User) authentication.getPrincipal();

        Optional<Transaction> transaction = transactionService.getTransactionById(currentUser, accountId, transactionId);

        return ResponseEntity.ok(transaction);

    }
}