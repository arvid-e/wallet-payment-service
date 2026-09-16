package com.arvid.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arvid.wallet.entity.Account;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
  Optional<Account> findByUserIdAndCurrency(UUID userId, String currency);
  Optional<Account> findByCodeAndCurrency(String code, String currency);
}