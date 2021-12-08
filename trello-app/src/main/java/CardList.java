import java.util.List;

public class CardList {
    private String name;
    private List<Card> cards;
    private boolean isArchived;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }
}
