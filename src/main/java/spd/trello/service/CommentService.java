package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domain.Comment;
import spd.trello.repository.AbstractRepository;

@Service
public class CommentService extends AbstractService<Comment>{
    public CommentService(AbstractRepository<Comment> repository) {
        super(repository);
    }
}
