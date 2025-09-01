package org.example.banking.service;

import lombok.RequiredArgsConstructor;
import org.example.banking.domain.Client;
import org.example.banking.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public List<Client> list() {
        return clientRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Client get(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found: " + id));
    }

    // ⚠️ Firma alineada con tu controlador: (firstName, lastName, dni, email)
    @Transactional
    public Client register(String firstName, String lastName, String dni, String email) {
        if (clientRepository.existsByDni(dni)) {
            throw new IllegalArgumentException("DNI is already in use");
        }
        if (clientRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already in use");
        }

        // ✅ Usamos Lombok Builder (no constructor manual)
        Client c = Client.builder()
                .firstName(firstName)
                .lastName(lastName)
                .dni(dni)
                .email(email)
                .build();

        return clientRepository.save(c);
    }

    @Transactional
    public Client updateClient(Long id, String firstName, String lastName, String email) {
        Client c = get(id);

        if (firstName != null && !firstName.isBlank()) c.setFirstName(firstName);
        if (lastName  != null && !lastName.isBlank())  c.setLastName(lastName);
        if (email     != null && !email.isBlank())     c.setEmail(email);

        return clientRepository.save(c);
    }

    @Transactional
    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new IllegalArgumentException("Client not found: " + id);
        }
        clientRepository.deleteById(id);
    }
}
