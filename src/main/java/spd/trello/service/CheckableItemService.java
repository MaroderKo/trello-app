package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domain.CheckableItem;
import spd.trello.repository.CheckableItemRepository;

@Service
public class CheckableItemService extends AbstractParentBasedService<CheckableItem, CheckableItemRepository>{

    public CheckableItemService(CheckableItemRepository repository) {
        super(repository);
    }


}
