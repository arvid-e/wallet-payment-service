package com.arvid.wallet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import com.arvid.wallet.TestcontainersConfig;
import com.arvid.wallet.entity.Account;
import com.arvid.wallet.entity.User;
import com.arvid.wallet.repository.AccountRepository;
import com.arvid.wallet.repository.UserRepository;

@SpringBootTest
@Import(TestcontainersConfig.class)
public class DepositServiceIntegrationTest {
    @Autowired
    AccountRepository accountRepo;
    @Autowired
    UserRepository userRepo;
    @Autowired
    DepositService depositService;
    @Autowired
    RegisterService registerService;

    @Autowired
    JdbcTemplate jdbc;

    String email = "test@mail.com";

    @AfterEach
    void cleanup() {
        jdbc.execute("TRUNCATE ledger_entries, accounts, users RESTART IDENTITY CASCADE");
    }

    @Test
    public void depositToUserAccountIsSuccessfull() {
        // Arrange
        String pass = "password123";
        String idempotencyKey = "123";
        long amount = 5000L;

        registerService.register(email, pass);
        User user = userRepo.findByEmail(email).orElseThrow();
        Account account = accountRepo.findByUserIdAndCurrency(user.getId(), "JPY").orElseThrow();

        // Act
        depositService.deposit(amount, account.getId(), idempotencyKey);

        // Assert
        Account updatedAccount = accountRepo.findById(account.getId()).orElseThrow();
        assertEquals(amount, updatedAccount.getBalance());
    }

}
