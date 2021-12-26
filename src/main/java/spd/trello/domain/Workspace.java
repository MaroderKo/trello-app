package spd.trello.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Workspace extends Resource{
    private String name = "";
    private String description = "";
    private WorkspaceVisibility visibility = WorkspaceVisibility.PRIVATE;
    private List<Member> members = new ArrayList<>();
    private List<Board> boards = new ArrayList<>();

}
