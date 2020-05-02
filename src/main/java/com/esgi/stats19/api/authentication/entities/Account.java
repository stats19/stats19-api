package com.esgi.stats19.api.authentication.entities;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Data
@Accessors(chain = true)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Length(min=5)
    private String password;
    @Column(unique = true)
    private String username;

    @Email
    private String email;
    private String phoneNumber;

    private String role;
}
