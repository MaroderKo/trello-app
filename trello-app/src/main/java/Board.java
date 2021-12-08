import java.util.List;

public class Board {
    private String name;
    private String description;
    private List<CardList> cardLists;
    private List<Member> members;
    private BoardVisibilityEnum visibility;
    private boolean favoriteStatus;
    private boolean isArchived;

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

    public BoardVisibilityEnum getVisibility() {
        return visibility;
    }

    public void setVisibility(BoardVisibilityEnum visibility) {
        this.visibility = visibility;
    }

    public boolean isFavoriteStatus() {
        return favoriteStatus;
    }

    public void setFavoriteStatus(boolean favoriteStatus) {
        this.favoriteStatus = favoriteStatus;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }

    public void addCard(CardList cardList) {
        cardLists.add(cardList);
    }

    public void removeCardList(int id) {
        cardLists.remove(id);
    }

    public CardList getCardList(int id) {
        return cardLists.get(id);
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
