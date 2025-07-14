package com.assignment.eagleBank.services.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidation {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_PHONE_NUMBER =
            Pattern.compile("^\\d{1,14}$");

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
}
