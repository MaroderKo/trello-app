package spd.trello.domain;

import lombok.Data;

import java.util.List;
@Data
public class CardList {
    private String name;
    private List<Card> cards;
    private Boolean archived;


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
