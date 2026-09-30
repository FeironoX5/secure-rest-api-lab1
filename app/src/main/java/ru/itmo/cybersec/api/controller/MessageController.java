package ru.itmo.cybersec.api.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.cybersec.api.dto.CreateMessageRequest;
import ru.itmo.cybersec.api.dto.MessageResponse;
import ru.itmo.cybersec.api.security.JwtAuthenticationInterceptor;
import ru.itmo.cybersec.api.service.MessageService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse create(
            @RequestAttribute(JwtAuthenticationInterceptor.AUTHENTICATED_USERNAME) String username,
            @Valid @RequestBody CreateMessageRequest request
    ) {
        return MessageResponse.from(messageService.create(username, request.content()));
    }

    @GetMapping
    public List<MessageResponse> list() {
        return messageService.findAll().stream().map(MessageResponse::from).toList();
    }
}
