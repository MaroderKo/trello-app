package spd.trello.repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class RepositoryUtil {
    public static LocalDateTime toLocalDateTime(String s)
    {
        return Timestamp.valueOf(s).toLocalDateTime();
    }
}
