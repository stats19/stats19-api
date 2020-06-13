package com.esgi.stats19.api.authentication.security;

import com.esgi.stats19.api.authentication.entities.Account;
import com.esgi.stats19.api.authentication.repositories.AccountRepository;
import com.esgi.stats19.api.authentication.services.AccountService;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DomainUserDetailsService implements UserDetailsService {

    private final AccountService accountService;

    public DomainUserDetailsService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Account appUser = accountService.getUser(username);

        return User.builder()
                .username(username)
                .password(appUser.getPassword())
                .roles(appUser.getRole())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();

    }
}
