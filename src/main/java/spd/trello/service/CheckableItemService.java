package spd.trello.service;

import spd.trello.domain.CardList;
import spd.trello.domain.CheckableItem;
import spd.trello.repository.IRepository;


public class CheckableItemService extends AbstractService<CheckableItem>{

    public CheckableItemService(IRepository<CheckableItem> repository) {
        super(repository);
    }


}
