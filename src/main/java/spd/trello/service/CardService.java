package spd.trello.service;

import spd.trello.domain.Card;
import spd.trello.repository.IRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class CardService extends AbstractService<Card>{
    static List<Card> storage = new ArrayList<>();

    public CardService(IRepository<Card> repository) {
        super(repository);
    }


}
