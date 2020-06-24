package com.esgi.stats19.api.authentication.services;

import com.esgi.stats19.api.authentication.dto.CreateAccountDTO;
import com.esgi.stats19.api.authentication.dto.GetCreatedAccount;
import com.esgi.stats19.api.authentication.entities.Account;
import com.esgi.stats19.api.authentication.repositories.AccountRepository;
import com.esgi.stats19.api.common.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Account getUser(String username) {
        return this.accountRepository.findByUsername(username)
                .orElseThrow(() -> new AuthenticationServiceException("username " + username + " not found"));
    }

    public Account createdAccount(CreateAccountDTO accountDTO) {
        var account = Account.builder().email(accountDTO.getEmail())
                .role("USER").username(accountDTO.getUsername())
                .password(passwordEncoder.encode(accountDTO.getPassword()))
                .build();
        Account user;
        try {
            user = this.accountRepository.save(account);
        } catch (Exception e) {
            throw new BadRequestException("bad params");
        }

        return user;
    }
}
