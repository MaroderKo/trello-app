package spd.trello.service;

import spd.trello.domain.Board;
import spd.trello.repository.IRepository;

import java.util.Scanner;
import java.util.UUID;

public class BoardService extends AbstractService<Board>{

    public BoardService(IRepository<Board> repository) {
        super(repository);
    }



}
