package org.example.banking.dto.account.request;

import lombok.Data;
import org.example.banking.domain.AccountType;

@Data
public class PatchAccountRequest {
    private AccountType type; // opcional
}
