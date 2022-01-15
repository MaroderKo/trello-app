package spd.trello.service;

import spd.trello.domain.Card;
import spd.trello.repository.AbstractRepository;

public class CardService extends AbstractService<Card>{
    public CardService(AbstractRepository<Card> repository) {
        super(repository);
    }
}
