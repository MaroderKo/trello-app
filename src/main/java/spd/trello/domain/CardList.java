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
@Entity(name = "cardlist")
public class CardList extends Resource implements ParentBased{
    @Column
    private String name;
    @Column(name = "board_id")
    private UUID parentId;
    @Column
    private Boolean archived;
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name = "card", joinColumns = @JoinColumn(name = "cardlist_id"))
    List<UUID> cards;


}
