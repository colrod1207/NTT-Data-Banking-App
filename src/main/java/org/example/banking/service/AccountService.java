package org.example.banking.service;

import org.example.banking.domain.AccountType;
import org.example.banking.domain.BankAccount;
import org.example.banking.domain.Client;
import org.example.banking.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    // Fuente de aleatoriedad para generar número de cuenta
    private final SecureRandom secureRandom = new SecureRandom();

    public AccountService(AccountRepository accountRepository, ClientRepository clientRepository) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
    }

    @Transactional
    public BankAccount openAccount(Long clientId, AccountType type, double initialBalance) {
        if (type == null) {
            throw new IllegalArgumentException("Account type required");
        }
        if (initialBalance <= 0) {
            throw new IllegalArgumentException("Initial balance must be greater than 0");
        }

        Client owner = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        String accNum = generateUniqueAccountNumber();

        BankAccount acc = new BankAccount(accNum, type, owner);
        acc.deposit(initialBalance); // aplica regla de depósito > 0
        return accountRepository.save(acc);
    }

    @Transactional
    public void deposit(Long accountId, double amount) {
        BankAccount acc = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        acc.deposit(amount);
        accountRepository.save(acc);
    }

    @Transactional
    public void withdraw(Long accountId, double amount) {
        BankAccount acc = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        acc.withdraw(amount);
        accountRepository.save(acc);
    }

    @Transactional(readOnly = true)
    public BankAccount get(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }

    @Transactional(readOnly = true)
    public List<BankAccount> listAll() {
        return accountRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<BankAccount> listByClient(Long clientId) {
        return accountRepository.findByOwner_Id(clientId);
    }

    // === Generador de número de cuenta único ===
    private String generateUniqueAccountNumber() {
        String acc;
        do {
            acc = "ACC-" + tenDigits();
        } while (accountRepository.existsByAccountNumber(acc));
        return acc;
    }

    private String tenDigits() {
        // 10 dígitos, incluye ceros a la izquierda
        long n = Math.abs(secureRandom.nextLong()) % 1_000_000_0000L;
        return String.format("%010d", n);
    }

    // == delete ==
    @Transactional
    public void delete(Long accountId) {
        BankAccount acc = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        // Regla de negocio (opcional): no permitir eliminar si saldo ≠ 0
        if (acc.getBalance() != 0.0) {
            throw new IllegalArgumentException("Cannot delete account with non-zero balance");
        }

        accountRepository.delete(acc);
    }

}
