package com.assignment.eagleBank.entity;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {

    public boolean supports(Class clazz) {
        return Address.class.equals(clazz);
    }

    public void validate(Object obj, Errors e) {
        ValidationUtils.rejectIfEmpty(e, "address", "address.empty");
//        ValidationUtils.rejectIfEmpty(e, "town", "town.empty");
//        ValidationUtils.rejectIfEmpty(e, "county", "county.empty");
//        ValidationUtils.rejectIfEmpty(e, "postcode", "postcode.empty");
//        Address p = (Address) obj;
//        if (p.getAge() < 0) {
//            e.rejectValue("age", "negativevalue");
//        } else if (p.getAge() > 110) {
//            e.rejectValue("age", "too.darn.old");
//        }
    }
}
