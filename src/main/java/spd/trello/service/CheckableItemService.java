package spd.trello.service;

import spd.trello.domain.CheckableItem;
import spd.trello.repository.AbstractRepository;


public class CheckableItemService extends AbstractService<CheckableItem>{

    public CheckableItemService(AbstractRepository<CheckableItem> repository) {
        super(repository);
    }


}
