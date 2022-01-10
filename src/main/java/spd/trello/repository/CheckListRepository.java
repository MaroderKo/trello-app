package spd.trello.repository;

import spd.trello.db.ConnectionPool;
import spd.trello.domain.CheckList;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static spd.trello.repository.RepositoryUtil.toLocalDateTime;


public class CheckListRepository implements IRepository<CheckList> {
    @Override
    public CheckList create(UUID parent, CheckList checkList) {
        try (
                Connection connection = ConnectionPool.get().getConnection();
                PreparedStatement ps = connection.prepareStatement("INSERT INTO CheckList (id,card_id, updated_by, created_by, created_date, updated_date, name) VALUES (?, ?, ?, ?, ?, ?, ?);")) {

            ps.setObject(1, checkList.getId(), Types.OTHER);
            ps.setObject(2, parent, Types.OTHER);
            ps.setObject(3, checkList.getUpdatedBy() == null ? null : checkList.getUpdatedBy());
            ps.setString(4, checkList.getCreatedBy());
            ps.setObject(5, Timestamp.valueOf(checkList.getCreatedDate()), Types.TIMESTAMP);
            ps.setObject(6, checkList.getUpdatedDate() == null ? null : Timestamp.valueOf(checkList.getUpdatedDate()), Types.TIMESTAMP);
            ps.setString(7, checkList.getName());

            ps.execute();


        } catch (SQLException e) {
            System.err.println("SQL EXCEPTION");
            e.printStackTrace();
        }
        return read(checkList.getId());
    }

    @Override
    public void update(CheckList checkList) {
        checkList.setUpdatedDate(LocalDateTime.now());
        try (
                Connection connection = ConnectionPool.get().getConnection();
                PreparedStatement ps = connection.prepareStatement("UPDATE CheckList SET updated_by = ?, updated_date = ?, name = ? WHERE id = \'" + checkList.getId() + "\';")) {


            ps.setObject(1, checkList.getUpdatedBy() == null ? null : checkList.getUpdatedBy());
            ps.setObject(2, checkList.getUpdatedDate() == null ? null : Timestamp.valueOf(checkList.getUpdatedDate()), Types.TIMESTAMP);
            ps.setString(3, checkList.getName());

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
                PreparedStatement ps = connection.prepareStatement("DELETE FROM CheckList WHERE id = \'" + uuid + "\';")) {
            ps.execute();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    @Override
    public CheckList read(UUID uuid) {
        CheckList checkList = null;
        try (
                Connection connection = ConnectionPool.get().getConnection();
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM checklist WHERE id = \'" + uuid.toString() + "\';")) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                checkList = new CheckList();
                checkList.setId(UUID.fromString(rs.getString("id")));
                if (rs.getString("updated_by") != null) {
                    checkList.setUpdatedBy(rs.getString("updated_by"));
                }
                checkList.setCreatedBy(rs.getString("created_by"));
                checkList.setCreatedDate(toLocalDateTime(rs.getString("created_date")));
                if (rs.getString("updated_date") != null) {
                    checkList.setUpdatedDate(toLocalDateTime(rs.getString("updated_date")));
                }
                checkList.setName(rs.getString("name"));
            }


        } catch (SQLException e) {
            System.err.println("Ошибка при обращении к базе данных");
            e.printStackTrace();
        }
        return checkList;
    }

    @Override
    public List<CheckList> getAll() {
        List<CheckList> checkLists = new ArrayList<>();
        try (
                Connection connection = ConnectionPool.get().getConnection();
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM CheckList")) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CheckList checkList = new CheckList();
                checkList.setId(UUID.fromString(rs.getString("id")));
                if (rs.getString("updated_by") != null) {
                    checkList.setUpdatedBy(rs.getString("updated_by"));
                }
                checkList.setCreatedBy(rs.getString("created_by"));
                checkList.setCreatedDate(toLocalDateTime(rs.getString("created_date")));
                if (rs.getString("updated_date") != null) {
                    checkList.setUpdatedDate(toLocalDateTime(rs.getString("updated_date")));
                }
                checkList.setName(rs.getString("name"));
                checkLists.add(checkList);
            }


        } catch (SQLException e) {
            System.err.println("Ошибка при обращении к базе данных");
            e.printStackTrace();
        }
        return checkLists;
    }

    @Override
    public List<CheckList> getParrent(UUID id) {
        List<CheckList> checkLists = new ArrayList<>();
        try (
                Connection connection = ConnectionPool.get().getConnection();
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM CheckList WHERE card_id = \'" + id.toString() + "\';")) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CheckList checkList = new CheckList();
                checkList.setId(UUID.fromString(rs.getString("id")));
                if (rs.getString("updated_by") != null) {
                    checkList.setUpdatedBy(rs.getString("updated_by"));
                }
                checkList.setCreatedBy(rs.getString("created_by"));
                checkList.setCreatedDate(toLocalDateTime(rs.getString("created_date")));
                if (rs.getString("updated_date") != null) {
                    checkList.setUpdatedDate(toLocalDateTime(rs.getString("updated_date")));
                }
                checkList.setName(rs.getString("name"));
                checkLists.add(checkList);
            }


        } catch (SQLException e) {
            System.err.println("Ошибка при обращении к базе данных");
            e.printStackTrace();
        }
        return checkLists;
    }

}
