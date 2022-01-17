package spd.trello.service;

import spd.trello.domain.Comment;
import spd.trello.repository.AbstractRepository;

public class CommentService extends AbstractService<Comment>{
    public CommentService(AbstractRepository<Comment> repository) {
        super(repository);
    }
}
