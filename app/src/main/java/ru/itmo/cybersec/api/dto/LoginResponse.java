package ru.itmo.cybersec.api.dto;

public record LoginResponse(String accessToken, String tokenType, long expiresInSeconds) {
}
