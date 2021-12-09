import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Reminder {
    private LocalDateTime start;
    private LocalDateTime end;
    private String remindOn;
    private boolean active;

}
