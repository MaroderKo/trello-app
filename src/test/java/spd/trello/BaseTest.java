package spd.trello;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import spd.trello.service.*;

import javax.transaction.Transactional;

@SpringBootTest(classes = {
        BoardService.class,
        CardListService.class,
        CardService.class,
        CommentService.class,
        MemberService.class,
        UserService.class,
        WorkspaceService.class

})
@EnableAutoConfiguration
//@Transactional()
public abstract class BaseTest {
//
//    protected static HikariDataSource dataSource;
//    static ConnectionPool connectionPool;
//
//    @BeforeAll
//    public static void init() {
//        HikariConfig cfg = new HikariConfig();
//        cfg.setPassword("sa");
//        cfg.setJdbcUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;MODE=PostgreSQL");
//        cfg.setUsername("sa");
//        cfg.setDriverClassName("org.h2.Driver");
//        dataSource = new HikariDataSource(cfg);
//        connectionPool = new ConnectionPool(dataSource);
//        Flyway flyway = Flyway.configure()
//                .locations("filesystem:src/test")
//                .dataSource(connectionPool.getSource())
//                .load();
//        flyway.migrate();
//    }

}
