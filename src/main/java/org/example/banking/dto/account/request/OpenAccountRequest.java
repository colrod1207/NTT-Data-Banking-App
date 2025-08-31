package org.example.banking.dto.account.request;

import lombok.Data;
import org.example.banking.domain.AccountType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class OpenAccountRequest {
    @NotNull private Long clientId;
    @NotNull private AccountType type;      // SAVINGS | CHECKING
    @Positive private Double initialBalance; // > 0
}
