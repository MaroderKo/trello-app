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

    public void addBoard(Board board) {
        boards.add(board);
    }

    public void removeBoard(int id) {
        boards.remove(id);
    }

    public Board getBoard(int id) {
        return boards.get(id);
    }

    public void addMember(Member member) {
        members.add(member);
    }

    public void removeMember(int id) {
        members.remove(id);
    }

    public Member getMember(int id) {
        return members.get(id);
    }
}
