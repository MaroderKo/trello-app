package spd.trello.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spd.trello.domain.Board;
import spd.trello.service.AbstractService;
@RestController
@RequestMapping("/api/boards")
public class BoardRESTController extends ChildController<Board>{
    public BoardRESTController(AbstractService<Board> service) {
        super(service);
    }
}
