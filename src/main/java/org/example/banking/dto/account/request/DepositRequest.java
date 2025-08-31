package org.example.banking.dto.account.request;

import lombok.Data;

import javax.validation.constraints.Positive;

@Data
public class DepositRequest {
    @Positive private Double amount;
}
