package com.jpmc.midascore.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    private String id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal balance;
    
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<TransactionRecord> sentTransactions = new ArrayList<>();
    
    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL)
    private List<TransactionRecord> receivedTransactions = new ArrayList<>();
    
    public User() {
    }
    
    public User(String id, String name, BigDecimal balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public BigDecimal getBalance() {
        return balance;
    }
    
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    
    public List<TransactionRecord> getSentTransactions() {
        return sentTransactions;
    }
    
    public void setSentTransactions(List<TransactionRecord> sentTransactions) {
        this.sentTransactions = sentTransactions;
    }
    
    public List<TransactionRecord> getReceivedTransactions() {
        return receivedTransactions;
    }
    
    public void setReceivedTransactions(List<TransactionRecord> receivedTransactions) {
        this.receivedTransactions = receivedTransactions;
    }
}