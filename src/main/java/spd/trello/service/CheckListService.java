package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domain.CheckList;
import spd.trello.repository.AbstractRepository;

@Service
public class CheckListService extends AbstractService<CheckList>{

    public CheckListService(AbstractRepository<CheckList> repository) {
        super(repository);
    }


}
