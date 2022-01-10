package spd.trello.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Comment extends Resource{
    private String author;
    private String text;
    private LocalDateTime date;
    private List<Attachment> attachments;
    private Boolean archived;



}

