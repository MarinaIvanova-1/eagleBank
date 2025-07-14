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

import static com.assignment.eagleBank.entity.TransactionTypeEnum.DEPOSIT;
import static com.assignment.eagleBank.entity.TransactionTypeEnum.WITHDRAWAL;

@Service
@AllArgsConstructor
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Transaction createTransaction(NewTransactionDto newTransactionDto, User user, Integer accountId) {
        if (newTransactionDto.getAmount() > 1000.00) {
            //TODO new exception type?
            throw new IllegalArgumentException("Amount can't be greater than 1000.00");
        }
        Account account = accountRepository.findAccountByAccountUser_IdAndAccountNumber(user.getId(), accountId).orElse(null);

        Transaction transaction = new Transaction();

        //TODO refactor nested if clause
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
                    throw new IllegalArgumentException("Insufficient funds");
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

}
