package ru.itmo.cybersec.api.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.cybersec.api.dto.LoginRequest;
import ru.itmo.cybersec.api.dto.LoginResponse;
import ru.itmo.cybersec.api.security.JwtService;
import ru.itmo.cybersec.api.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    public AuthController(AuthenticationService authenticationService, JwtService jwtService) {
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        String token = authenticationService.authenticate(request.username(), request.password());
        return new LoginResponse(token, "Bearer", jwtService.expirationSeconds());
    }
}
