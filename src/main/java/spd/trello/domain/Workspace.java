package spd.trello.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Workspace extends Resource{
    @Column
    @NotBlank
    private String name = "";
    @Column
    private String description = "";
    @Column
    @Enumerated(EnumType.STRING)
    private WorkspaceVisibility visibility = WorkspaceVisibility.PRIVATE;
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name = "board", joinColumns = @JoinColumn(name = "parent_id"))
    @Column(name = "id")
    List<UUID> boards = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "workspace_member",
            joinColumns = @JoinColumn(name = "workspace_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id"))
    List<Member> members = new ArrayList<>();
}
