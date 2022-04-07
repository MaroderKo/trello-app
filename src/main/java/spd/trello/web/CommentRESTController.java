package spd.trello.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spd.trello.domain.Comment;
import spd.trello.repository.CommentRepository;
import spd.trello.service.AbstractParentBasedService;
import spd.trello.service.AbstractService;

@RestController
@RequestMapping("/api/comments")
public class CommentRESTController extends ParentBasedController<Comment, CommentRepository>{
    public CommentRESTController(AbstractService<Comment, CommentRepository> service, AbstractParentBasedService<Comment, CommentRepository> service1) {
        super(service, service1);
    }
}
