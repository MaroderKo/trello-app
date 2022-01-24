package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domain.User;
import spd.trello.repository.AbstractRepository;

@Service
public class UserService extends AbstractService<User>{
    public UserService(AbstractRepository<User> repository) {
        super(repository);
    }

}
