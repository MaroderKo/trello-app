package spd.trello.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spd.trello.domain.Card;
import spd.trello.domain.Reminder;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReminderRepository extends AbstractParentBasedRepository<Reminder>{
    @Query(value = "select * from reminder where active = true and remind_on < now()",nativeQuery = true)
    List<Reminder> findActive();

    @Query(value = "update reminder AS r SET active = false where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    void deactivate(UUID id);
}
