package spd.trello.service;

import spd.trello.domain.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class CardService extends AbstractService<Card>{
    static List<Card> storage = new ArrayList<>();
    @Override
    public Card create() {
        Card card = new Card();
        Scanner sc = new Scanner(System.in);
        System.out.print("Input name: ");
        String name = sc.nextLine();
        card.setName(name);
        storage.add(card);
        return card;
    }

    @Override
    public void update(UUID index, Card card) {
        throw new UnsupportedOperationException();
    }

}
