package org.example.banking.dto.client.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CreateClientRequest {

    @NotBlank
    @Size(min = 2, max = 50)
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 50)
    private String lastName;

    @NotBlank
    @Size(min = 8, max = 20)
    private String dni;

    @Email
    @NotBlank
    private String email;
}
