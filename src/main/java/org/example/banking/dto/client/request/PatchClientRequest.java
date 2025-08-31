package org.example.banking.dto.client.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class PatchClientRequest {
    private String firstName;
    private String lastName;
    private String email;
}
