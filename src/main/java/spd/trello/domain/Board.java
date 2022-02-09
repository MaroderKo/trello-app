package spd.trello.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
    @Column(name = "workspace_id")
    private UUID parentId;
    @Column
    @Enumerated(EnumType.STRING)
    private BoardVisibility visibility;
    @Column
    private Boolean archived;

}
