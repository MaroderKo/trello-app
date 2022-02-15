package spd.trello;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import spd.trello.db.ConnectionPool;
import spd.trello.repository.BoardRepository;
import spd.trello.repository.WorkspaceRepository;
import spd.trello.service.BoardService;
import spd.trello.service.WorkspaceService;

import javax.transaction.Transactional;

@SpringBootTest(classes = {
		WorkspaceService.class,
		WorkspaceRepository.class,
		BoardRepository.class,
		BoardService.class
})
@EnableAutoConfiguration
@Transactional()
public abstract class BaseTest {

	protected static HikariDataSource dataSource;
	static ConnectionPool connectionPool;

	@BeforeAll
	public static void init() {
		HikariConfig cfg = new HikariConfig();
		cfg.setPassword("sa");
		cfg.setJdbcUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;MODE=PostgreSQL");
		cfg.setUsername("sa");
		cfg.setDriverClassName("org.h2.Driver");
		dataSource = new HikariDataSource(cfg);
		connectionPool = new ConnectionPool(dataSource);
		Flyway flyway = Flyway.configure()
				.locations("filesystem:src/test")
				.dataSource(connectionPool.getSource())
				.load();
		flyway.migrate();
	}

}
