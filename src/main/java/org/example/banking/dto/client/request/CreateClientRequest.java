package org.example.banking.dto.client.request;

import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class CreateClientRequest {
    @NotBlank private String firstName;
    @NotBlank private String lastName;
    @NotBlank private String dni;
    @Email @NotBlank private String email;
}
