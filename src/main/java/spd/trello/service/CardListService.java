package spd.trello.service;

import spd.trello.domain.CardList;
import spd.trello.repository.AbstractRepository;


public class CardListService extends AbstractService<CardList>{

    public CardListService(AbstractRepository<CardList> repository) {
        super(repository);
    }


}
