package spd.trello.repository;

import org.springframework.stereotype.Repository;
import spd.trello.domain.Card;

@Repository
public interface CardRepository extends AbstractParentBasedRepository<Card>{
//    @Query("select Card from Card where parentId = ?1")
//    List<Card> findByParentId(UUID id);
}
