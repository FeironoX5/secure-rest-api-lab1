package ru.itmo.cybersec.api.repository;

import java.util.Optional;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import ru.itmo.cybersec.api.model.User;

@Repository
public class UserRepository {
    private final JdbcClient jdbcClient;

    public UserRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Optional<User> findByUsername(String username) {
        return jdbcClient.sql("SELECT id, username, password_hash FROM users WHERE username = :username")
                .param("username", username)
                .query((resultSet, rowNumber) -> new User(
                        resultSet.getLong("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password_hash")
                ))
                .optional();
    }

    public void create(String username, String passwordHash) {
        jdbcClient.sql("INSERT INTO users (username, password_hash) VALUES (:username, :passwordHash)")
                .param("username", username)
                .param("passwordHash", passwordHash)
                .update();
    }
}
