package org.example.banking.dto.client.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter @Setter
@NoArgsConstructor
public class CreateClientRequest {
    @NotBlank private String firstName;
    @NotBlank private String lastName;
    @NotBlank private String dni;
    @NotBlank @Email private String email;
}
