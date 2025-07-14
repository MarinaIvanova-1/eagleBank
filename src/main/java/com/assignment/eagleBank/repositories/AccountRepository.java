package com.assignment.eagleBank.repositories;

import com.assignment.eagleBank.entity.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {
    List<Account> findAccountsByAccountUser_Id(Integer userId);
    Optional<Account> findAccountByAccountUser_IdAndAccountNumber(Integer userId, Integer accountId);
    Optional<Account> findAccountsByAccountNumber(Integer accountId);
    boolean existsAccountByAccountNumber(Integer accountId);
//    existsByAccountNumber
//    Optional<Account> findAccountById(Long id);
//    List<Account> findAccountsByAccountUser2(User user);
//    List<Account> findAll();
}
