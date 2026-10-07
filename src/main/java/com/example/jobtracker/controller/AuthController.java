package com.example.jobtracker.controller;

import com.example.jobtracker.dto.RegisterRequest;
import com.example.jobtracker.dto.UserResponse;
import com.example.jobtracker.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/** Exposes the registration endpoint. */
@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    /** Creates a new account. */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody RegisterRequest request){
        return authService.register(request);
    }
}
