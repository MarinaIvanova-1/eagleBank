package com.assignment.eagleBank;

import com.assignment.eagleBank.dtos.LoginUserDto;
import com.assignment.eagleBank.entity.Account;
import com.assignment.eagleBank.entity.Address;
import com.assignment.eagleBank.entity.CurrencyEnum;
import com.assignment.eagleBank.entity.Transaction;
import com.assignment.eagleBank.entity.TransactionTypeEnum;
import com.assignment.eagleBank.entity.User;
import com.assignment.eagleBank.repositories.AccountRepository;
import com.assignment.eagleBank.repositories.TransactionRepository;
import com.assignment.eagleBank.repositories.UserRepository;
import com.assignment.eagleBank.services.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class TestUtils {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransactionRepository transactionRepository;
    public static User createNewUser(Address address) {
        User user = new User();
        user.setId("usr-test1234");
        user.setAddress(address);
        user.setEmail("test1@test.com");
        user.setName("test name");
        user.setPassword("test password");
        user.setCreatedAt(new Date());
        user.setPhoneNumber("0123456789");
        user.setUpdatedAt(new Date());
        return user;
    }

    public static Address createNewAddress() {
        Address address = new Address();
        address.setLine1("Line 1 of address");
        address.setCounty("test county");
        address.setTown("test town");
        address.setPostcode("test postcode");
        return address;
    }

    public static Account createNewAccount(User user) {
        Account account = new Account();
        //TODO sort out account numbers
        account.setAccountNumber("01234234");
        account.setAccountType("PERSONAL");
        account.setBalance(0.00);
        account.setCurrency(CurrencyEnum.GBP);
        account.setName("test account name");
        account.setCreatedAt(new Date());
        account.setUpdatedAt(new Date());
        account.setUser(user);
        account.setSortCode("10-10-10");
        return account;
    }

    public static Transaction createNewtransaction(Account account, TransactionTypeEnum type, Integer value) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId("tan-testtest");
        transaction.setAmount(value);
        //TODO check this
//        transaction.setCurrency(CurrencyEnum.GBP);
        transaction.setType(type);
        transaction.setCreatedAt(new Date());
        transaction.setAccount(account);
        return transaction;
    }

    public static LoginUserDto createLoginUserDto() {
        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setEmail("test1@tqqest.com");
        loginUserDto.setPassword("test passwordqqq");
        return loginUserDto;
    }

    public User saveUserToDb(String email) {

        var address = new Address()
                .setLine1("Line 1 of address")
                .setTown("Test town")
                .setCounty("Test county")
                .setPostcode("Test postcode");
        var user = new User()
                .setName("test name")
                .setEmail(email)
                .setPhoneNumber("+1111111111111")
                .setAddress(address)
                .setPassword("password")
                .setId(IdGenerator.generateUserId());

        return userRepository.save(user);
    }
}