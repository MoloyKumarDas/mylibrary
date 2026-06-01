package com.das.mylibrary.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank
    private String usernameOrEmail;     // accepts both, username and email

    @NotBlank
    private String password;
}