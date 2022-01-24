package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domain.Board;
import spd.trello.repository.AbstractRepository;

@Service
public class BoardService extends AbstractService<Board>{

    public BoardService(AbstractRepository<Board> repository) {
        super(repository);
    }



}
