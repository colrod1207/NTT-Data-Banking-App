package org.example.banking.dto.account.request;

import lombok.Data;
import org.example.banking.domain.AccountType;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class CreateAccountRequest {
    @NotNull private Long clientId;
    @NotNull private AccountType type;
    @NotNull private BigDecimal initialDeposit; // >= 0 (validado en servicio)
}
