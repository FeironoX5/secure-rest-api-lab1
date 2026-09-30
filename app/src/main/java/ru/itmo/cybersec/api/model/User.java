package ru.itmo.cybersec.api.model;

public record User(long id, String username, String passwordHash) {
}
