import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import spd.trello.db.ConnectionPool;
import spd.trello.domain.*;
import spd.trello.service.CardService;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static spd.trello.Util.loadProperties;

public class Main {
    public static void main(String[] args) throws IOException {
        Properties properties = loadProperties();
        System.out.println(properties);
        System.out.println("username="+properties.getProperty("jdbc.username"));
        System.out.println("url="+properties.getProperty("jdbc.url"));

        Flyway flyway = createFlyway(ConnectionPool.get());
        //flyway.clean();
        flyway.migrate();


    }


    private static Flyway createFlyway(DataSource dataSource)
    {
        return Flyway.configure()
                .dataSource(dataSource)
                .load();
    }
    
}
