package spd.trello.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Workspace extends Resource{
    private String name = "";
    private String description = "";
    private WorkspaceVisibility visibility = WorkspaceVisibility.PRIVATE;

}
