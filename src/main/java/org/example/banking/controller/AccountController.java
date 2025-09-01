package org.example.banking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.banking.domain.Account;
import org.example.banking.dto.account.request.AmountRequest;
import org.example.banking.dto.account.request.CreateAccountRequest;
import org.example.banking.dto.account.request.PatchAccountRequest;
import org.example.banking.dto.account.request.UpdateAccountRequest;
import org.example.banking.dto.account.response.AccountResponse;
import org.example.banking.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping
@Tag(name = "Accounts", description = "CRUD de cuentas y operaciones de saldo")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping("/accounts")
    @Operation(summary = "Crear cuenta")
    public ResponseEntity<AccountResponse> create(@Valid @RequestBody CreateAccountRequest req) {
        Account a = service.create(req.getClientId(), req.getType(), req.getInitialDeposit());
        return ResponseEntity.ok(AccountResponse.from(a));
    }

    @GetMapping("/accounts")
    @Operation(summary = "Listar todas las cuentas")
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(
                service.list().stream().map(AccountResponse::from).collect(Collectors.toList())
        );
    }

    @GetMapping("/clients/{clientId}/accounts")
    @Operation(summary = "Listar cuentas por cliente")
    public ResponseEntity<?> listByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(
                service.listByClient(clientId).stream().map(AccountResponse::from).collect(Collectors.toList())
        );
    }

    @GetMapping("/accounts/{id}")
    @Operation(summary = "Obtener cuenta por ID")
    public ResponseEntity<AccountResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(AccountResponse.from(service.get(id)));
    }

    @PutMapping("/accounts/{id}")
    @Operation(summary = "Actualizar tipo de cuenta (PUT)")
    public ResponseEntity<AccountResponse> update(@PathVariable Long id,
                                                  @Valid @RequestBody UpdateAccountRequest req) {
        Account a = service.updateType(id, req.getType());
        return ResponseEntity.ok(AccountResponse.from(a));
    }

    @PatchMapping("/accounts/{id}")
    @Operation(summary = "Actualizar parcialmente la cuenta (PATCH)")
    public ResponseEntity<AccountResponse> patch(@PathVariable Long id,
                                                 @RequestBody PatchAccountRequest req) {
        if (req.getType() == null) return ResponseEntity.ok(AccountResponse.from(service.get(id)));
        Account a = service.updateType(id, req.getType());
        return ResponseEntity.ok(AccountResponse.from(a));
    }

    @PostMapping("/accounts/{id}/deposit")
    @Operation(summary = "Depositar dinero en una cuenta")
    public ResponseEntity<AccountResponse> deposit(@PathVariable Long id,
                                                   @Valid @RequestBody AmountRequest req) {
        Account a = service.deposit(id, req.getAmount());
        return ResponseEntity.ok(AccountResponse.from(a));
    }

    @PostMapping("/accounts/{id}/withdraw")
    @Operation(summary = "Retirar dinero de una cuenta")
    public ResponseEntity<AccountResponse> withdraw(@PathVariable Long id,
                                                    @Valid @RequestBody AmountRequest req) {
        Account a = service.withdraw(id, req.getAmount());
        return ResponseEntity.ok(AccountResponse.from(a));
    }

    @DeleteMapping("/accounts/{id}")
    @Operation(summary = "Eliminar cuenta")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
