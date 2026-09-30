package ru.itmo.cybersec.api.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itmo.cybersec.api.repository.UserRepository;

@Configuration
public class DatabaseInitializer {
    @Bean
    public ApplicationRunner sampleUserInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return arguments -> {
            if (userRepository.findByUsername("student").isEmpty()) {
                userRepository.create("student", passwordEncoder.encode("correct-horse-battery-staple"));
            }
        };
    }
}
