package spd.trello.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spd.trello.domain.Board;
import spd.trello.repository.BoardRepository;
import spd.trello.service.AbstractService;
import spd.trello.service.BoardService;

@RestController
@RequestMapping("/api/boards")
public class BoardRESTController extends ParentBasedController<Board, BoardRepository> {
    public BoardRESTController(AbstractService<Board, BoardRepository> service, BoardService service1) {
        super(service, service1);
    }
}
