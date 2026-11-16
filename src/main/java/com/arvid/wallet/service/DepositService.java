package com.arvid.wallet.service;

import java.math.BigInteger;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.arvid.wallet.entity.Account;
import com.arvid.wallet.entity.EntryType;
import com.arvid.wallet.entity.LedgerEntry;
import com.arvid.wallet.entity.Transaction;
import com.arvid.wallet.entity.TransactionStatus;
import com.arvid.wallet.entity.TransactionType;
import com.arvid.wallet.exception.AccountNotFoundException;
import com.arvid.wallet.repository.AccountRepository;
import com.arvid.wallet.repository.LedgerEntryRepository;
import com.arvid.wallet.repository.TransactionRepository;

import jakarta.transaction.Transactional;

@Service
public class DepositService {
  private final TransactionRepository transactionRepo;
  private final LedgerEntryRepository ledgerEntryRepo;
  private final AccountRepository accountRepo;

  public DepositService(TransactionRepository transactionRepo, LedgerEntryRepository ledgerEntryRepo,
      AccountRepository accountRepo) {
    this.transactionRepo = transactionRepo;
    this.ledgerEntryRepo = ledgerEntryRepo;
    this.accountRepo = accountRepo;
  }

  @Transactional
  public Transaction deposit(long amount, UUID accountId, String idempotencyKey) {
    Account account = accountRepo.findById(accountId)
        .orElseThrow(() -> new AccountNotFoundException("Account not found"));

    Transaction transaction = new Transaction(TransactionType.DEPOSIT, account.getCurrency(), idempotencyKey, account.getUserId());
    transaction.setTransactionStatus(TransactionStatus.COMPLETED);
    transactionRepo.save(transaction);

    LedgerEntry ledgerEntry = new LedgerEntry(transaction, EntryType.CREDIT, amount, accountId);
    ledgerEntryRepo.save(ledgerEntry);

    account.addBalance(amount);
    accountRepo.save(account);

    return transaction;
  }
}
