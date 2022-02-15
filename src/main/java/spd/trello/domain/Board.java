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
public class Board extends Resource implements ParentBased{
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private UUID parentId;
    @Column
    @Enumerated(EnumType.STRING)
    private BoardVisibility visibility;
    @Column
    private Boolean archived;
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name = "cardlist", joinColumns = @JoinColumn(name = "board_id"))
    @Column(name = "id")
    List<UUID> cardlist;

}
