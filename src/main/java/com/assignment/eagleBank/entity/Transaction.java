package com.assignment.eagleBank.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "transactions")
@Getter
@Setter
public class Transaction {
    @Id
    @Column
    private String transactionId;

    @ManyToOne()
    @JoinColumn(name = "accountNumber")
    private Account account;

    //TODO should it be a double?
    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionTypeEnum type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrencyEnum currency;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    public Transaction setTransactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public Transaction setAccount(Account account) {
        this.account = account;
        return this;
    }

    public Transaction setTransactionType(TransactionTypeEnum type) {
        this.type = type;
        return this;
    }

    public Transaction setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public Transaction setCurrency(CurrencyEnum currency) {
        this.currency = currency;
        return this;
    }
}
