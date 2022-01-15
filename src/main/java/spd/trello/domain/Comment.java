package spd.trello.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Comment extends Resource{
    private String author;
    private UUID cardId;
    private String text;
    private LocalDateTime date;
    private Boolean archived;



}

