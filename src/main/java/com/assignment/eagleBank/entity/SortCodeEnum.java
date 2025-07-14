package com.assignment.eagleBank.entity;

public enum SortCodeEnum {
    EAGLE_BANK_SORT_CODE("10-10-10");

    private final String branchSortCode;
    private SortCodeEnum(String branchSortCode) {this.branchSortCode = branchSortCode; }
    public String getBranchSortCode() {
        return branchSortCode;
    }
}
