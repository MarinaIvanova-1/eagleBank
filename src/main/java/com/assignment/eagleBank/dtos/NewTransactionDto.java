package com.assignment.eagleBank.dtos;

import com.assignment.eagleBank.entity.TransactionTypeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewTransactionDto {
    private double amount;
    private TransactionTypeEnum type;
    private String currency;
}
