package spd.trello.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import spd.trello.db.ConnectionPool;
import spd.trello.domain.Comment;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static spd.trello.repository.RepositoryUtil.toLocalDateTime;

@Component
public class CommentRepository extends AbstractRepository<Comment> {
    @Override
    public Comment create(Comment comment) {
        jdbcTemplate.update("INSERT INTO Comment (id, card_id, updated_by, created_by, created_date, updated_date, author, text, archived) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);",
                comment.getId(),
                comment.getCardId(),
                comment.getUpdatedBy(),
                comment.getCreatedBy(),
                comment.getCreatedDate(),
                comment.getUpdatedDate(),
                comment.getAuthor(),
                comment.getText(),
                comment.getArchived());
        return getById(comment.getId());
    }

    @Override
    public Comment update(Comment comment) {
        comment.setUpdatedDate(LocalDateTime.now());
        jdbcTemplate.update("UPDATE Comment SET updated_by = ?, updated_date = ?, text = ?, archived = ? WHERE id = ?",
                comment.getUpdatedBy(),
                comment.getUpdatedDate(),
                comment.getText(),
                comment.getArchived(),
                comment.getId());
        return getById(comment.getId());
    }

    @Override
    public void delete(UUID uuid) {
        jdbcTemplate.update("DELETE FROM Comment WHERE id = ?", uuid);
    }

    @Override
    public Comment getById(UUID uuid) {
        return jdbcTemplate.query("SELECT * FROM Comment WHERE id = ?", new BeanPropertyRowMapper<>(Comment.class),uuid).stream().findFirst().orElse(null);
    }

    @Override
    public List<Comment> getAll() {
        return jdbcTemplate.query("SELECT * FROM Comment", new BeanPropertyRowMapper<>(Comment.class));
    }

    @Override
    public List<Comment> getParent(UUID id) {
        return jdbcTemplate.query("SELECT * FROM Comment WHERE card_id = ?", new BeanPropertyRowMapper<>(Comment.class),id);
    }

}
