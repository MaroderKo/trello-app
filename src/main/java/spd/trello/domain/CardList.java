package spd.trello.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "cardlist")
public class CardList extends Resource implements ParentBased{
    @Column
    private String name;
    @Column(name="parent_id")
    private UUID parentId;
    @Column
    private Boolean archived;
    @ElementCollection(fetch = FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name = "card", joinColumns = @JoinColumn(name = "parent_id"))
    @Column(name = "id")
    List<UUID> cards = new ArrayList<>();


}
