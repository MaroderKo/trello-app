package spd.trello.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spd.trello.domain.Board;

import java.util.List;
import java.util.UUID;


@Repository
public interface BoardRepository extends AbstractParentBasedRepository<Board>{
//    @Query("select Board from Board where parentId = ?1")
//    List<Board> getRole(UUID id);
}
