package spd.trello.service;

import spd.trello.domain.User;
import spd.trello.repository.AbstractRepository;

public class UserService extends AbstractService<User>{
    public UserService(AbstractRepository<User> repository) {
        super(repository);
    }

}
