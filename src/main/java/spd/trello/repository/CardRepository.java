package spd.trello.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import spd.trello.domain.Card;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class CardRepository extends AbstractRepository<Card> {
    @Override
    public Card create(Card card) {
        jdbcTemplate.update("INSERT INTO card (id,cardlist_id, updated_by, created_by, created_date, updated_date, name, description, archived) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);",
                card.getId(),
                card.getCardlistId(),
                card.getUpdatedBy(),
                card.getCreatedBy(),
                card.getCreatedDate(),
                card.getUpdatedDate(),
                card.getName(),
                card.getDescription(),
                card.getArchived());

        return getById(card.getId());
    }

    @Override
    public Card update(Card card) {
        card.setUpdatedDate(LocalDateTime.now());
        jdbcTemplate.update("UPDATE card SET updated_by = ?, updated_date = ?, name = ?, description = ?, archived = ? WHERE id = ?",
                card.getUpdatedBy(),
                card.getUpdatedDate(),
                card.getName(),
                card.getDescription(),
                card.getArchived(),
                card.getId());


        return getById(card.getId());
    }

    @Override
    public void delete(UUID uuid) {
        jdbcTemplate.update("DELETE FROM card WHERE id = ?", uuid);


    }

    @Override
    public Card getById(UUID uuid) {
        return jdbcTemplate.query("SELECT * FROM card WHERE id = ?",
                new BeanPropertyRowMapper<>(Card.class), uuid).stream().findFirst().orElse(null);

    }

    @Override
    public List<Card> getAll() {
        return jdbcTemplate.query("SELECT * FROM card", new BeanPropertyRowMapper<>(Card.class));

    }

    @Override
    public List<Card> getParent(UUID id) {
        return jdbcTemplate.query("SELECT * FROM card WHERE cardlist_id = ?", new BeanPropertyRowMapper<>(Card.class), id);
    }

}
