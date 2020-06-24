package com.esgi.stats19.api.authentication.controllers;

import com.esgi.stats19.api.authentication.dto.CreateAccountDTO;
import com.esgi.stats19.api.authentication.dto.GetCreatedAccount;
import com.esgi.stats19.api.authentication.dto.GetUserDTO;
import com.esgi.stats19.api.authentication.dto.LoginDTO;
import com.esgi.stats19.api.authentication.security.TokenProvider;
import com.esgi.stats19.api.authentication.services.AccountService;
import com.esgi.stats19.api.authentication.services.UserDTOService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.readOnlyHttpHeaders;

@RestController
@RequestMapping("/")
public class AuthenticationController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManager;
    private final AccountService accountService;
    private final UserDTOService userDTOService;

    public AuthenticationController(TokenProvider tokenProvider,
                                    AuthenticationManagerBuilder authenticationManager,
                                    UserDTOService userDTOService,
                                    AccountService accountService) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.accountService = accountService;
        this.userDTOService = userDTOService;
    }

    @PostMapping("login")
    public ResponseEntity<GetUserDTO> login(@RequestBody LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());

        Authentication authentication = authenticationManager.getObject().authenticate(authenticationToken);
        String token = tokenProvider.createToken(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(AUTHORIZATION, "Bearer " + token);
        var account = this.accountService.getUser(authentication.getName());
        return ResponseEntity.ok().headers(httpHeaders).body(this.userDTOService.toResponse(account));
    }

    @PostMapping("register")
    public GetUserDTO register(@RequestBody CreateAccountDTO registerDTO) {
        return this.userDTOService.toResponse(this.accountService.createdAccount(registerDTO));
    }
}
