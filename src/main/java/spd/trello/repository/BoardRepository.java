package spd.trello.repository;

import spd.trello.db.ConnectionPool;
import spd.trello.domain.Board;
import spd.trello.domain.BoardVisibility;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static spd.trello.repository.RepositoryUtil.toLocalDateTime;


public class BoardRepository implements IRepository<Board> {
    @Override
    public Board create(UUID parent, Board board) {
        try (PreparedStatement ps = ConnectionPool.get().getConnection().prepareStatement("INSERT INTO Board (id,workspace_id, updated_by, created_by, created_date, updated_date, name, description, archived, visibility) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);")) {

            ps.setObject(1, board.getId(), Types.OTHER);
            ps.setObject(2, parent, Types.OTHER);
            ps.setObject(3, board.getUpdatedBy() == null ? null : board.getUpdatedBy());
            ps.setString(4, board.getCreatedBy());
            ps.setObject(5, Timestamp.valueOf(board.getCreatedDate()), Types.TIMESTAMP);
            ps.setObject(6, board.getUpdatedDate() == null ? null : Timestamp.valueOf(board.getUpdatedDate()), Types.TIMESTAMP);
            ps.setString(7, board.getName());
            ps.setString(8, board.getDescription());
            ps.setBoolean(9, false);
            ps.setString(10, board.getVisibility().toString());

            ps.execute();


        } catch (SQLException e) {
            System.err.println("SQL EXCEPTION");
            e.printStackTrace();
        }
        return read(board.getId());
    }

    @Override
    public void update(UUID uuid, Board board) {
        board.setUpdatedDate(LocalDateTime.now());
        try (PreparedStatement ps = ConnectionPool.get().getConnection().prepareStatement("UPDATE Board SET updated_by = ?, updated_date = ?, name = ?, description = ?, archived = ?, visibility = ? WHERE id = \'" + uuid + "\';")) {


            ps.setObject(1, board.getUpdatedBy() == null ? null : board.getUpdatedBy());
            ps.setObject(2, board.getUpdatedDate() == null ? null : Timestamp.valueOf(board.getUpdatedDate()), Types.TIMESTAMP);
            ps.setString(3, board.getName());
            ps.setString(4, board.getDescription());
            ps.setBoolean(5, board.getArchived());
            ps.setString(6, board.getVisibility().toString());

            ps.execute();

        } catch (SQLException e) {
            System.err.println("SQL EXCEPTION");
            e.printStackTrace();
        }
    }

    @Override
    public void delete(UUID uuid) {
        try (PreparedStatement ps = ConnectionPool.get().getConnection().prepareStatement("DELETE FROM board WHERE id = \'" + uuid + "\';")) {
            ps.execute();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    @Override
    public Board read(UUID uuid) {
        Board board = null;
        try (PreparedStatement ps = ConnectionPool.get().getConnection().prepareStatement("SELECT * FROM board WHERE id = \'" + uuid.toString() + "\';")) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                board = new Board();
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
                board.setDescription(rs.getString("description"));
                board.setArchived(rs.getBoolean("archived"));
                board.setVisibility(BoardVisibility.valueOf(rs.getString("visibility")));
            }


        } catch (SQLException e) {
            System.err.println("Ошибка при обращении к базе данных");
            e.printStackTrace();
        }
        return board;
    }

    @Override
    public List<Board> getAll() {
        List<Board> boards = new ArrayList<>();
        try (PreparedStatement ps = ConnectionPool.get().getConnection().prepareStatement("SELECT * FROM board")) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Board board = new Board();
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
                board.setDescription(rs.getString("description"));
                board.setArchived(rs.getBoolean("archived"));
                board.setVisibility(BoardVisibility.valueOf(rs.getString("visibility")));
                boards.add(board);
            }


        } catch (SQLException e) {
            System.err.println("Ошибка при обращении к базе данных");
            e.printStackTrace();
        }
        return boards;
    }

    @Override
    public List<Board> getParrent(UUID id) {
        List<Board> boards = new ArrayList<>();
        try (PreparedStatement ps = ConnectionPool.get().getConnection().prepareStatement("SELECT * FROM board WHERE workspace_id = \'"+id.toString()+"\';")) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Board board = new Board();
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
                board.setDescription(rs.getString("description"));
                board.setArchived(rs.getBoolean("archived"));
                board.setVisibility(BoardVisibility.valueOf(rs.getString("visibility")));
                boards.add(board);
            }


        } catch (SQLException e) {
            System.err.println("Ошибка при обращении к базе данных");
            e.printStackTrace();
        }
        return boards;
    }

}
