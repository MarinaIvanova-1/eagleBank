package com.assignment.eagleBank.controllers;

import com.assignment.eagleBank.dtos.NewTransactionDto;
import com.assignment.eagleBank.entity.Account;
import com.assignment.eagleBank.entity.Transaction;
import com.assignment.eagleBank.entity.User;
import com.assignment.eagleBank.services.TransactionService;
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

@RestController
@RequestMapping("/v1/accounts/{accountId}/transactions")
@AllArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@PathVariable("accountId") Integer accountId, @RequestBody NewTransactionDto newTransactionDto) {
        //TODO Validate input here
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        Transaction transaction = transactionService.createTransaction(newTransactionDto, currentUser, accountId);

        return ResponseEntity.ok(transaction);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable("accountId") Integer accountId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        List<Transaction> transactions = transactionService.getAllTransactionsFromAccount(currentUser, accountId);
        return ResponseEntity.ok(transactions);
    }
}
