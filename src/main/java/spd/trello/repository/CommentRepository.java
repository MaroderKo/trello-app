package spd.trello.repository;

import spd.trello.db.ConnectionPool;
import spd.trello.domain.Comment;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static spd.trello.repository.RepositoryUtil.toLocalDateTime;


public class CommentRepository implements IRepository<Comment> {
    @Override
    public Comment create(UUID parent, Comment Comment) {
        try (
                Connection connection = ConnectionPool.get().getConnection();
                PreparedStatement ps = connection.prepareStatement("INSERT INTO Comment (id, card_id, updated_by, created_by, created_date, updated_date, author, text, archived) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);")) {

            ps.setObject(1, Comment.getId(), Types.OTHER);
            ps.setObject(2, parent, Types.OTHER);
            ps.setObject(3, Comment.getUpdatedBy() == null ? null : Comment.getUpdatedBy());
            ps.setString(4, Comment.getCreatedBy());
            ps.setObject(5, Timestamp.valueOf(Comment.getCreatedDate()), Types.TIMESTAMP);
            ps.setObject(6, Comment.getUpdatedDate() == null ? null : Timestamp.valueOf(Comment.getUpdatedDate()), Types.TIMESTAMP);
            ps.setString(7, Comment.getAuthor());
            ps.setString(8, Comment.getText());
            ps.setBoolean(9, Comment.getArchived());

            ps.execute();


        } catch (SQLException e) {
            System.err.println("Error occurred while connecting to database");
            e.printStackTrace();
        }
        return getById(Comment.getId());
    }

    @Override
    public Comment update(Comment comment) {
        comment.setUpdatedDate(LocalDateTime.now());
        try (
                Connection connection = ConnectionPool.get().getConnection();
                PreparedStatement ps = connection.prepareStatement("UPDATE Comment SET updated_by = ?, updated_date = ?, text = ?, archived = ? WHERE id = '" + comment.getId() + "';")) {


            ps.setObject(1, comment.getUpdatedBy() == null ? null : comment.getUpdatedBy());
            ps.setObject(2, comment.getUpdatedDate() == null ? null : Timestamp.valueOf(comment.getUpdatedDate()), Types.TIMESTAMP);
            ps.setString(3, comment.getText());
            ps.setBoolean(4, comment.getArchived());

            ps.execute();

        } catch (SQLException e) {
            System.err.println("Error occurred while connecting to database");
            e.printStackTrace();
        }

        return getById(comment.getId());
    }

    @Override
    public void delete(UUID uuid) {
        try (
                Connection connection = ConnectionPool.get().getConnection();
                PreparedStatement ps = connection.prepareStatement("DELETE FROM Comment WHERE id = '" + uuid + "';")) {
            ps.execute();
        } catch (SQLException e) {
            System.err.println("Error occurred while connecting to database");
            e.printStackTrace();
        }

    }

    @Override
    public Comment getById(UUID uuid) {
        Comment Comment = null;
        try (
                Connection connection = ConnectionPool.get().getConnection();
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM Comment WHERE id = '" + uuid.toString() + "';")) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Comment = new Comment();
                Comment.setId(UUID.fromString(rs.getString("id")));
                if (rs.getString("updated_by") != null) {
                    Comment.setUpdatedBy(rs.getString("updated_by"));
                }
                Comment.setCreatedBy(rs.getString("created_by"));
                Comment.setCreatedDate(toLocalDateTime(rs.getString("created_date")));
                if (rs.getString("updated_date") != null) {
                    Comment.setUpdatedDate(toLocalDateTime(rs.getString("updated_date")));
                }
                Comment.setAuthor(rs.getString("author"));
                Comment.setText(rs.getString("text"));
                Comment.setArchived(rs.getBoolean("archived"));
            }


        } catch (SQLException e) {
            System.err.println("Error occurred while connecting to database");
            e.printStackTrace();
        }
        return Comment;
    }

    @Override
    public List<Comment> getAll() {
        List<Comment> Comments = new ArrayList<>();
        try (
                Connection connection = ConnectionPool.get().getConnection();
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM Comment")) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Comment Comment = new Comment();
                Comment.setId(UUID.fromString(rs.getString("id")));
                if (rs.getString("updated_by") != null) {
                    Comment.setUpdatedBy(rs.getString("updated_by"));
                }
                Comment.setCreatedBy(rs.getString("created_by"));
                Comment.setCreatedDate(toLocalDateTime(rs.getString("created_date")));
                if (rs.getString("updated_date") != null) {
                    Comment.setUpdatedDate(toLocalDateTime(rs.getString("updated_date")));
                }
                Comment.setAuthor(rs.getString("author"));
                Comment.setText(rs.getString("text"));
                Comment.setArchived(rs.getBoolean("archived"));
                Comments.add(Comment);
            }


        } catch (SQLException e) {
            System.err.println("Error occurred while connecting to database");
            e.printStackTrace();
        }
        return Comments;
    }

    @Override
    public List<Comment> getParrent(UUID id) {
        List<Comment> Comments = new ArrayList<>();
        try (
                Connection connection = ConnectionPool.get().getConnection();
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM Comment WHERE card_id = '" + id.toString() + "';")) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Comment Comment = new Comment();
                Comment.setId(UUID.fromString(rs.getString("id")));
                if (rs.getString("updated_by") != null) {
                    Comment.setUpdatedBy(rs.getString("updated_by"));
                }
                Comment.setCreatedBy(rs.getString("created_by"));
                Comment.setCreatedDate(toLocalDateTime(rs.getString("created_date")));
                if (rs.getString("updated_date") != null) {
                    Comment.setUpdatedDate(toLocalDateTime(rs.getString("updated_date")));
                }
                Comment.setAuthor(rs.getString("author"));
                Comment.setText(rs.getString("text"));
                Comment.setArchived(rs.getBoolean("archived"));
                Comments.add(Comment);
            }


        } catch (SQLException e) {
            System.err.println("Error occurred while connecting to database");
            e.printStackTrace();
        }
        return Comments;
    }

}
