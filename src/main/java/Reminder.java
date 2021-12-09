import java.time.LocalDateTime;

public class Reminder {
    private LocalDateTime start;
    private LocalDateTime end;
    private String remindOn;
    private boolean isActive;

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public String getRemindOn() {
        return remindOn;
    }

    public void setRemindOn(String remindOn) {
        this.remindOn = remindOn;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
