package org.example.banking.dto.account.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.banking.domain.Account;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponse {
    private Long id;
    private String number;
    private String type;
    private BigDecimal balance;
    private Long clientId;

    public static AccountResponse from(Account a) {
        return AccountResponse.builder()
                .id(a.getId())
                .number(a.getNumber())
                .type(a.getType().name())
                .balance(a.getBalance())
                .clientId(a.getClient().getId())
                .build();
    }
}
