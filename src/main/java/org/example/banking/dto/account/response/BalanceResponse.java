package org.example.banking.dto.account.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BalanceResponse {
    private Long accountId;
    private double balance;
}
