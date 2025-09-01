package org.example.banking.service;

import lombok.RequiredArgsConstructor;
import org.example.banking.domain.Account;
import org.example.banking.domain.AccountType;
import org.example.banking.domain.Client;
import org.example.banking.repository.AccountRepository;
import org.example.banking.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public List<Account> list() {
        return accountRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Account> listByClient(Long clientId) {
        return accountRepository.findByClient_Id(clientId);
    }

    @Transactional(readOnly = true)
    public Account get(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + id));
    }

    @Transactional
    public Account create(Long clientId, AccountType type, BigDecimal initialDeposit) {
        if (initialDeposit == null || initialDeposit.signum() < 0) {
            throw new IllegalArgumentException("initialDeposit must be >= 0");
        }

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found: " + clientId));

        // Generar número único de cuenta
        String number;
        do {
            number = "ACC-" + System.currentTimeMillis() + "-" +
                    UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        } while (accountRepository.existsByNumber(number));

        Account acc = Account.builder()
                .number(number)
                .type(type)
                .balance(initialDeposit)
                .client(client)
                .build();

        return accountRepository.save(acc);
    }

    @Transactional
    public Account updateType(Long id, AccountType type) {
        Account a = get(id);
        a.setType(type);
        return accountRepository.save(a);
    }

    @Transactional
    public Account deposit(Long id, BigDecimal amount) {
        if (amount == null || amount.signum() <= 0) throw new IllegalArgumentException("amount must be > 0");
        Account a = get(id);
        a.setBalance(a.getBalance().add(amount));
        return accountRepository.save(a);
    }

    @Transactional
    public Account withdraw(Long id, BigDecimal amount) {
        if (amount == null || amount.signum() <= 0) throw new IllegalArgumentException("amount must be > 0");
        Account a = get(id);
        BigDecimal newBalance = a.getBalance().subtract(amount);
        if (newBalance.signum() < 0) throw new IllegalArgumentException("insufficient funds");
        a.setBalance(newBalance);
        return accountRepository.save(a);
    }

    @Transactional
    public void delete(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new IllegalArgumentException("Account not found: " + id);
        }
        accountRepository.deleteById(id);
    }
}
