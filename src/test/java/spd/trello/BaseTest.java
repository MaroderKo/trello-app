package spd.trello;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import spd.trello.db.ConnectionPool;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

public abstract class BaseTest {

	protected static HikariDataSource dataSource;
	@Autowired
	static ConnectionPool connectionPool;

	@BeforeAll
	public static void init() {
		HikariConfig cfg = new HikariConfig();
		cfg.setPassword("sa");
		cfg.setJdbcUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;MODE=PostgreSQL");
		cfg.setUsername("sa");
		cfg.setDriverClassName("org.h2.Driver");
		dataSource = new HikariDataSource(cfg);
		connectionPool.setSource(dataSource);
		Flyway flyway = Flyway.configure()
				.locations("filesystem:src/test")
				.dataSource(connectionPool.getSource())
				.load();
		flyway.migrate();
	}

}