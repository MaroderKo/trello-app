package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domain.CardList;
import spd.trello.repository.AbstractRepository;

@Service
public class CardListService extends AbstractService<CardList>{

    public CardListService(AbstractRepository<CardList> repository) {
        super(repository);
    }


}
