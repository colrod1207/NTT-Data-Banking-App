package org.example.banking.domain;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "accounts", indexes = {
        @Index(name = "ix_account_number", columnList = "number", unique = true),
        @Index(name = "ix_account_client", columnList = "client_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // Identificador único

    @Column(nullable = false, unique = true, length = 40)
    private String number;  // Número de cuenta único

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountType type;  // Tipo de cuenta (SAVINGS o CHECKING)

    @NotNull
    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal balance;  // Saldo actual de la cuenta

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;  // Relación N:1 → muchas cuentas pertenecen a un cliente
}
