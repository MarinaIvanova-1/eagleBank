package com.assignment.eagleBank.services;

import com.assignment.eagleBank.dtos.NewAccountDto;
import com.assignment.eagleBank.entity.Account;
import com.assignment.eagleBank.entity.User;
import com.assignment.eagleBank.repositories.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    public Account createAccount(NewAccountDto newAccountDto, User user) {
        Random gen = new Random();
        long randomInt = gen.nextInt(100000, 999999);
        String formattedId = "01" + randomInt;
        Account account = new Account()
                .setName(newAccountDto.getName())
                .setAccountType(newAccountDto.getType())
                .setBalance(0.00)
                .setSortCode("10-10-10")
                .setCurrency("GBP")
                .setAccountNumber(Integer.valueOf(formattedId))
                .setUser(user);

        return accountRepository.save(account);
    }

    //TODO this needs doing
    public List<Account> getAllUserAccounts(String userId) {
        List<Account> accounts = new ArrayList<>();
        return accounts;

    }
}
