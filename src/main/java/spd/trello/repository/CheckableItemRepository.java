package spd.trello.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import spd.trello.domain.CheckableItem;

import java.util.List;
import java.util.UUID;

@Component
public class CheckableItemRepository extends AbstractRepository<CheckableItem> {
    @Override
    public CheckableItem create(CheckableItem checkableItem) {
        jdbcTemplate.update("INSERT INTO checkable_item (id, checklist_id, name, checked) VALUES (?, ?, ?, ?);",
                checkableItem.getId(),
                checkableItem.getChecklistId(),
                checkableItem.getName(),
                checkableItem.getChecked());
        return getById(checkableItem.getId());
    }

    @Override
    public CheckableItem update(CheckableItem checkableItem) {
        jdbcTemplate.update("UPDATE checkable_item SET name = ?, checked = ? WHERE id = ?",
                checkableItem.getName(),
                checkableItem.getChecked(),
                checkableItem.getId());
        return getById(checkableItem.getId());
    }

    @Override
    public void delete(UUID uuid) {
        jdbcTemplate.update("DELETE FROM checkable_item WHERE id = ?", uuid);
    }

    @Override
    public CheckableItem getById(UUID uuid) {
        return jdbcTemplate.query("SELECT * FROM checkable_item WHERE id = ?", new BeanPropertyRowMapper<>(CheckableItem.class),uuid).stream().findFirst().orElse(null);

    }

    @Override
    public List<CheckableItem> getAll() {

        return jdbcTemplate.query("SELECT * FROM checkable_item", new BeanPropertyRowMapper<>(CheckableItem.class));
    }

    @Override
    public List<CheckableItem> getParent(UUID id) {
        return jdbcTemplate.query("SELECT * FROM checkable_item WHERE checklist_id = ?", new BeanPropertyRowMapper<>(CheckableItem.class), id);

    }

}
