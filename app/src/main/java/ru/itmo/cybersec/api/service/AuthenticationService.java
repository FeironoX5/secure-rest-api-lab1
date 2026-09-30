package ru.itmo.cybersec.api.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itmo.cybersec.api.model.User;
import ru.itmo.cybersec.api.repository.UserRepository;
import ru.itmo.cybersec.api.security.JwtService;

@Service
public class AuthenticationService {
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthenticationService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String authenticate(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(InvalidCredentialsException::new);
        if (!passwordEncoder.matches(password, user.passwordHash())) {
            throw new InvalidCredentialsException();
        }
        return jwtService.issue(user.username());
    }
}
