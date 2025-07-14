package com.assignment.eagleBank.repositories;

import com.assignment.eagleBank.entity.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account, Long> {
//    List<Account> findAccountsByAccountUser(String userId);
}
