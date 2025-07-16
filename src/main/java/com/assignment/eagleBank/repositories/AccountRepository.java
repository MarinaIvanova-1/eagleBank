package com.assignment.eagleBank.repositories;

import com.assignment.eagleBank.entity.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {
    List<Account> findAccountsByAccountUser_Id(String userId);
    Optional<Account> findAccountByAccountUser_IdAndAccountNumber(String userId, String accountId);
    Optional<Account> findAccountsByAccountNumber(String accountId);
    boolean existsAccountByAccountNumber(String accountId);
//    existsByAccountNumber
//    Optional<Account> findAccountById(Long id);
//    List<Account> findAccountsByAccountUser2(User user);
//    List<Account> findAll();
}
