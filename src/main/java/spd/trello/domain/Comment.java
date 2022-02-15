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
public class Comment extends Resource implements ParentBased{
    @Column
    private String author;
    @Column(name="parent_id")
    private UUID parentId;
    @Column
    private String text;
    @Column
    private LocalDateTime date;
    @Column
    private Boolean archived;



}

