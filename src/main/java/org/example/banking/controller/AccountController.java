package org.example.banking.controller;

import org.example.banking.domain.BankAccount;
import org.example.banking.dto.account.request.DepositRequest;
import org.example.banking.dto.account.request.OpenAccountRequest;
import org.example.banking.dto.account.request.WithdrawRequest;
import org.example.banking.dto.account.response.AccountResponse;
import org.example.banking.dto.account.response.BalanceResponse;
import org.example.banking.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // Crear cuenta (numeroCuenta autogenerado + saldo inicial > 0)
    @PostMapping
    public ResponseEntity<AccountResponse> open(@Valid @RequestBody OpenAccountRequest req) {
        BankAccount a = accountService.openAccount(req.getClientId(), req.getType(), req.getInitialBalance());
        return ResponseEntity.ok(AccountResponse.from(a));
    }

    // Depósito
    @PutMapping("/{id}/deposit")
    public ResponseEntity<BalanceResponse> deposit(@PathVariable Long id,
                                                   @Valid @RequestBody DepositRequest req) {
        accountService.deposit(id, req.getAmount());
        BankAccount a = accountService.get(id);
        return ResponseEntity.ok(new BalanceResponse(a.getId(), a.getBalance()));
    }

    // Retiro
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<BalanceResponse> withdraw(@PathVariable Long id,
                                                    @Valid @RequestBody WithdrawRequest req) {
        accountService.withdraw(id, req.getAmount());
        BankAccount a = accountService.get(id);
        return ResponseEntity.ok(new BalanceResponse(a.getId(), a.getBalance()));
    }

    // Obtener una cuenta
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(AccountResponse.from(accountService.get(id)));
    }

    // Listar cuentas: /accounts?clientId=123 (si no se envía clientId, lista todas)
    @GetMapping
    public ResponseEntity<?> list(@RequestParam(required = false) Long clientId) {
        if (clientId != null) {
            return ResponseEntity.ok(
                    accountService.listByClient(clientId).stream().map(AccountResponse::from).collect(Collectors.toList())
            );
        }
        return ResponseEntity.ok(
                accountService.listAll().stream().map(AccountResponse::from).collect(Collectors.toList())
        );
    }

    // (Opcional) Solo saldo
    @GetMapping("/{id}/balance")
    public ResponseEntity<BalanceResponse> balance(@PathVariable Long id) {
        BankAccount a = accountService.get(id);
        return ResponseEntity.ok(new BalanceResponse(a.getId(), a.getBalance()));
    }

    // (Opcional) Eliminar cuenta (si agregas reglas, ponlas en AccountService.delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // accountService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
