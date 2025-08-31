package org.example.banking.domain;

import javax.persistence.*;

@Entity
@Table(name = "accounts")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String accountNumber;

    @Column(nullable = false)
    private double balance = 0.0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client owner;

    protected BankAccount() {}

    public BankAccount(String accountNumber, AccountType type, Client owner) {
        if (accountNumber == null || accountNumber.isBlank())
            throw new IllegalArgumentException("Account number required");
        if (type == null)
            throw new IllegalArgumentException("Type required");
        if (owner == null)
            throw new IllegalArgumentException("Owner required");

        this.accountNumber = accountNumber;
        this.type = type;
        this.owner = owner;
        this.balance = 0.0;
    }

    // --- Reglas de negocio ---
    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Deposit must be positive");
        balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Withdraw must be positive");

        double next = balance - amount;

        if (type == AccountType.SAVINGS && next < 0) {
            throw new IllegalArgumentException("Savings account cannot go negative");
        }
        if (type == AccountType.CHECKING && next < -500.0) {
            throw new IllegalArgumentException("Checking account cannot go below -500");
        }

        balance = next;
    }

    // --- Getters---
    public Long getId() {
        return id;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public double getBalance() {
        return balance;
    }
    public AccountType getType() {
        return type;
    }
    public Client getOwner() {
        return owner;
    }

    // ---Setters ---
    public void setType(AccountType type) {
        this.type = type;
    }
    public void setOwner(Client owner) {
        this.owner = owner;
    }

}
