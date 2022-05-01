package spd.trello.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spd.trello.domain.User;
@Repository
public interface UserRepository extends AbstractRepository<User>{
    @Query(value = "select * from users where login  = ?1", nativeQuery = true)
    public User getByLogin(String login);
}
