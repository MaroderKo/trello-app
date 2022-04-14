package spd.trello.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Reminder extends Resource{
    @Column(name="parent_id")
    @NotNull
    private UUID parentId;
    @Column
    @FutureOrPresent
    private LocalDateTime start;
    @Column
    @FutureOrPresent
    private LocalDateTime ends;
    @Column
    @FutureOrPresent
    private LocalDateTime remindOn;
    @Column
    private Boolean active = false;

    public Reminder(UUID parentId) {
        this.parentId = parentId;
    }

}
