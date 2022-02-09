package spd.trello.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.UUID;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Card extends Resource implements ParentBased{
    @Column
    private String name;
    @Column(name = "cardlist_id")
    private UUID parentId;
    @Column
    private String description;
    @Column
    private Boolean archived;

}
