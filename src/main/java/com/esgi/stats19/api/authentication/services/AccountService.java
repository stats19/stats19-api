package com.esgi.stats19.api.authentication.services;

import com.esgi.stats19.api.authentication.entities.Account;
import com.esgi.stats19.api.authentication.repositories.AccountRepository;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account getUser(String username) {
        return this.accountRepository.findByUsername(username)
                .orElseThrow(() -> new AuthenticationServiceException("username " + username + " not found"));
    }
}
