package com.assignment.eagleBank.services;

import com.assignment.eagleBank.dtos.NewTransactionDto;
import com.assignment.eagleBank.entity.Account;
import com.assignment.eagleBank.entity.Transaction;
import com.assignment.eagleBank.entity.User;
import com.assignment.eagleBank.repositories.AccountRepository;
import com.assignment.eagleBank.repositories.TransactionRepository;
import com.assignment.eagleBank.services.utils.IdGenerator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.assignment.eagleBank.entity.TransactionTypeEnum.DEPOSIT;
import static com.assignment.eagleBank.entity.TransactionTypeEnum.WITHDRAWAL;

@Service
@AllArgsConstructor
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;

    //TODO refactor to rename accountID to accountNumber for consistency
    public Transaction createTransaction(NewTransactionDto newTransactionDto, User user, String accountId) {
        if (newTransactionDto.getAmount() > 10000.00) {
            //TODO new exception type?
            throw new IllegalArgumentException("Amount can't be greater than 1000.00");
        }
        Account account = accountRepository.findAccountByAccountUser_IdAndAccountNumber(user.getId(), accountId).orElse(null);

        Transaction transaction = new Transaction();

        //TODO refactor tripple nested if clause
        if (account != null) {
            transaction.setAmount(newTransactionDto.getAmount())
                    .setTransactionType(newTransactionDto.getType())
                    .setCurrency(newTransactionDto.getCurrency())
                    //TODO does this need to be unique?
                    .setTransactionId(IdGenerator.generateTransactionId())
                    .setAccount(account);

            if (newTransactionDto.getType().equals(WITHDRAWAL)) {
                if (account.getBalance() < transaction.getAmount()) {
                    //TODO Check which exception is required here
                    throw new UnsupportedOperationException ("Insufficient funds to process transaction");
                } else {
                    account.setBalance(account.getBalance() - transaction.getAmount());
                }

            } else if (newTransactionDto.getType().equals(DEPOSIT)) {
                account.setBalance(account.getBalance() + transaction.getAmount());
            }

        } else {
            if (accountRepository.existsAccountByAccountNumber(accountId)) {
                throw new AccessDeniedException("The user is not allowed to access the bank account details");
            } else {
                throw new IllegalArgumentException("Bank account was not found");
            }
        }

        accountRepository.save(account);
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactionsFromAccount(User user, String accountId) {
        Optional<Account> account = accountRepository.findAccountByAccountUser_IdAndAccountNumber(user.getId(), accountId);
        List<Transaction> transactions = new ArrayList<>();
        if (account.isPresent()) {
            transactions = transactionRepository.findTransactionsByAccountAccountNumber(accountId);
        } else if (accountRepository.existsAccountByAccountNumber(accountId)) {
            throw new AccessDeniedException("The user is not allowed to access the transactions");
        } else {
            throw new IllegalArgumentException("Bank account was not found");
        }
        return transactions;
    }

    public Optional<Transaction> getTransactionById(User user, String accountId, String transactionId) {
        accountService.getUserAccountById(user, accountId);
        Optional<Transaction> transaction =
                transactionRepository
                        .findTransactionByTransactionIdEqualsAndAccount_AccountNumber(transactionId, accountId);
        return transaction;
    }

}