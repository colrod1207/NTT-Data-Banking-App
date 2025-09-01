package org.example.banking.dto.client.request;

import lombok.Data;

@Data
public class PatchClientRequest {
    private String firstName;
    private String lastName;
    private String email;
}
