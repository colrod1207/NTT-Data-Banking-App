package org.example.banking.controller;

import org.example.banking.domain.Client;
import org.example.banking.dto.client.request.CreateClientRequest;
import org.example.banking.dto.client.request.PatchClientRequest;
import org.example.banking.dto.client.request.UpdateClientRequest;
import org.example.banking.dto.response.ClientResponse;
import org.example.banking.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<ClientResponse> create(@Valid @RequestBody CreateClientRequest req) {
        Client c = clientService.register(
                req.getFirstName(),
                req.getLastName(),
                req.getDni(),
                req.getEmail()
        );
        return ResponseEntity.ok(ClientResponse.from(c));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(ClientResponse.from(clientService.get(id)));
    }

    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(
                clientService.list().stream()
                        .map(ClientResponse::from)
                        .collect(Collectors.toList())
        );
    }

    @PutMapping("/{id}")
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
