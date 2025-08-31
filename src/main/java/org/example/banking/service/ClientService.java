package org.example.banking.service;

import org.example.banking.domain.Client;
import org.example.banking.repository.AccountRepository;
import org.example.banking.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;

    public ClientService(ClientRepository clientRepository, AccountRepository accountRepository) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional(readOnly = true)
    public Client get(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));
    }

    @Transactional
    public Client register(String firstName, String lastName, String email, String dni){
        if(isBlank(firstName) || isBlank(lastName) || isBlank(dni) || isBlank(email)){
            throw new IllegalArgumentException("All fields are required");
        }
        if(clientRepository.existsByDni(dni)){
            throw new IllegalArgumentException("Dni is already in use");
        }
        if(clientRepository.existsByEmail(email)){
            throw new IllegalArgumentException("Email is already in use");
        }
        Client c = new Client(firstName, lastName, dni, email);
        return clientRepository.save(c);
    }

    @Transactional
    public Client updateClient(Long id, String firstName, String lastName, String email) {
        Client c = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        if (firstName != null && !firstName.isBlank()) {
            c.setFirstName(firstName);
        }
        if (lastName != null && !lastName.isBlank()) {
            c.setLastName(lastName);
        }
        if (email != null && !email.isBlank()) {
            if (!email.equalsIgnoreCase(c.getEmail()) && clientRepository.existsByEmail(email)) {
                throw new IllegalArgumentException("Email already in use");
            }
            c.setEmail(email);
        }

        return clientRepository.save(c);
    }

    @Transactional(readOnly = true)
    public List<Client> list() {
        return clientRepository.findAll();
    }

    @Transactional
    public void deleteClient(Long id) {
        boolean hasAccounts = !accountRepository.findByOwner_Id(id).isEmpty();
        if (hasAccounts) {
            throw new IllegalArgumentException("Cannot delete client with active accounts");
        }
        if(!clientRepository.existsById(id)){
            throw new IllegalArgumentException("Client not found");
        }
        clientRepository.deleteById(id);
    }

    private boolean isBlank(String s){
        return s == null || s.trim().isEmpty();
    }

}
