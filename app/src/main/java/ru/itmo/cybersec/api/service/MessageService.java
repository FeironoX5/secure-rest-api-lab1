package ru.itmo.cybersec.api.service;

import java.util.List;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.springframework.stereotype.Service;
import ru.itmo.cybersec.api.model.Message;
import ru.itmo.cybersec.api.repository.MessageRepository;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final PolicyFactory noHtmlPolicy;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
        this.noHtmlPolicy = new HtmlPolicyBuilder().toFactory();
    }

    public Message create(String username, String content) {
        return messageRepository.create(username, sanitize(content));
    }

    public List<Message> findAll() {
        return messageRepository.findAll().stream()
                .map(message -> new Message(
                        message.id(),
                        sanitize(message.username()),
                        sanitize(message.content()),
                        message.createdAt()
                ))
                .toList();
    }

    private String sanitize(String value) {
        return noHtmlPolicy.sanitize(value);
    }
}
