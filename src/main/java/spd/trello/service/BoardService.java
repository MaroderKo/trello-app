package spd.trello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spd.trello.domain.Board;
import spd.trello.repository.BoardRepository;


@Service
public class BoardService extends AbstractParentBasedService<Board, BoardRepository>{

    @Autowired
    MemberService memberService;

    public BoardService(BoardRepository repository) {
        super(repository);
    }

    @Override
    public Board create(Board board) {
        board.getMembers().add(memberService.ownerMember());
        return super.create(board);
    }
}
