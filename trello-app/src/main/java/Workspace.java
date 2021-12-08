import java.util.List;

public class Workspace {
    private String name;
    private String description;
    private WorkspaceVisibility visibility;
    private List<Member> members;
    private List<Board> boards;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public WorkspaceVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(WorkspaceVisibility visibility) {
        this.visibility = visibility;
    }
}
