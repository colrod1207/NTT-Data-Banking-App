package org.example.banking.dto.account.request;

import lombok.Data;

import javax.validation.constraints.Positive;

@Data
public class WithdrawRequest {
    @Positive private Double amount;
}
