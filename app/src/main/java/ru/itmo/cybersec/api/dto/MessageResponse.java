package ru.itmo.cybersec.api.dto;

import java.time.Instant;
import ru.itmo.cybersec.api.model.Message;

public record MessageResponse(long id, String username, String content, Instant createdAt) {
    public static MessageResponse from(Message message) {
        return new MessageResponse(message.id(), message.username(), message.content(), message.createdAt());
    }
}
