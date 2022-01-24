package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domain.Card;
import spd.trello.repository.AbstractRepository;

@Service
public class CardService extends AbstractService<Card>{
    public CardService(AbstractRepository<Card> repository) {
        super(repository);
    }
}
