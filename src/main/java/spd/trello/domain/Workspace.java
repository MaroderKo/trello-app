package spd.trello.domain;

import lombok.Data;

import java.util.List;
@Data
public class Workspace {
    private String name;
    private String description;
    private WorkspaceVisibility visibility;
    private List<Member> members;
    private List<Board> boards;

}
