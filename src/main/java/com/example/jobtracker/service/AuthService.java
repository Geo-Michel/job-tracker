package com.example.jobtracker.service;

import com.example.jobtracker.domain.User;
import com.example.jobtracker.dto.RegisterRequest;
import com.example.jobtracker.dto.UserResponse;
import com.example.jobtracker.exception.ConflictException;
import com.example.jobtracker.repository.UserRepository;
import com.example.jobtracker.dto.LoginResponse;
import com.example.jobtracker.dto.LoginRequest;
import com.example.jobtracker.exception.UnauthorizedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Handles account registration and login. */
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService (UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
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

    /** Checks the credentials and returns a signed token; fails with 401 if they are wrong. */
    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        String email = request.email().trim().toLowerCase();
        User user = userRepository.findByEmail(email)
                .filter(found -> passwordEncoder.matches(request.password(), found.getPasswordHash()))
                .orElseThrow(() -> new UnauthorizedException("Invalid email or password"));

        return new LoginResponse(jwtService.generateToken(user), "Bearer", jwtService.getExpirationSeconds());
    }
}
