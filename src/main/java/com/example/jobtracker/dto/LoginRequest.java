package com.example.jobtracker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/** Credentials a client sends to log in. */
public record LoginRequest(@NotBlank @Email String email,
                           @NotBlank String password) {
}
