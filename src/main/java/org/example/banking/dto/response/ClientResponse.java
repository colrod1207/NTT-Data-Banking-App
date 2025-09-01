package org.example.banking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.banking.domain.Client;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientResponse {
    private Long id;
    private String dni;
    private String firstName;
    private String lastName;
    private String email;

    // Factory para convertir desde la entidad
    public static ClientResponse from(Client c) {
        return ClientResponse.builder()
                .id(c.getId())
                .dni(c.getDni())
                .firstName(c.getFirstName())
                .lastName(c.getLastName())
                .email(c.getEmail())
                .build();
    }
}
