package spd.trello.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import spd.trello.domain.CardList;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


public class CardListRepository extends AbstractRepository<CardList> {
    @Override
    public CardList create(CardList cardList) {
        jdbcTemplate.update("INSERT INTO cardlist (id, board_id, updated_by, created_by, created_date, updated_date, name, archived) VALUES (?, ?, ?, ?, ?, ?, ?, ?);",
                cardList.getId(),
                cardList.getBoardId(),
                cardList.getUpdatedBy(),
                cardList.getCreatedBy(),
                cardList.getCreatedDate(),
                cardList.getUpdatedDate(),
                cardList.getName(),
                cardList.getArchived());
        return getById(cardList.getId());
    }

    @Override
    public CardList update(CardList cardList) {
        cardList.setUpdatedDate(LocalDateTime.now());
        jdbcTemplate.update("UPDATE cardList SET updated_by = ?, updated_date = ?, name = ?, archived = ? WHERE id = ?",
                cardList.getUpdatedBy(),
                cardList.getUpdatedDate(),
                cardList.getName(),
                cardList.getArchived(),
                cardList.getId());
        return getById(cardList.getId());
    }

    @Override
    public void delete(UUID uuid) {
        jdbcTemplate.update("DELETE FROM cardlist WHERE id = ?", uuid);

    }

    @Override
    public CardList getById(UUID uuid) {
        return jdbcTemplate.query("SELECT * FROM cardlist WHERE id = ?",
                new BeanPropertyRowMapper<>(CardList.class), uuid).stream().findFirst().orElse(null);
    }

    @Override
    public List<CardList> getAll() {
        return jdbcTemplate.query("SELECT * FROM cardlist", new BeanPropertyRowMapper<>(CardList.class));
    }

    @Override
    public List<CardList> getParent(UUID parentId) {
        return jdbcTemplate.query("SELECT * FROM cardlist WHERE board_id = ?", new BeanPropertyRowMapper<>(CardList.class), parentId);

    }

}
