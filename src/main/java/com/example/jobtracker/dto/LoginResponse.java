package com.example.jobtracker.dto;

/** The token returned after a successful login. */
public record LoginResponse(String token, String tokenType, long expiresInSeconds) {
}
