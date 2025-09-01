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

    // Listar todas las cuentas
    @Transactional(readOnly = true)
    public List<Account> list() {
        return accountRepository.findAll();
    }

    // Listar cuentas de un cliente
    @Transactional(readOnly = true)
    public List<Account> listByClient(Long clientId) {
        return accountRepository.findByClient_Id(clientId);
    }

    // Buscar una cuenta por ID
    @Transactional(readOnly = true)
    public Account get(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + id));
    }

    // Crear cuenta para un cliente
    @Transactional
    public Account create(Long clientId, AccountType type, BigDecimal initialDeposit) {
        if (initialDeposit == null || initialDeposit.signum() < 0) {
            throw new IllegalArgumentException("initialDeposit must be >= 0");
        }

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found: " + clientId));

        // Generar número único
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

    // Actualizar tipo de cuenta
    @Transactional
    public Account updateType(Long id, AccountType type) {
        Account a = get(id);
        a.setType(type);
        return accountRepository.save(a);
    }

    // Depositar
    @Transactional
    public Account deposit(Long id, BigDecimal amount) {
        if (amount == null || amount.signum() <= 0)
            throw new IllegalArgumentException("amount must be > 0");

        Account a = get(id);
        a.setBalance(a.getBalance().add(amount));
        return accountRepository.save(a);
    }

    // Retirar
    @Transactional
    public Account withdraw(Long id, BigDecimal amount) {
        if (amount == null || amount.signum() <= 0)
            throw new IllegalArgumentException("amount must be > 0");

        Account a = get(id);
        BigDecimal newBalance = a.getBalance().subtract(amount);
        if (newBalance.signum() < 0)
            throw new IllegalArgumentException("insufficient funds");

        a.setBalance(newBalance);
        return accountRepository.save(a);
    }

    // Transferir entre cuentas
    @Transactional
    public void transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) {
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("amount must be > 0");
        }
        if (fromAccountId.equals(toAccountId)) {
            throw new IllegalArgumentException("cannot transfer to the same account");
        }

        Account from = get(fromAccountId);
        Account to = get(toAccountId);

        BigDecimal newBalance = from.getBalance().subtract(amount);
        if (newBalance.signum() < 0) {
            throw new IllegalArgumentException("insufficient funds in account " + fromAccountId);
        }

        from.setBalance(newBalance);
        to.setBalance(to.getBalance().add(amount));

        accountRepository.save(from);
        accountRepository.save(to);
    }

    // Eliminar cuenta
    @Transactional
    public void delete(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new IllegalArgumentException("Account not found: " + id);
        }
        accountRepository.deleteById(id);
    }
}
