package com.esgi.stats19.api.authentication.services;

import com.esgi.stats19.api.authentication.dto.GetUserDTO;
import com.esgi.stats19.api.authentication.entities.Account;
import org.springframework.stereotype.Service;

@Service
public class UserDTOService {

    public GetUserDTO toResponse(Account account) {
        return GetUserDTO.builder()
                .username(account.getUsername())
                .email(account.getEmail())
                .build();
    }
}
