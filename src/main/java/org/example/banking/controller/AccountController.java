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

    // POST /accounts: Crear una cuenta para un cliente
    @PostMapping
    public ResponseEntity<AccountResponse> open(@Valid @RequestBody OpenAccountRequest req) {
        BankAccount a = accountService.openAccount(req.getClientId(), req.getType(), req.getInitialBalance());
        return ResponseEntity.ok(AccountResponse.from(a));
    }

    // GET /accounts: Listar TODAS las cuentas
    @GetMapping
    public ResponseEntity<?> listAll() {
        return ResponseEntity.ok(
                accountService.listAll()
                        .stream()
                        .map(AccountResponse::from)
                        .collect(Collectors.toList())
        );
    }

    // GET /accounts/{id}: Listar cuentas por ID DE CLIENTE (requerimiento del documento)
    @GetMapping("/{id}")
    public ResponseEntity<?> listByClient(@PathVariable Long id) {
        return ResponseEntity.ok(
                accountService.listByClient(id)
                        .stream()
                        .map(AccountResponse::from)
                        .collect(Collectors.toList())
        );
    }

    // GET /accounts/by-id/{accountId}: Obtener detalles de UNA cuenta por ID DE CUENTA
    @GetMapping("/by-id/{accountId}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable Long accountId) {
        return ResponseEntity.ok(AccountResponse.from(accountService.get(accountId)));
    }

    // PUT /accounts/{accountId}/deposit: Depositar en una CUENTA específica
    @PutMapping("/{accountId}/deposit")
    public ResponseEntity<BalanceResponse> deposit(@PathVariable Long accountId,
                                                   @Valid @RequestBody DepositRequest req) {
        accountService.deposit(accountId, req.getAmount());
        BankAccount a = accountService.get(accountId);
        return ResponseEntity.ok(new BalanceResponse(a.getId(), a.getBalance()));
    }

    // PUT /accounts/{accountId}/withdraw: Retirar de una CUENTA específica
    @PutMapping("/{accountId}/withdraw")
    public ResponseEntity<BalanceResponse> withdraw(@PathVariable Long accountId,
                                                    @Valid @RequestBody WithdrawRequest req) {
        accountService.withdraw(accountId, req.getAmount());
        BankAccount a = accountService.get(accountId);
        return ResponseEntity.ok(new BalanceResponse(a.getId(), a.getBalance()));
    }

    // GET /accounts/by-id/{accountId}/balance: (Opcional) Consultar solo saldo por ID DE CUENTA
    @GetMapping("/by-id/{accountId}/balance")
    public ResponseEntity<BalanceResponse> balance(@PathVariable Long accountId) {
        BankAccount a = accountService.get(accountId);
        return ResponseEntity.ok(new BalanceResponse(a.getId(), a.getBalance()));
    }

    // DELETE /accounts/by-id/{accountId}: Eliminar una cuenta por ID DE CUENTA
    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> delete(@PathVariable Long accountId) {
        accountService.delete(accountId);
        return ResponseEntity.noContent().build();
    }
}
