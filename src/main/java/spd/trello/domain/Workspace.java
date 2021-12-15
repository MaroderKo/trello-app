package spd.trello.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.List;
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Workspace extends Resource{
    private String name;
    private String description;
    private WorkspaceVisibility visibility;
    private List<Member> members;
    private List<Board> boards;

}
