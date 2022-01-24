package spd.trello.repository;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class RepositoryUtil {
    public static LocalDateTime toLocalDateTime(String s)
    {
        return Timestamp.valueOf(s).toLocalDateTime();
    }
}
