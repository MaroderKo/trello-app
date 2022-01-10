package spd.trello.service;

import spd.trello.domain.CardList;
import spd.trello.domain.CheckList;
import spd.trello.repository.IRepository;


public class CheckListService extends AbstractService<CheckList>{

    public CheckListService(IRepository<CheckList> repository) {
        super(repository);
    }


}
