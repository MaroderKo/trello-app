package spd.trello.repository;

import org.springframework.stereotype.Repository;
import spd.trello.domain.CheckList;

@Repository
public interface CheckListRepository extends AbstractParentBasedRepository<CheckList> {
//    @Query("select CheckList from CheckList t where CheckList.parentId = ?1")
//    List<CheckList> findByParentId(UUID id);
}
