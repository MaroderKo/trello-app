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
public class CheckableItem extends Domain implements ParentBased{
    @Column(name = "checklist_id")
    private UUID parentId;
    @Column
    private String name;
    @Column
    private Boolean checked;

}
