package com.ledger;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class JdbcTaskStore implements TaskStore {

    private final JdbcTemplate jdbc;

    // RowMapper turns one database row into one Task
    private final RowMapper<Task> mapper = (rs, rowNum) -> new Task(rs.getInt("id"), rs.getString("title"),
            rs.getBoolean("done"));

    public JdbcTaskStore(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public boolean delete(int id) {
        int rowsAffected = jdbc.update("DELETE FROM tasks WHERE id = ?", id);
        return rowsAffected > 0;
    }

    @Override
    public List<Task> findAll() {
        return jdbc.query("SELECT id , title, done FROM tasks ORDER BY id", mapper);
    }

    @Override
    public Task findById(int id) {
        List<Task> result = jdbc.query(
                "SELECT id,title,done FROM tasks WHERE id= ?", mapper, id);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public Task save(CreateTaskRecord task) {
        String query="INSERT INTO tasks (title,done) VALUES (?,?) RETURNING id,title,done";
        // jdbcTemplate.queryForObject() is used to execute a SQL query and map a single
        // row or single column result directly to a Java object.
        Task result=jdbc.queryForObject(query, mapper,task.title(),task.done());
        return result;
    }

    @Override
    public Task update(int id, CreateTaskRecord request) {
        String query = "UPDATE tasks SET title = ?, done = ? WHERE id = ? RETURNING id, title, done";       
         // Use jdbc.query(...) here, not queryForObject, because a missing id means zero
        // rows and queryForObject would throw
        List<Task> result =jdbc.query(query, mapper,request.title(),request.done(),id);
        return result.isEmpty() ? null : result.get(0);
    }

}
