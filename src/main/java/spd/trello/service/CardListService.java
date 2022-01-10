package spd.trello.service;

import spd.trello.domain.CardList;
import spd.trello.repository.IRepository;


public class CardListService extends AbstractService<CardList>{

    public CardListService(IRepository<CardList> repository) {
        super(repository);
    }


}
