package com.arvid.wallet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.arvid.wallet.entity.Account;
import com.arvid.wallet.entity.AccountType;
import com.arvid.wallet.entity.User;
import com.arvid.wallet.repository.AccountRepository;
import com.arvid.wallet.repository.UserRepository;

@SpringBootTest
public class RegisterServiceIntegrationTest {

  @Autowired
  RegisterService registerService;

  @Autowired
  UserRepository userRepo;

  @Autowired
  AccountRepository accountRepo;

  @Autowired
  PasswordEncoder passwordEncoder;

  String email = "test@mail.com";
  String password = "password123";

  @AfterEach
  void cleanup() {
    userRepo.findByEmail(email).ifPresent(user -> {
      accountRepo.findByUserIdAndCurrency(user.getId(), "JPY").ifPresent(accountRepo::delete);
      userRepo.delete(user);
    });
  }

  @Test
  public void userAndAccountGetsCreated() {
    // Arrange

    // Act
    registerService.register(email, password);
    User user = userRepo.findByEmail(email).orElseThrow();
    Account account = accountRepo.findByUserIdAndCurrency(user.getId(), "JPY").orElseThrow();

    // Assert
    boolean match = passwordEncoder.matches(password, user.getPasswordHash());
    assertEquals(true, match);
    assertEquals(email, user.getEmail());

    assertEquals(AccountType.USER, account.getAccountType());
    assertEquals(null, account.getCode());
    assertEquals(0L, account.getBalance());
    assertEquals("JPY", account.getCurrency());
    assertEquals(user.getId(), account.getUserId());

    accountRepo.deleteById(account.getId());
    userRepo.deleteById(user.getId());
  }
}
