package spd.trello;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import spd.trello.db.ConnectionPool;

import java.sql.SQLException;

public abstract class BaseTest {

	protected static HikariDataSource dataSource;

	@BeforeAll
	public static void init() {
		HikariConfig cfg = new HikariConfig();
		cfg.setPassword("sa");
		cfg.setJdbcUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;MODE=PostgreSQL");
		cfg.setUsername("sa");
		cfg.setDriverClassName("org.h2.Driver");
		dataSource = new HikariDataSource(cfg);
		ConnectionPool.setSource(dataSource);
		Flyway flyway = Flyway.configure()
				.dataSource(ConnectionPool.get())
				.load();
		flyway.migrate();
	}

	@AfterAll
	public static void clearAll() throws SQLException {
		ConnectionPool.get().getConnection().prepareStatement(
				"DROP ALL OBJECTS")
				.execute();
		System.out.println("Cleared!");
	}
}
