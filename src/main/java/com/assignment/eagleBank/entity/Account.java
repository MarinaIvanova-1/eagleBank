package com.assignment.eagleBank.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "accounts")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
@Getter
@Setter
public class Account {
    @Id
    @Column
    private String accountNumber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User accountUser;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
//    @Enumerated(EnumType.STRING)
    private String accountType;

    private Double balance;

//    @Enumerated(EnumType.STRING)
    private String currency;

    //TODO does it need to be an enum?
    private String sortCode;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    public Account setName(String name) {
        this.name = name;
        return this;
    }

    public Account setAccountType(String accountType) {
        this.accountType = accountType;
        return this;
    }

    public Account setBalance(double balance) {
        this.balance = balance;
        return this;
    }

    public Account setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public Account setSortCode(String sortCode) {
        this.sortCode = sortCode;
        return this;
    }

    public Account setUser(User user) {
        this.accountUser = user;
        return this;
    }

    public Account setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }
}
