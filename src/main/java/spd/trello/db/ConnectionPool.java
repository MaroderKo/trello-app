package spd.trello.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

import static spd.trello.Util.loadProperties;

public class ConnectionPool {
    private static DataSource source;

    public static DataSource get() {
        if (source == null) {
            try {
                source = createDataSource();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return source;

    }


    private static DataSource createDataSource() throws IOException {
        Properties properties = loadProperties();
        HikariConfig cfg = new HikariConfig();
            cfg.setJdbcUrl(properties.getProperty("jdbc.url"));
            cfg.setUsername(properties.getProperty("jdbc.username"));
            cfg.setPassword(properties.getProperty("jdbc.password"));
        cfg.setDriverClassName("org.postgresql.Driver");


        int maxPoolSize = Integer.parseInt(properties.getProperty("jdbc.pool.maxConnection"));
        cfg.setMaximumPoolSize(maxPoolSize);

        return new HikariDataSource(cfg);
    }

    public static void setSource(DataSource source) {
        ConnectionPool.source = source;
    }
}
