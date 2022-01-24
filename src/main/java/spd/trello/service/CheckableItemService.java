package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domain.CheckableItem;
import spd.trello.repository.AbstractRepository;

@Service
public class CheckableItemService extends AbstractService<CheckableItem>{

    public CheckableItemService(AbstractRepository<CheckableItem> repository) {
        super(repository);
    }


}
