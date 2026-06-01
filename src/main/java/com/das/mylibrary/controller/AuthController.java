package com.das.mylibrary.controller;

import com.das.mylibrary.dto.LoginRequest;
import com.das.mylibrary.dto.RegisterRequest;
import com.das.mylibrary.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return authService.login(request.getUsernameOrEmail(), request.getPassword());
    }
}