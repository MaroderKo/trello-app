package spd.trello.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import spd.trello.db.ConnectionPool;
import spd.trello.domain.Board;
import spd.trello.domain.BoardVisibility;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static spd.trello.repository.RepositoryUtil.toLocalDateTime;


public class BoardRepository extends AbstractRepository<Board> {
    @Override
    public Board create(Board board) {
        jdbcTemplate.update("INSERT INTO Board (id,workspace_id, updated_by, created_by, created_date, updated_date, name, description, archived, visibility) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);",
                board.getId(),
                board.getWorkspaceId(),
                board.getUpdatedBy(),
                board.getCreatedBy(),
                board.getCreatedDate(),
                board.getUpdatedDate(),
                board.getName(),
                board.getDescription(),
                board.getArchived(),
                board.getVisibility().toString());

        return getById(board.getId());
    }

    @Override
    public Board update(Board board) {
        board.setUpdatedDate(LocalDateTime.now());
        jdbcTemplate.update("UPDATE Board SET updated_by = ?, updated_date = ?, name = ?, description = ?, archived = ?, visibility = ? WHERE id = ?;",
                board.getUpdatedBy(),
                board.getUpdatedDate(),
                board.getName(),
                board.getDescription(),
                board.getArchived(),
                board.getVisibility().toString(),
                board.getId());


        return getById(board.getId());
    }

    @Override
    public void delete(UUID uuid) {
        jdbcTemplate.update("DELETE FROM board WHERE id = ?", uuid);
    }

    @Override
    public Board getById(UUID uuid) {
        return jdbcTemplate.query("SELECT * FROM board WHERE id = ?", new BeanPropertyRowMapper<>(Board.class), uuid).stream().findFirst().orElse(null);
    }

    @Override
    public List<Board> getAll() {
        return jdbcTemplate.query("SELECT * FROM board", new BeanPropertyRowMapper<>(Board.class));
    }

    @Override
    public List<Board> getParent(UUID id) {
        return jdbcTemplate.query("SELECT * FROM board WHERE workspace_id = ?", new BeanPropertyRowMapper<>(Board.class), id);
    }

}
