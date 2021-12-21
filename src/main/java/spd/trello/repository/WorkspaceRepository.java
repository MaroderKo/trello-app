package spd.trello.repository;

import spd.trello.db.ConnectionPool;
import spd.trello.domain.Workspace;

import java.sql.*;
import java.util.UUID;

import static spd.trello.repository.RepositoryUtil.toLocalDateTime;


public class WorkspaceRepository {

    public static void create(Workspace workspace) {
        try (PreparedStatement ps = ConnectionPool.get().getConnection().prepareStatement("INSERT INTO workspace (id, updated_by, created_by, created_date, updated_date, name, description) VALUES (?, ?, ?, ?, ?, ?, ?);")) {


            ps.setObject(1, workspace.getId(), Types.OTHER);
            ps.setObject(2, workspace.getUpdatedBy() == null ? null : workspace.getUpdatedBy());
            ps.setString(3, workspace.getCreatedBy());
            ps.setObject(4, Timestamp.valueOf(workspace.getCreatedDate()), Types.TIMESTAMP);
            ps.setObject(5, workspace.getUpdatedDate() == null ? null : Timestamp.valueOf(workspace.getUpdatedDate()), Types.TIMESTAMP);
            ps.setString(6, workspace.getName());
            ps.setString(7, workspace.getDescription());

            ps.execute();
            //System.out.println("Workspace save status : " + ps.getResultSet().first());


        } catch (SQLException e) {
            System.err.println("SQL EXCEPTION");
            e.printStackTrace();
        }
    }

    public static void update(UUID uuid, Workspace workspace) {
        try (PreparedStatement ps = ConnectionPool.get().getConnection().prepareStatement("DELETE FROM workspace WHERE id = ${uuid};INSERT INTO workspace (id, updated_by, created_by, created_date, updated_date, name, description) VALUES (?, ?, ?, ?, ?, ?, ?);")) {


            ps.setObject(1, workspace.getId(), Types.OTHER);
            ps.setObject(2, workspace.getUpdatedBy() == null ? null : workspace.getUpdatedBy());
            ps.setString(3, workspace.getCreatedBy());
            ps.setObject(4, Timestamp.valueOf(workspace.getCreatedDate()), Types.TIMESTAMP);
            ps.setObject(5, workspace.getUpdatedDate() == null ? null : Timestamp.valueOf(workspace.getUpdatedDate()).toString());
            ps.setString(6, workspace.getName());
            ps.setString(7, workspace.getDescription());

            ps.execute();
            System.out.println("Workspace save status : " + ps.getResultSet().first());


        } catch (SQLException e) {
            System.err.println("SQL EXCEPTION");
            e.printStackTrace();
        }
    }

    public static void delete(UUID uuid) {
        try (PreparedStatement ps = ConnectionPool.get().getConnection().prepareStatement("DELETE * FROM workspace WHERE id = " + uuid)) {
            ps.execute();
        } catch (SQLException e)
        {
            System.err.println(e.getMessage());
        }

    }


    public static Workspace read(UUID uuid) {
        Workspace workspace = null;
        System.out.println(uuid);
        try (PreparedStatement ps = ConnectionPool.get().getConnection().prepareStatement("SELECT * FROM workspace WHERE id = \'"+uuid.toString()+"\';")) {

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
//                workspace.setName(rs.getString("name"));
//                workspace.setName(rs.getString("name"));

            }


        } catch (SQLException e) {
            System.err.println("Ошибка при обращении к базе данных");
            e.printStackTrace();
        }
        return workspace;
    }

}
