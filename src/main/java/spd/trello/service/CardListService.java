package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domain.CardList;
import spd.trello.repository.CardListRepository;

@Service
public class CardListService extends AbstractParentBasedService<CardList, CardListRepository>{

    public CardListService(CardListRepository repository) {
        super(repository);
    }

}
