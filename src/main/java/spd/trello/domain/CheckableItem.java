package spd.trello.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class CheckableItem extends Domain implements ParentBased{
    @Column(name="parent_id")
    @NotNull
    private UUID parentId;
    @Column
    private String name;
    @Column
    private Boolean checked;

}
