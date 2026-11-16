package com.arvid.wallet.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arvid.wallet.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction,UUID> {
}
