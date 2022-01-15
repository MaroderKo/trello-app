package spd.trello.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import spd.trello.db.ConnectionPool;
import spd.trello.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



public class UserRepository extends AbstractRepository<User> {

    @Override
    public User create(User user) {
        jdbcTemplate.update("INSERT INTO \"user\" (id, firstname, lastname, email) VALUES (?, ?, ?, ?);",
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getEmail());
        return getById(user.getId());
    }

    @Override
    public User update(User user) {
        jdbcTemplate.update("UPDATE \"user\" SET firstname = ?, lastname = ?, email = ? WHERE id = ?",
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getId());
        return getById(user.getId());
    }

    @Override
    public void delete(UUID uuid) {
        jdbcTemplate.update("DELETE FROM \"user\" WHERE id = ?",uuid);
    }

    @Override
    public User getById(UUID uuid) {
        return jdbcTemplate.query("SELECT * FROM \"user\" WHERE id = ?",new BeanPropertyRowMapper<>(User.class),uuid).stream().findFirst().orElse(null);

    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM \"user\"",new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public List<User> getParent(UUID id) {
        return new ArrayList<>();
    }
}
