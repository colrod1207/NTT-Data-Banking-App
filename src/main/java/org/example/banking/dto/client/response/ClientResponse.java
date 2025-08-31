package org.example.banking.dto.client.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.banking.domain.Client;

@Getter
@AllArgsConstructor
public class ClientResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String dni;
    private String email;

    public static ClientResponse from(Client c) {
        return new ClientResponse(
                c.getId(),
                c.getFirstName(),
                c.getLastName(),
                c.getDni(),
                c.getEmail()
        );
    }
}
