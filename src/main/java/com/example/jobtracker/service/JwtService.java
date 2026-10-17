package com.example.jobtracker.service;

import com.example.jobtracker.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/** Creates signed JWT tokens for authenticated users. */
@Service
public class JwtService {

    private final SecretKey key;
    private final long expirationMinutes;

    public JwtService(@Value("${app.jwt.secret}") String secret, @Value("${app.jwt.expiration-minutes}") long expirationMinutes){
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMinutes = expirationMinutes;
    }

    /**Builds a signed token that identifies the user and expires after the configured time. */
    public String generateToken(User user){
        Instant now= Instant.now();
        return Jwts.builder().subject(String.valueOf(user.getId())).claim("email", user.getEmail()).issuedAt(Date.from(now)).expiration(Date.from(now.plus(expirationMinutes, ChronoUnit.MINUTES))).signWith(key).compact();

    }

    /** Returns how long a newly issued token stays valid, in seconds. */
    public long getExpirationSeconds() {
        return expirationMinutes * 60;
    }
}
