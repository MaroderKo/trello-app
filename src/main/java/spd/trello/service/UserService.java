package spd.trello.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import spd.trello.domain.User;
import spd.trello.exception.WrongUserException;
import spd.trello.repository.UserRepository;
import spd.trello.security.SecurityConfig;

import java.util.List;
import java.util.UUID;

@Service
public class UserService extends AbstractService<User, UserRepository>{
    public UserService(UserRepository repository) {
        super(repository);
    }

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private boolean userCheck(UUID uuid)
    {
        User user = getByLogin(SecurityConfig.getUserName());
        return user.getId().equals(uuid);
    }

    @Override
    public User create(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return super.create(user);
    }

    @Override
    public User update(User user) {
        if (!userCheck(user.getId()))
            throw new WrongUserException();
        return super.update(user);
    }

    @Override
    public void delete(UUID id) {
        if (!userCheck(id))
            throw new WrongUserException();
        super.delete(id);
    }

    @Override
    public List<User> getAll() {
        return super.getAll();
    }

    public User getByLogin(String login)
    {
        return repository.getByLogin(login);
    };
}
