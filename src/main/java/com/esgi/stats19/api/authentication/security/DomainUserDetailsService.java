package com.esgi.stats19.api.authentication.security;
import com.esgi.stats19.api.authentication.entities.Account;
import com.esgi.stats19.api.authentication.repositories.AccountRepository;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DomainUserDetailsService implements UserDetailsService {

    private final AccountRepository userRepository;

    public DomainUserDetailsService(AccountRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Account appUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new AuthenticationServiceException("username " + username + " not found"));

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
