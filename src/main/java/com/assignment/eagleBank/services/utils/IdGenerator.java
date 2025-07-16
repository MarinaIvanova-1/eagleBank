package com.assignment.eagleBank.services.utils;

import java.util.Random;
import java.util.UUID;

public class IdGenerator {
    private static final String USER_ID_PREFIX = "usr-";
    private static final String ACCOUNT_NUMBER_PREFIX = "01";
    private static final String TRANSACTION_ID_PREFIX = "tan-";

    public static String generateUserId() {
        return USER_ID_PREFIX + UUID.randomUUID().toString().substring(0, 8);
    }

    public static String generateAccountNumber() {
        Random gen = new Random();
        long randomInt = gen.nextInt(100000, 999999);
        return ACCOUNT_NUMBER_PREFIX + randomInt;
    }

    public static String generateTransactionId() {
        return TRANSACTION_ID_PREFIX + UUID.randomUUID().toString().substring(0, 8);
    }
}