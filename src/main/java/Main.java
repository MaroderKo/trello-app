import org.flywaydb.core.Flyway;
import spd.trello.domain.*;
import spd.trello.service.CardService;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws IOException {
        Properties properties = loadProperties();
        System.out.println(properties);
        System.out.println("username="+properties.getProperty("jdbc.username"));
        System.out.println("url="+properties.getProperty("jdbc.url"));

    }

    private static Flyway createFlyway(DataSource dataSource)
    {
        return Flyway.configure()
                .dataSource(dataSource)
                .load();
    }

    private static Properties loadProperties() throws IOException {
        InputStream in = Main.class.getClassLoader()
                .getResourceAsStream("application.properties");
        Properties properties = new Properties();
        properties.load(in);

        return properties;
    }
}
