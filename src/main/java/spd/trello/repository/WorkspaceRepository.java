package spd.trello.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import spd.trello.db.ConnectionPool;
import spd.trello.domain.Workspace;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class WorkspaceRepository extends AbstractRepository<Workspace> {

    @Override
    public Workspace create(Workspace workspace) {
        jdbcTemplate.update("INSERT INTO workspace (id, updated_by, created_by, created_date, updated_date, name, description, visibility) VALUES (?, ?, ?, ?, ?, ?, ?, ?);",
                workspace.getId(),
                workspace.getUpdatedBy(),
                workspace.getCreatedBy(),
                workspace.getCreatedDate(),
                workspace.getUpdatedDate(),
                workspace.getName(),
                workspace.getDescription(),
                workspace.getVisibility().toString());
        return getById(workspace.getId());
    }

    @Override
    public Workspace update(Workspace workspace) {
        workspace.setUpdatedDate(LocalDateTime.now());
        jdbcTemplate.update("UPDATE workspace SET updated_by = ?, updated_date = ?, name = ?, description = ?, visibility = ? WHERE id = ?",
                workspace.getUpdatedBy(),
                workspace.getUpdatedDate(),
                workspace.getName(),
                workspace.getDescription(),
                workspace.getVisibility().name(),
                workspace.getId());

        return getById(workspace.getId());
    }

    @Override
    public void delete(UUID uuid) {

        jdbcTemplate.update("DELETE FROM workspace WHERE id = ?", uuid);


    }

    @Override
    public Workspace getById(UUID uuid) {
        return jdbcTemplate.query("SELECT * FROM workspace WHERE id = ?",
                new BeanPropertyRowMapper<>(Workspace.class), uuid).stream().findFirst().orElse(null);
    }

    @Override
    public List<Workspace> getAll() {
        return jdbcTemplate.query("SELECT * FROM workspace", new BeanPropertyRowMapper<>(Workspace.class));
    }

    @Override
    public List<Workspace> getParent(UUID parentId) {
        return new ArrayList<>();
    }
}
