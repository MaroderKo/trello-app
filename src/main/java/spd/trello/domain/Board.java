package spd.trello.domain;

import lombok.Data;

import java.util.List;
@Data
public class Board {
    private String name;
    private String description;
    private List<CardList> cardLists;
    private List<Member> members;
    private BoardVisibilityEnum visibility;
    private Boolean favoriteStatus;
    private Boolean archived;


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
