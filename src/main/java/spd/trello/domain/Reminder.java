package spd.trello.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Reminder extends Resource{
    @Column(name="parent_id")
    private UUID parentId;
    @Column
    private LocalDateTime start;
    @Column
    private LocalDateTime ends;
    @Column
    private LocalDateTime remindOn;
    @Column
    private Boolean active = false;

    public Reminder(UUID parentId) {
        this.parentId = parentId;
    }

}
