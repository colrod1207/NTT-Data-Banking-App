package org.example.banking.dto.account.request;

import lombok.Data;
import org.example.banking.domain.AccountType;

import javax.validation.constraints.NotNull;

@Data
public class UpdateAccountRequest {
    @NotNull
    private AccountType type;
}
