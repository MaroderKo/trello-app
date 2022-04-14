package spd.trello.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Attachment extends Resource implements ParentBased{
    @Column(name="parent_id")
    @NotNull
    private UUID parentId;
    @Column
    @NotBlank
    private String name;
    @Column
    private String link = "";
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "data_id")
    private AttachmentData data = new AttachmentData();


    public Attachment(UUID parentId) {
        this.parentId = parentId;
    }
}
