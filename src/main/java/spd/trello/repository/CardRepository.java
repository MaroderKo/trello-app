package spd.trello.repository;

import spd.trello.db.ConnectionPool;
import spd.trello.domain.Card;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static spd.trello.repository.RepositoryUtil.toLocalDateTime;


public class CardRepository implements IRepository<Card> {
    @Override
    public Card create(UUID parent, Card Card) {
        try (
                Connection connection = ConnectionPool.get().getConnection();
                PreparedStatement ps = connection.prepareStatement("INSERT INTO card (id,cardlist_id, updated_by, created_by, created_date, updated_date, name, description, archived) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);")) {

            ps.setObject(1, Card.getId(), Types.OTHER);
            ps.setObject(2, parent, Types.OTHER);
            ps.setObject(3, Card.getUpdatedBy() == null ? null : Card.getUpdatedBy());
            ps.setString(4, Card.getCreatedBy());
            ps.setObject(5, Timestamp.valueOf(Card.getCreatedDate()), Types.TIMESTAMP);
            ps.setObject(6, Card.getUpdatedDate() == null ? null : Timestamp.valueOf(Card.getUpdatedDate()), Types.TIMESTAMP);
            ps.setString(7, Card.getName());
            ps.setString(8, Card.getDescription());
            ps.setBoolean(9, Card.getArchived());

            ps.execute();


        } catch (SQLException e) {
            System.err.println("Error occurred while connecting to database");
            e.printStackTrace();
        }
        return getById(Card.getId());
    }

    @Override
    public Card update(Card card) {
        card.setUpdatedDate(LocalDateTime.now());
        try (
                Connection connection = ConnectionPool.get().getConnection();
                PreparedStatement ps = connection.prepareStatement("UPDATE card SET updated_by = ?, updated_date = ?, name = ?, description = ?, archived = ? WHERE id = '" + card.getId() + "';")) {


            ps.setObject(1, card.getUpdatedBy() == null ? null : card.getUpdatedBy());
            ps.setObject(2, card.getUpdatedDate() == null ? null : Timestamp.valueOf(card.getUpdatedDate()), Types.TIMESTAMP);
            ps.setString(3, card.getName());
            ps.setString(4, card.getDescription());
            ps.setBoolean(5, card.getArchived());

            ps.execute();

        } catch (SQLException e) {
            System.err.println("Error occurred while connecting to database");
            e.printStackTrace();
        }

        return getById(card.getId());
    }

    @Override
    public void delete(UUID uuid) {
        try (
                Connection connection = ConnectionPool.get().getConnection();
                PreparedStatement ps = connection.prepareStatement("DELETE FROM card WHERE id = '" + uuid + "';")) {
            ps.execute();
        } catch (SQLException e) {
            System.err.println("Error occurred while connecting to database");
            e.printStackTrace();
        }

    }

    @Override
    public Card getById(UUID uuid) {
        Card Card = null;
        try (
                Connection connection = ConnectionPool.get().getConnection();
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM card WHERE id = '" + uuid.toString() + "';")) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Card = new Card();
                Card.setId(UUID.fromString(rs.getString("id")));
                if (rs.getString("updated_by") != null) {
                    Card.setUpdatedBy(rs.getString("updated_by"));
                }
                Card.setCreatedBy(rs.getString("created_by"));
                Card.setCreatedDate(toLocalDateTime(rs.getString("created_date")));
                if (rs.getString("updated_date") != null) {
                    Card.setUpdatedDate(toLocalDateTime(rs.getString("updated_date")));
                }
                Card.setName(rs.getString("name"));
                Card.setDescription(rs.getString("description"));
                Card.setArchived(rs.getBoolean("archived"));
            }


        } catch (SQLException e) {
            System.err.println("Error occurred while connecting to database");
            e.printStackTrace();
        }
        return Card;
    }

    @Override
    public List<Card> getAll() {
        List<Card> Cards = new ArrayList<>();
        try (
                Connection connection = ConnectionPool.get().getConnection();
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM Card")) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Card Card = new Card();
                Card.setId(UUID.fromString(rs.getString("id")));
                if (rs.getString("updated_by") != null) {
                    Card.setUpdatedBy(rs.getString("updated_by"));
                }
                Card.setCreatedBy(rs.getString("created_by"));
                Card.setCreatedDate(toLocalDateTime(rs.getString("created_date")));
                if (rs.getString("updated_date") != null) {
                    Card.setUpdatedDate(toLocalDateTime(rs.getString("updated_date")));
                }
                Card.setName(rs.getString("name"));
                Card.setDescription(rs.getString("description"));
                Card.setArchived(rs.getBoolean("archived"));
                Cards.add(Card);
            }


        } catch (SQLException e) {
            System.err.println("Error occurred while connecting to database");
            e.printStackTrace();
        }
        return Cards;
    }

    @Override
    public List<Card> getParrent(UUID id) {
        List<Card> Cards = new ArrayList<>();
        try (
                Connection connection = ConnectionPool.get().getConnection();
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM Card WHERE cardlist_id = '" + id.toString() + "';")) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Card Card = new Card();
                Card.setId(UUID.fromString(rs.getString("id")));
                if (rs.getString("updated_by") != null) {
                    Card.setUpdatedBy(rs.getString("updated_by"));
                }
                Card.setCreatedBy(rs.getString("created_by"));
                Card.setCreatedDate(toLocalDateTime(rs.getString("created_date")));
                if (rs.getString("updated_date") != null) {
                    Card.setUpdatedDate(toLocalDateTime(rs.getString("updated_date")));
                }
                Card.setName(rs.getString("name"));
                Card.setDescription(rs.getString("description"));
                Card.setArchived(rs.getBoolean("archived"));
                Cards.add(Card);
            }


        } catch (SQLException e) {
            System.err.println("Error occurred while connecting to database");
            e.printStackTrace();
        }
        return Cards;
    }

}
