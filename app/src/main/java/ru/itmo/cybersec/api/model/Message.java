package ru.itmo.cybersec.api.model;

import java.time.Instant;

public record Message(long id, String username, String content, Instant createdAt) {
}
