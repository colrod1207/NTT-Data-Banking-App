package org.example.banking.dto.account.request;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class TransferRequest {
    @NotNull private Long fromAccountId;  // Cuenta origen
    @NotNull private Long toAccountId;    // Cuenta destino

    @NotNull
    @DecimalMin(value = "0.01", message = "amount must be > 0")
    private BigDecimal amount;            // Monto a transferir
}
