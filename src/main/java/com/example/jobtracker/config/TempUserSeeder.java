package com.example.jobtracker.config;

import com.example.jobtracker.domain.User;
import com.example.jobtracker.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/** TEMPORARY: creates a test user at startup so applications have an owner. */
@Component
public class TempUserSeeder implements CommandLineRunner{

    private final UserRepository userRepository;

    public TempUserSeeder(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        if(!userRepository.existsByEmail("test@example.com")){
            userRepository.save(new User("test@example.com", "not-a-real-hash"));
        }
    }
}
