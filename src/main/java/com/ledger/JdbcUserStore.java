package com.ledger;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class JdbcUserStore implements UserStore {

    private final JdbcTemplate jdbc;

    private final RowMapper<User> mapper = (rs, rowNum) -> new User(rs.getInt("id"), rs.getString("email"),
            rs.getString("password_hash"));

    public JdbcUserStore(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public User findByEmail(String email) {
        List<User> result = jdbc.query(
                "SELECT id, email, password_hash FROM users WHERE email = ?", mapper, email);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public User save(String email, String passwordHash) {
        return jdbc.queryForObject(
                "INSERT INTO users (email, password_hash) VALUES (?, ?) RETURNING id, email, password_hash",
                mapper, email, passwordHash);
    }
}