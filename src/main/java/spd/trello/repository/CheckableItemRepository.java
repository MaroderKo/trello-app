package spd.trello.repository;

import spd.trello.db.ConnectionPool;
import spd.trello.domain.CheckableItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CheckableItemRepository implements IRepository<CheckableItem> {
    @Override
    public CheckableItem create(UUID parent, CheckableItem checkableItem) {
        try (
                Connection connection = ConnectionPool.get().getConnection();
                PreparedStatement ps = connection.prepareStatement("INSERT INTO checkable_item (id, checklist_id, name, checked) VALUES (?, ?, ?, ?);")) {

            ps.setObject(1, checkableItem.getId(), Types.OTHER);
            ps.setObject(2, parent, Types.OTHER);
            ps.setString(3, checkableItem.getName());
            ps.setBoolean(4, checkableItem.getChecked());

            ps.execute();


        } catch (SQLException e) {
            System.err.println("SQL EXCEPTION");
            e.printStackTrace();
        }
        return read(checkableItem.getId());
    }

    @Override
    public void update(CheckableItem CheckableItem) {
        try (
                Connection connection = ConnectionPool.get().getConnection();
                PreparedStatement ps = connection.prepareStatement("UPDATE checkable_item SET name = ?, checked = ? WHERE id = \'" + CheckableItem.getId() + "\';")) {

            ps.setString(1, CheckableItem.getName());
            ps.setBoolean(2, CheckableItem.getChecked());

            ps.execute();

        } catch (SQLException e) {
            System.err.println("SQL EXCEPTION");
            e.printStackTrace();
        }
    }

    @Override
    public void delete(UUID uuid) {
        try (
                Connection connection = ConnectionPool.get().getConnection();
                PreparedStatement ps = connection.prepareStatement("DELETE FROM checkable_item WHERE id = \'" + uuid + "\';")) {
            ps.execute();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    @Override
    public CheckableItem read(UUID uuid) {
        CheckableItem CheckableItem = null;
        try (
                Connection connection = ConnectionPool.get().getConnection();
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM checkable_item WHERE id = \'" + uuid.toString() + "\';")) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CheckableItem = new CheckableItem();
                CheckableItem.setId(UUID.fromString(rs.getString("id")));
                CheckableItem.setName(rs.getString("name"));
                CheckableItem.setChecked(rs.getBoolean("checked"));
            }


        } catch (SQLException e) {
            System.err.println("Ошибка при обращении к базе данных");
            e.printStackTrace();
        }
        return CheckableItem;
    }

    @Override
    public List<CheckableItem> getAll() {
        List<CheckableItem> CheckableItems = new ArrayList<>();
        try (
                Connection connection = ConnectionPool.get().getConnection();
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM checkable_item")) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CheckableItem CheckableItem = new CheckableItem();
                CheckableItem.setId(UUID.fromString(rs.getString("id")));
                CheckableItem.setName(rs.getString("name"));
                CheckableItem.setChecked(rs.getBoolean("checked"));
                CheckableItems.add(CheckableItem);
            }


        } catch (SQLException e) {
            System.err.println("Ошибка при обращении к базе данных");
            e.printStackTrace();
        }
        return CheckableItems;
    }

    @Override
    public List<CheckableItem> getParrent(UUID id) {
        List<CheckableItem> CheckableItems = new ArrayList<>();
        try (
                Connection connection = ConnectionPool.get().getConnection();
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM checkable_item WHERE checklist_id = \'" + id.toString() + "\';")) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CheckableItem CheckableItem = new CheckableItem();
                CheckableItem.setId(UUID.fromString(rs.getString("id")));
                CheckableItem.setName(rs.getString("name"));
                CheckableItem.setChecked(rs.getBoolean("checked"));
                CheckableItems.add(CheckableItem);
            }


        } catch (SQLException e) {
            System.err.println("Ошибка при обращении к базе данных");
            e.printStackTrace();
        }
        return CheckableItems;
    }

}
