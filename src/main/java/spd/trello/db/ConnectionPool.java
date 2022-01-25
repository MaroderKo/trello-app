package spd.trello.db;

import org.springframework.stereotype.Component;
import javax.sql.DataSource;


@Component
public class ConnectionPool {
    public ConnectionPool(DataSource source) {
        this.source = source;
    }

    DataSource source;

    public void setSource(DataSource source) {
        this.source = source;
    }

    public DataSource getSource() {
        return source;
    }
}
