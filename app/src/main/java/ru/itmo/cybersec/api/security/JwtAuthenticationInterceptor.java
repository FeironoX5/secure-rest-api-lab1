package ru.itmo.cybersec.api.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtAuthenticationInterceptor implements HandlerInterceptor {
    public static final String AUTHENTICATED_USERNAME = "authenticatedUsername";

    private final JwtService jwtService;

    public JwtAuthenticationInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Authentication is required");
            return false;
        }

        try {
            String username = jwtService.subject(authorization.substring("Bearer ".length()));
            request.setAttribute(AUTHENTICATED_USERNAME, username);
            return true;
        } catch (JwtException | IllegalArgumentException exception) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid or expired token");
            return false;
        }
    }
}
