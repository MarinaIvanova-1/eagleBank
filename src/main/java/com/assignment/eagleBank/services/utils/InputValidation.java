package com.assignment.eagleBank.services.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidation {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_PHONE_NUMBER =
//            TODO this looks like a good regex for phone numbers:
//            ^(\+\d{1,2}\s?)?1?\-?\.?\s?\(?\d{3}\)?[\s.-]?\d{3}[\s.-]?\d{4}$
            Pattern.compile("^\\+[1-9]\\d{1,14}$");

    public static boolean isEmptyInput(String input) {
        return input == null || input.trim().isEmpty();
    }

    public static boolean isValidEMail(String input) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(input);
        return matcher.matches();
    }

    public static boolean isValidPhoneNumber(String input) {
        //TODO might need to allow for dashes and brackets, but strip them before saving to the db
//        input.replaceAll("[^0-9]", "");
        Matcher matcher = VALID_PHONE_NUMBER.matcher(input);
        return matcher.matches();
    }

    public static boolean isValidAmount(String input) {
        Double transactionAmount = Double.parseDouble(input);
        return transactionAmount > 0 && transactionAmount <= 10000;
    }
}