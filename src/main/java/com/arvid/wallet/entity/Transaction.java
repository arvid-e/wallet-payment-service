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
@Table(name = "transactions")
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private UUID userId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private TransactionType transactionType;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private TransactionStatus transactionStatus;

  @Column
  private String currency;

  @Column
  private String idempotencyKey;

  public Transaction(TransactionType type, String currency, String key, UUID userId) {
    this.transactionType = type;
    this.currency = currency;
    this.idempotencyKey = key;
    this.userId = userId;
    this.transactionStatus = TransactionStatus.PENDING;
  }

  protected Transaction() {
  }

  public UUID getId() {
    return id;
  }

  public UUID getUserId() {
    return userId;
  }

  public String getCurrency() {
    return currency;
  }

  public TransactionType getTransactionType() {
    return transactionType;
  }

  public TransactionStatus getTransactionStatus() {
    return transactionStatus;
  }

  public void setTransactionStatus(TransactionStatus transactionStatus) {
    this.transactionStatus = transactionStatus;
  }

  public String getIdempotencyKey() {
    return idempotencyKey;
  }
}
