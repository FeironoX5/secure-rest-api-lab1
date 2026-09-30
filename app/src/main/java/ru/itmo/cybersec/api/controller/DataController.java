package ru.itmo.cybersec.api.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/data")
public class DataController {
    @GetMapping
    public List<DataItem> data() {
        return List.of(
                new DataItem("A03: Injection", "Use parameterized database statements."),
                new DataItem("A07: Authentication", "Use a password hash and short-lived JWT tokens."),
                new DataItem("XSS", "Sanitize untrusted content before returning it.")
        );
    }

    public record DataItem(String topic, String guidance) {
    }
}
