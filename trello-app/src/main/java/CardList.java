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


    public void addCard(Card card) {
        cards.add(card);
    }

    public void removeCard(int id) {
        cards.remove(id);
    }

    public Card getCard(int id) {
        return cards.get(id);
    }

}
