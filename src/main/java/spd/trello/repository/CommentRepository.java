package spd.trello.repository;

import org.springframework.stereotype.Repository;
import spd.trello.domain.Comment;

@Repository
public interface CommentRepository extends AbstractParentBasedRepository<Comment>{
//    @Query("select Comment from Comment where parentId = ?1")
//    List<Comment> findByParentId(UUID id);
}
