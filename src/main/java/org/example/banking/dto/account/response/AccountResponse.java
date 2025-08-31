package org.example.banking.dto.account.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.banking.domain.BankAccount;

@Getter
@AllArgsConstructor
public class AccountResponse {
    private Long id;
    private String accountNumber;
    private double balance;
    private String type;
    private Long clientId;

    public static AccountResponse from(BankAccount a) {
        return new AccountResponse(
                a.getId(),
                a.getAccountNumber(),
                a.getBalance(),
                a.getType().name(),
                a.getOwner().getId()
        );
    }
}
