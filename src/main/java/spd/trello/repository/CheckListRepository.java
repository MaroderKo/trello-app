package spd.trello.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import spd.trello.db.ConnectionPool;
import spd.trello.domain.CheckList;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static spd.trello.repository.RepositoryUtil.toLocalDateTime;

@Component
public class CheckListRepository extends AbstractRepository<CheckList> {
    @Override
    public CheckList create(CheckList checkList) {
        jdbcTemplate.update("INSERT INTO CheckList (id,card_id, updated_by, created_by, created_date, updated_date, name) VALUES (?, ?, ?, ?, ?, ?, ?);",
                checkList.getId(),
                checkList.getCardId(),
                checkList.getUpdatedBy(),
                checkList.getCreatedBy(),
                checkList.getCreatedDate(),
                checkList.getUpdatedDate(),
                checkList.getName());
        return getById(checkList.getId());
    }

    @Override
    public CheckList update(CheckList checkList) {
        checkList.setUpdatedDate(LocalDateTime.now());
        jdbcTemplate.update("UPDATE CheckList SET updated_by = ?, updated_date = ?, name = ? WHERE id = ?",
                checkList.getUpdatedBy(),
                checkList.getUpdatedDate(),
                checkList.getName(),
                checkList.getId());
        return getById(checkList.getId());
    }

    @Override
    public void delete(UUID uuid) {
        jdbcTemplate.update("DELETE FROM CheckList WHERE id = ?", uuid);
    }

    @Override
    public CheckList getById(UUID uuid) {
        return jdbcTemplate.query("SELECT * FROM checklist WHERE id = ?", new BeanPropertyRowMapper<>(CheckList.class),uuid).stream().findFirst().orElse(null);
    }

    @Override
    public List<CheckList> getAll() {
       return jdbcTemplate.query("SELECT * FROM CheckList", new BeanPropertyRowMapper<>(CheckList.class));
    }

    @Override
    public List<CheckList> getParent(UUID id) {
        return jdbcTemplate.query("SELECT * FROM CheckList WHERE card_id = ?", new BeanPropertyRowMapper<>(CheckList.class),id);
    }

}
