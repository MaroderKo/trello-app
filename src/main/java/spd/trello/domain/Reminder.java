package spd.trello.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Reminder extends Resource{
    private UUID cardId;
    private LocalDateTime start;
    private LocalDateTime end;
    private String remindOn;
    private Boolean active;

}
