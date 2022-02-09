package spd.trello.repository;

import org.springframework.stereotype.Repository;
import spd.trello.domain.CardList;


@Repository
public interface CardListRepository extends AbstractParentBasedRepository<CardList>{
//    @Query("select CardList from CardList where parentId = ?1")
//    List<CardList> findByParentId(UUID BoardId);
}
