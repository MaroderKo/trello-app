package spd.trello.repository;

import spd.trello.db.ConnectionPool;
import spd.trello.domain.CardList;
import spd.trello.domain.BoardVisibility;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static spd.trello.repository.RepositoryUtil.toLocalDateTime;


public class CardListRepository implements IRepository<CardList> {
    @Override
    public CardList create(UUID parent, CardList cardList) {
        try (
                Connection connection = ConnectionPool.get().getConnection();
                PreparedStatement ps = connection.prepareStatement("INSERT INTO cardlist (id,board_id, updated_by, created_by, created_date, updated_date, name, archived) VALUES (?, ?, ?, ?, ?, ?, ?, ?);")) {

            ps.setObject(1, cardList.getId(), Types.OTHER);
            ps.setObject(2, parent, Types.OTHER);
            ps.setObject(3, cardList.getUpdatedBy() == null ? null : cardList.getUpdatedBy());
            ps.setString(4, cardList.getCreatedBy());
            ps.setObject(5, Timestamp.valueOf(cardList.getCreatedDate()), Types.TIMESTAMP);
            ps.setObject(6, cardList.getUpdatedDate() == null ? null : Timestamp.valueOf(cardList.getUpdatedDate()), Types.TIMESTAMP);
            ps.setString(7, cardList.getName());
            ps.setBoolean(8, cardList.getArchived());

            ps.execute();


        } catch (SQLException e) {
            System.err.println("SQL EXCEPTION");
            e.printStackTrace();
        }
        return read(cardList.getId());
    }

    @Override
    public void update(CardList cardList) {
        cardList.setUpdatedDate(LocalDateTime.now());
        try (
                Connection connection = ConnectionPool.get().getConnection();
                PreparedStatement ps = connection.prepareStatement("UPDATE cardlist SET updated_by = ?, updated_date = ?, name = ?, archived = ? WHERE id = \'" + cardList.getId() + "\';")) {


            ps.setObject(1, cardList.getUpdatedBy() == null ? null : cardList.getUpdatedBy());
            ps.setObject(2, cardList.getUpdatedDate() == null ? null : Timestamp.valueOf(cardList.getUpdatedDate()), Types.TIMESTAMP);
            ps.setString(3, cardList.getName());
            ps.setBoolean(4, cardList.getArchived());

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
                PreparedStatement ps = connection.prepareStatement("DELETE FROM cardlist WHERE id = \'" + uuid + "\';")) {
            ps.execute();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    @Override
    public CardList read(UUID uuid) {
        CardList cardList = null;
        try (
                Connection connection = ConnectionPool.get().getConnection();
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM cardlist WHERE id = \'" + uuid.toString() + "\';")) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                cardList = new CardList();
                cardList.setId(UUID.fromString(rs.getString("id")));
                if (rs.getString("updated_by") != null) {
                    cardList.setUpdatedBy(rs.getString("updated_by"));
                }
                cardList.setCreatedBy(rs.getString("created_by"));
                cardList.setCreatedDate(toLocalDateTime(rs.getString("created_date")));
                if (rs.getString("updated_date") != null) {
                    cardList.setUpdatedDate(toLocalDateTime(rs.getString("updated_date")));
                }
                cardList.setName(rs.getString("name"));
                cardList.setArchived(rs.getBoolean("archived"));
            }


        } catch (SQLException e) {
            System.err.println("Ошибка при обращении к базе данных");
            e.printStackTrace();
        }
        return cardList;
    }

    @Override
    public List<CardList> getAll() {
        List<CardList> boards = new ArrayList<>();
        try (
                Connection connection = ConnectionPool.get().getConnection();
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM cardlist")) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CardList board = new CardList();
                board.setId(UUID.fromString(rs.getString("id")));
                if (rs.getString("updated_by") != null) {
                    board.setUpdatedBy(rs.getString("updated_by"));
                }
                board.setCreatedBy(rs.getString("created_by"));
                board.setCreatedDate(toLocalDateTime(rs.getString("created_date")));
                if (rs.getString("updated_date") != null) {
                    board.setUpdatedDate(toLocalDateTime(rs.getString("updated_date")));
                }
                board.setName(rs.getString("name"));
                board.setArchived(rs.getBoolean("archived"));
                boards.add(board);
            }


        } catch (SQLException e) {
            System.err.println("Ошибка при обращении к базе данных");
            e.printStackTrace();
        }
        return boards;
    }

    @Override
    public List<CardList> getParrent(UUID id) {
        List<CardList> boards = new ArrayList<>();
        try (
                Connection connection = ConnectionPool.get().getConnection();
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM cardlist WHERE board_id = \'" + id.toString() + "\';")) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CardList board = new CardList();
                board.setId(UUID.fromString(rs.getString("id")));
                if (rs.getString("updated_by") != null) {
                    board.setUpdatedBy(rs.getString("updated_by"));
                }
                board.setCreatedBy(rs.getString("created_by"));
                board.setCreatedDate(toLocalDateTime(rs.getString("created_date")));
                if (rs.getString("updated_date") != null) {
                    board.setUpdatedDate(toLocalDateTime(rs.getString("updated_date")));
                }
                board.setName(rs.getString("name"));
                board.setArchived(rs.getBoolean("archived"));
                boards.add(board);
            }


        } catch (SQLException e) {
            System.err.println("Ошибка при обращении к базе данных");
            e.printStackTrace();
        }
        return boards;
    }

}
