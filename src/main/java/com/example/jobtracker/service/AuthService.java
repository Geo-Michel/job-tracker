package com.example.jobtracker.service;

import com.example.jobtracker.domain.User;
import com.example.jobtracker.dto.RegisterRequest;
import com.example.jobtracker.dto.UserResponse;
import com.example.jobtracker.exception.ConflictException;
import com.example.jobtracker.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Handles account registration. */
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService (UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /** Creates a new account with a hashed password; fails with 409 if the email is taken. */

    @Transactional
    public UserResponse register(RegisterRequest request){
        String email = request.email().trim().toLowerCase();
        if(userRepository.existsByEmail(email)){
            throw new ConflictException("Email is already registered");
        }
        User user = new User(email, passwordEncoder.encode(request.password()));
        return UserResponse.from(userRepository.save(user));
    }
}
