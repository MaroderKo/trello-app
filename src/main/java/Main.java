import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spd.trello.db.ConnectionPool;
import spd.trello.domain.Workspace;
import spd.trello.domain.WorkspaceVisibility;
import spd.trello.service.WorkspaceService;

import javax.sql.DataSource;

public class Main {
    public static void main(String[] args) {
        Flyway flyway = createFlyway(ConnectionPool.get());
        flyway.migrate();
        //flyway.repair();
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConnectionPool.class);

        Workspace workspace = new Workspace();
        workspace.setName("test");
        workspace.setVisibility(WorkspaceVisibility.PUBLIC);
        workspace.setDescription("12345");
        WorkspaceService service = context.getBean(WorkspaceService.class);
        service.create(workspace);
    }
//TODO: change parent id interface with method MOVE

    private static Flyway createFlyway(DataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .load();
    }

}
