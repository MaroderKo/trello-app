package spd.trello.repository;

import spd.trello.db.ConnectionPool;
import spd.trello.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



public class UserRepository implements IRepository<User> {

    @Override
    public User create(UUID parent, User user) {
        try (
            Connection connection = ConnectionPool.get().getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO \"user\" (id, firstname, lastname, email) VALUES (?, ?, ?, ?);")) {


            ps.setObject(1, user.getId(), Types.OTHER);
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getEmail());

            ps.execute();


        } catch (SQLException e) {
            System.err.println("SQL EXCEPTION");
            e.printStackTrace();
        }
        return read(user.getId());
    }

    @Override
    public void update(User user) {
        try (
            Connection connection = ConnectionPool.get().getConnection();
            PreparedStatement ps = connection.prepareStatement("UPDATE \"user\" SET firstname = ?, lastname = ?, email = ? WHERE id = \'" + user.getId()+"\'")) {


            ps.setObject(1, user.getFirstName());
            ps.setObject(2, user.getLastName());
            ps.setString(3, user.getEmail());

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
            PreparedStatement ps = connection.prepareStatement("DELETE FROM \"user\" WHERE id = \'" + uuid + "\';")) {
            ps.execute();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    @Override
    public User read(UUID uuid) {
        User user = null;
        try (
            Connection connection = ConnectionPool.get().getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"user\" WHERE id = \'" + uuid.toString() + "\';")) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                user = new User();
                user.setId(UUID.fromString(rs.getString("id")));
                user.setEmail(rs.getString("email"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));

            }


        } catch (SQLException e) {
            System.err.println("Ошибка при обращении к базе данных");
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> Users = new ArrayList<>();
        try (
            Connection connection = ConnectionPool.get().getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"user\"")) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(UUID.fromString(rs.getString("id")));
                user.setEmail(rs.getString("email"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                Users.add(user);
            }


        } catch (SQLException e) {
            System.err.println("Ошибка при обращении к базе данных");
            e.printStackTrace();
        }
        return Users;
    }

    @Override
    public List<User> getParrent(UUID id) {
        return null;
    }
}
