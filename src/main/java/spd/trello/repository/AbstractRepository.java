package spd.trello.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import spd.trello.db.ConnectionPool;
import spd.trello.domain.Domain;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Component
public abstract class AbstractRepository<T extends Domain> {

    protected static final String SQL_EXCEPTION_MESSAGE = "Error occurred while connecting to database";

    JdbcTemplate jdbcTemplate = new JdbcTemplate(ConnectionPool.get());

    public abstract T create(T t);

    public abstract void delete(UUID id);

    public abstract T update(T t);

    public abstract T getById(UUID id);

    public abstract List<T> getAll();

    public abstract List<T> getParent(UUID id);

    protected void sqlExceptionMapper(SQLException e)
    {
        System.err.println(SQL_EXCEPTION_MESSAGE);
        throw new IllegalStateException(e.getCause());
    }

}
