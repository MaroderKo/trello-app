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


	@BeforeAll
	public static void init() {
		ConnectionPool.get(1);
		Flyway flyway = Flyway.configure()
				//.locations("classpath:migrations")
				.dataSource(ConnectionPool.get())
				.load();
		flyway.migrate();
	}

	@AfterAll
	public static void clearAll() throws SQLException {
		ConnectionPool.get(1).getConnection().prepareStatement(
				"DROP SCHEMA public CASCADE; " +
						"CREATE SCHEMA public; " +
						"GRANT ALL ON SCHEMA public TO test; " +
						"GRANT ALL ON SCHEMA public TO public;")
				.execute();
		System.out.println("Cleared!");
	}
}
