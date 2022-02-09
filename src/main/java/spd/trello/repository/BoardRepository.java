package spd.trello.repository;

import org.springframework.stereotype.Repository;
import spd.trello.domain.Board;



@Repository
public interface BoardRepository extends AbstractParentBasedRepository<Board>{
//    @Query("select Board from Board where parentId = ?1")
//    List<Board> findByParentId(UUID id);
}
