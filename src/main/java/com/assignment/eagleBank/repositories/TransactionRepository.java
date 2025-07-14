package com.assignment.eagleBank.repositories;

import com.assignment.eagleBank.entity.Account;
import com.assignment.eagleBank.entity.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    List<Transaction> findTransactionsByAccountAccountNumber(Integer accountId);
//    Optional<Account> findAccountByAccountUser_IdAndAccountNumber(Integer userId, Integer accountId);
//    Optional<Account> findAccountsByAccountNumber(Integer accountId);
//    boolean existsAccountByAccountNumber(Integer accountId);
//    existsByAccountNumber
//    Optional<Account> findAccountById(Long id);
//    List<Account> findAccountsByAccountUser2(User user);
//    List<Account> findAll();
}
