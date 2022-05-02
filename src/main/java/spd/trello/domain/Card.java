package spd.trello.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Card extends Resource implements ParentBased{
    @Column
    @NotBlank
    private String name;
    @Column(name="parent_id")
    @NotNull
    private UUID parentId;
    @Column
    private String description;
    @Column
    private Boolean archived;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="reminder_id")
    private Reminder reminder;
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name = "checklist", joinColumns = @JoinColumn(name = "parent_id"))
    @Column(name = "id")
    List<UUID> checkList = new ArrayList<>();
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name = "comment", joinColumns = @JoinColumn(name = "parent_id"))
    @Column(name = "id")
    List<UUID> comments = new ArrayList<>();
}
