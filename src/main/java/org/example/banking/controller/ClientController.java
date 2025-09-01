package org.example.banking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.example.banking.domain.Client;
import org.example.banking.dto.client.request.PatchClientRequest;
import org.example.banking.dto.account.request.client.UpdateClientRequest;   // ✅ CLIENT, no account
import org.example.banking.dto.response.ClientResponse;
import org.example.banking.service.ClientService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/clients")
@Tag(name = "Clients", description = "CRUD de clientes")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cliente (PUT)")
    public ResponseEntity<ClientResponse> update(@PathVariable Long id,
                                                 @Valid @RequestBody UpdateClientRequest req) {
        Client c = clientService.updateClient(
                id,
                req.getFirstName(),
                req.getLastName(),
                req.getEmail()
        );
        return ResponseEntity.ok(ClientResponse.from(c));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente cliente (PATCH)")
    public ResponseEntity<ClientResponse> patch(@PathVariable Long id,
                                                @RequestBody PatchClientRequest req) {
        Client c = clientService.updateClient(
                id,
                req.getFirstName(),
                req.getLastName(),
                req.getEmail()
        );
        return ResponseEntity.ok(ClientResponse.from(c));
    }

    // … (resto del controlador igual que ya lo tienes)
}
