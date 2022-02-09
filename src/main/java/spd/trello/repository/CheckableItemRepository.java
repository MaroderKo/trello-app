package spd.trello.repository;

import org.springframework.stereotype.Repository;
import spd.trello.domain.CheckableItem;

@Repository
public interface CheckableItemRepository extends AbstractParentBasedRepository<CheckableItem>{
//    @Query("select CheckableItem from CheckableItem where parentId = ?1")
//    List<CheckableItem> findByParentId(UUID id);
}
