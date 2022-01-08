package spd.trello.service;

import spd.trello.domain.User;
import spd.trello.repository.IRepository;

public class UserService extends AbstractService<User>{
    public UserService(IRepository<User> repository) {
        super(repository);
    }

}
