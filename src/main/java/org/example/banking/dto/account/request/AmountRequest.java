package org.example.banking.dto.account.request;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class AmountRequest {
    @NotNull
    @DecimalMin(value = "0.01", inclusive = true, message = "amount must be > 0")
    private BigDecimal amount;
}
