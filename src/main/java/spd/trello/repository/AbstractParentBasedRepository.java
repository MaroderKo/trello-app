package spd.trello.repository;

import org.springframework.data.jpa.repository.Query;
import spd.trello.domain.Domain;
import spd.trello.domain.ParentBased;

import java.util.List;
import java.util.UUID;

public interface AbstractParentBasedRepository<T extends Domain & ParentBased> extends AbstractRepository<T>{
    @Query(value = "select t.type from #{#entityName} t where t.parentId  = ?1", nativeQuery = true)
    List<T> findByParentId(UUID id);
}
