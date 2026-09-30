package ru.itmo.cybersec.api.repository;

import java.time.Instant;
import java.util.List;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import ru.itmo.cybersec.api.model.Message;

@Repository
public class MessageRepository {
    private final JdbcClient jdbcClient;

    public MessageRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Message create(String username, String content) {
        Instant createdAt = Instant.now();
        return jdbcClient.sql("""
                        INSERT INTO messages (username, content, created_at)
                        VALUES (:username, :content, :createdAt)
                        RETURNING id, username, content, created_at
                        """)
                .param("username", username)
                .param("content", content)
                .param("createdAt", createdAt.toString())
                .query((resultSet, rowNumber) -> new Message(
                        resultSet.getLong("id"),
                        resultSet.getString("username"),
                        resultSet.getString("content"),
                        Instant.parse(resultSet.getString("created_at"))
                ))
                .single();
    }

    public List<Message> findAll() {
        return jdbcClient.sql("SELECT id, username, content, created_at FROM messages ORDER BY id DESC")
                .query((resultSet, rowNumber) -> new Message(
                        resultSet.getLong("id"),
                        resultSet.getString("username"),
                        resultSet.getString("content"),
                        Instant.parse(resultSet.getString("created_at"))
                ))
                .list();
    }
}
