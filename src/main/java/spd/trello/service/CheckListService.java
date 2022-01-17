package spd.trello.service;

import spd.trello.domain.CheckList;
import spd.trello.repository.AbstractRepository;


public class CheckListService extends AbstractService<CheckList>{

    public CheckListService(AbstractRepository<CheckList> repository) {
        super(repository);
    }


}
