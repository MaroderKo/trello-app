package spd.trello.service;

import spd.trello.domain.Board;
import spd.trello.repository.AbstractRepository;

public class BoardService extends AbstractService<Board>{

    public BoardService(AbstractRepository<Board> repository) {
        super(repository);
    }



}
