package spd.trello.repository;

import spd.trello.db.ConnectionPool;
import spd.trello.domain.Workspace;
import spd.trello.domain.WorkspaceVisibility;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static spd.trello.repository.RepositoryUtil.toLocalDateTime;


public class WorkspaceRepository implements IRepository<Workspace> {

    @Override
    public Workspace create(UUID parent, Workspace workspace) {
        try (
            Connection connection = ConnectionPool.get().getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO workspace (id, updated_by, created_by, created_date, updated_date, name, description, visibility) VALUES (?, ?, ?, ?, ?, ?, ?, ?);")) {


            ps.setObject(1, workspace.getId(), Types.OTHER);
            ps.setObject(2, workspace.getUpdatedBy() == null ? null : workspace.getUpdatedBy());
            ps.setString(3, workspace.getCreatedBy());
            ps.setObject(4, Timestamp.valueOf(workspace.getCreatedDate()), Types.TIMESTAMP);
            ps.setObject(5, workspace.getUpdatedDate() == null ? null : Timestamp.valueOf(workspace.getUpdatedDate()), Types.TIMESTAMP);
            ps.setString(6, workspace.getName());
            ps.setString(7, workspace.getDescription());
            ps.setString(8, workspace.getVisibility().name());

            ps.execute();


        } catch (SQLException e) {
            System.err.println("Error occurred while connecting to database");
            e.printStackTrace();
        }
        return getById(workspace.getId());
    }

    @Override
    public Workspace update(Workspace workspace) {
        workspace.setUpdatedDate(LocalDateTime.now());
        try (
            Connection connection = ConnectionPool.get().getConnection();
            PreparedStatement ps = connection.prepareStatement("UPDATE workspace SET updated_by = ?, updated_date = ?, name = ?, description = ?, visibility = ? WHERE id = '" + workspace.getId()+"'")) {


            ps.setObject(1, workspace.getUpdatedBy() == null ? null : workspace.getUpdatedBy());
            ps.setObject(2, workspace.getUpdatedDate() == null ? null : Timestamp.valueOf(workspace.getUpdatedDate()), Types.TIMESTAMP);
            ps.setString(3, workspace.getName());
            ps.setString(4, workspace.getDescription());
            ps.setString(5, workspace.getVisibility().name());

            ps.execute();

        } catch (SQLException e) {
            System.err.println("Error occurred while connecting to database");
            e.printStackTrace();
        }

        return getById(workspace.getId());
    }

    @Override
    public void delete(UUID uuid) {
        try (
            Connection connection = ConnectionPool.get().getConnection();
            PreparedStatement ps = connection.prepareStatement("DELETE FROM workspace WHERE id = '" + uuid + "';")) {
            ps.execute();
        } catch (SQLException e) {
            System.err.println("Error occurred while connecting to database");
            e.printStackTrace();
        }

    }

    @Override
    public Workspace getById(UUID uuid) {
        Workspace workspace = null;
        try (
            Connection connection = ConnectionPool.get().getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM workspace WHERE id = '" + uuid.toString() + "';")) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                workspace = new Workspace();
                workspace.setId(UUID.fromString(rs.getString("id")));
                if (rs.getString("updated_by") != null) {
                    workspace.setUpdatedBy(rs.getString("updated_by"));
                }
                workspace.setCreatedBy(rs.getString("created_by"));
                workspace.setCreatedDate(toLocalDateTime(rs.getString("created_date")));
                if (rs.getString("updated_date") != null) {
                    workspace.setUpdatedDate(toLocalDateTime(rs.getString("updated_date")));
                }
                workspace.setName(rs.getString("name"));
                workspace.setDescription(rs.getString("description"));
                workspace.setVisibility(WorkspaceVisibility.valueOf(rs.getString("visibility")));
            }


        } catch (SQLException e) {
            System.err.println("Error occurred while connecting to database");
            e.printStackTrace();
        }
        return workspace;
    }

    @Override
    public List<Workspace> getAll() {
        List<Workspace> workspaces = new ArrayList<>();
        try (
            Connection connection = ConnectionPool.get().getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM workspace")) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Workspace workspace = new Workspace();
                workspace.setId(UUID.fromString(rs.getString("id")));
                if (rs.getString("updated_by") != null) {
                    workspace.setUpdatedBy(rs.getString("updated_by"));
                }
                workspace.setCreatedBy(rs.getString("created_by"));
                workspace.setCreatedDate(toLocalDateTime(rs.getString("created_date")));
                if (rs.getString("updated_date") != null) {
                    workspace.setUpdatedDate(toLocalDateTime(rs.getString("updated_date")));
                }
                workspace.setName(rs.getString("name"));
                workspace.setDescription(rs.getString("description"));
                workspace.setVisibility(WorkspaceVisibility.valueOf(rs.getString("visibility")));
                workspaces.add(workspace);
            }


        } catch (SQLException e) {
            System.err.println("Error occurred while connecting to database");
            e.printStackTrace();
        }
        return workspaces;
    }

    @Override
    public List<Workspace> getParrent(UUID id) {
        return null;
    }
}
