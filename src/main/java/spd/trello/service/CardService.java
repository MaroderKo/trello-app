package spd.trello.service;

import spd.trello.domain.Card;
import spd.trello.repository.IRepository;

public class CardService extends AbstractService<Card>{
    public CardService(IRepository<Card> repository) {
        super(repository);
    }
}
