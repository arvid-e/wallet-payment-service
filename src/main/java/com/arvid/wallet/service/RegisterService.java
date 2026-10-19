package com.arvid.wallet.service;

import org.springframework.stereotype.Service;

import com.arvid.wallet.entity.Account;
import com.arvid.wallet.entity.User;
import com.arvid.wallet.repository.AccountRepository;
import com.arvid.wallet.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class RegisterService {
  private final UserRepository userRepo;
  private final AccountRepository accountRepo;
  private final PasswordEncoder passwordEncoder;

  public RegisterService(UserRepository userRepo, AccountRepository accountRepo, PasswordEncoder passwordEncoder) {
    this.userRepo = userRepo;
    this.accountRepo = accountRepo;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional 
  public void register(String email, String password) {
    User user = new User(email, passwordEncoder.encode(password));
    this.userRepo.save(user); 

    Account account = Account.forUser(user.getId(), "JPY");
    this.accountRepo.save(account);
  }
}
