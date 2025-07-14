package com.assignment.eagleBank.entity;

public enum AccountTypeEnum {
    PERSONAL("personal"),
    BUSINESS("business");

    private final String type;
    AccountTypeEnum(String type) {this.type = type; }
    public String getType() {
        return type;
    }
}
