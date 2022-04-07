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
@Entity
public class Comment extends Resource implements ParentBased{
    @Column
    private String author;
    @Column(name="parent_id")
    private UUID parentId;
    @Column
    private String text;
    @Column
    private Boolean archived;

    @EqualsAndHashCode.Exclude
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name = "attachment", joinColumns = @JoinColumn(name = "parent_id"))
    @Column(name = "id")
    List<Attachment> attachments = new ArrayList<>();

}

