package com.assignment.eagleBank.repositories;

import com.assignment.eagleBank.entity.Account;
import com.assignment.eagleBank.entity.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    List<Transaction> findTransactionsByAccountAccountNumber(String accountId);
    //    Optional<Transaction> findTransactionByTransactionUser_IdAndAccountNumberAndTransactionId(String userId, String accountId, String transactionId);
//    Optional<Transaction> findTransactionByTransactionId(String userId, String accountId, String transactionId);
//    Optional<Account> findAccountByAccountUser_IdAndAccountNumber(Integer userId, Integer accountId);
//    Optional<Account> findAccountsByAccountNumber(Integer accountId);
//    boolean existsAccountByAccountNumber(Integer accountId);
//    existsByAccountNumber
//    Optional<Account> findAccountById(Long id);
//    List<Account> findAccountsByAccountUser2(User user);
//    List<Account> findAll();
}

