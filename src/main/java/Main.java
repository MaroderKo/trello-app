import org.flywaydb.core.Flyway;
import spd.trello.db.ConnectionPool;
import spd.trello.domain.Workspace;
import spd.trello.repository.WorkspaceRepository;
import spd.trello.service.WorkspaceService;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
import java.util.UUID;

import static spd.trello.Util.loadProperties;

public class Main {
    public static void main(String[] args) {
        Flyway flyway = createFlyway(ConnectionPool.get());
        flyway.migrate();

        WorkspaceService service = new WorkspaceService();
        Workspace workspace = service.create();
        Scanner sc = new Scanner(System.in);
        UUID id = workspace.getId();
        Workspace workspace1 = service.read(id);
        System.out.println(workspace.equals(workspace1));


    }


    private static Flyway createFlyway(DataSource dataSource)
    {
        return Flyway.configure()
                .dataSource(dataSource)
                .load();
    }
    
}
