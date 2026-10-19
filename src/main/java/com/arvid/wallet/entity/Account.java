package com.arvid.wallet.entity;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "accounts")
public class Account {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private UUID userId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private AccountType accountType;

  @Enumerated(EnumType.STRING)
  @Column(length = 50)
  private SystemAccountCode code;

  @Column(nullable = false, length = 3)
  private String currency;

  @Column(nullable = false)
  private long balance;

  @Version
  private Long version;

  @org.hibernate.annotations.CreationTimestamp
  @Column(nullable = false, updatable = false)
  private Instant createdAt;

  public static Account forUser(UUID userId, String currency) {
    Account account = new Account();
    account.userId = userId;
    account.currency = currency;
    account.accountType = AccountType.USER;
    account.balance = 0L;
    return account;
  }

  public static Account forSystem(SystemAccountCode code, String currency) {
    Account account = new Account();
    account.code = code;
    account.currency = currency;
    account.accountType = AccountType.SYSTEM;
    account.balance = 0L;
    return account;
  }

  protected Account() {
  }

  public UUID getId() {
    return id;
  }

  public UUID getUserId() {
    return userId;
  }

  public AccountType getAccountType() {
    return accountType;
  }

  public SystemAccountCode getCode() {
    return code;
  }

  public String getCurrency() {
    return currency;
  }

  public long getBalance() {
    return balance;
  }

  public Long getVersion() {
    return version;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }
}
