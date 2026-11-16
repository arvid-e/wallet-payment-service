package com.arvid.wallet.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ledger_entries")
public class LedgerEntry {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private UUID transactionId;

  @Column(nullable = false)
  private UUID accountId;

  @Column(nullable = false)
  private String currency;

  @Column(nullable = false)
  private long amount;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private EntryType entryType;

  public LedgerEntry(Transaction transaction, EntryType entryType, Long amount, UUID accountId) {
    this.transactionId = transaction.getId();
    this.entryType = entryType;
    this.currency = transaction.getCurrency();
    this.amount = amount;
    this.accountId = accountId;
  }

  public UUID getId() {
    return id;
  }

  public UUID getAccountId() {
    return accountId;
  }

  public UUID getTransactionId() {
    return transactionId;
  }

  public EntryType getEntryType() {
    return entryType;
  }

  public long getAmount() {
    return amount;
  }

  public String getCurrency() {
    return currency;
  }

}
