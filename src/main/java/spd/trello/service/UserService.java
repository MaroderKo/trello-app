package spd.trello.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import spd.trello.domain.User;
import spd.trello.repository.UserRepository;

@Service
public class UserService extends AbstractService<User, UserRepository>{
    public UserService(UserRepository repository) {
        super(repository);
    }

    //@Autowired
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public User create(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return super.create(user);
    }


    public User getByLogin(String login)
    {
        return repository.getByLogin(login);
    };
}
