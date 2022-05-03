package spd.trello.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spd.trello.domain.Board;
import spd.trello.repository.BoardRepository;
import spd.trello.service.AbstractService;
import spd.trello.service.BoardService;
import spd.trello.service.MemberService;
import spd.trello.web.ParentBasedController;

@RestController
@RequestMapping("/api/boards")
public class BoardRESTController extends ParentBasedController<Board, BoardRepository> {

    @Autowired
    MemberService memberService;

    public BoardRESTController(AbstractService<Board, BoardRepository> service, BoardService service1) {
        super(service, service1);
    }

    @Override
    public ResponseEntity<Board> Create(@RequestBody Board board) {
        board.getMembers().add(memberService.ownerMember());
        return super.Create(board);
    }
}
