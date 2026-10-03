package com.example.jobtracker.repository;

import com.example.jobtracker.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/** Database access for users. */
public interface UserRepository extends JpaRepository<User, Long> {
    /** Finds a user by email; the result is empty if none exists. */
    Optional<User> findByEmail(String email);

    /** Checks whether an email is already registered. */
    boolean existsByEmail(String email);
}
