package org.example.banking.repository;

import org.example.banking.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByNumber(String number);
    List<Account> findByClient_Id(Long clientId);
}
