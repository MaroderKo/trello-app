package spd.trello.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;
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
    @OneToOne(targetEntity = Reminder.class)
    Reminder reminder;
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name = "checklist", joinColumns = @JoinColumn(name = "card_id"))
    List<UUID> checkList;
}
