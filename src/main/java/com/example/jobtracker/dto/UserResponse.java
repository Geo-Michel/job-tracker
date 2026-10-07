package com.example.jobtracker.dto;

import com.example.jobtracker.domain.User;

import java.time.Instant;

/** Public details of a user; never includes the password hash. */
public record UserResponse (Long id, String email, Instant createdAt){

    /** Builds a response from a stored user. */
    public static UserResponse from (User user){
        return new UserResponse(user.getId(), user.getEmail(), user.getCreatedAt());
    }
}
