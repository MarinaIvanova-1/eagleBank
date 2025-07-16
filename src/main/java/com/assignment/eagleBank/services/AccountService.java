package com.assignment.eagleBank.services;

import com.assignment.eagleBank.dtos.NewAccountDto;
import com.assignment.eagleBank.entity.Account;
import com.assignment.eagleBank.entity.CurrencyEnum;
import com.assignment.eagleBank.entity.User;
import com.assignment.eagleBank.repositories.AccountRepository;
import com.assignment.eagleBank.services.utils.IdGenerator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(NewAccountDto newAccountDto, User user) {
        Account account = new Account()
                .setName(newAccountDto.getName())
                .setAccountType(newAccountDto.getType())
                .setBalance(0.00)
                .setSortCode("10-10-10")
                .setCurrency(CurrencyEnum.GBP)
                .setAccountNumber(IdGenerator.generateAccountNumber())
                .setUser(user);

        return accountRepository.save(account);
    }

    public List<Account> getAllUserAccounts(User user) {
        List<Account> accounts = accountRepository.findAccountsByAccountUser_Id(user.getId());
        return accounts;
    }

    public Optional<Account> getUserAccountById(User user, String accountId) {

        Optional<Account> account = accountRepository.findAccountByAccountUser_IdAndAccountNumber(user.getId(), accountId);

        if (account.isEmpty()) {
            if (accountRepository.existsAccountByAccountNumber(accountId)) {
                throw new AccessDeniedException("The user is not allowed to access the bank account details");
            } else {
                throw new IllegalArgumentException("Bank account was not found");
            }
        }

        return account;
    }
}