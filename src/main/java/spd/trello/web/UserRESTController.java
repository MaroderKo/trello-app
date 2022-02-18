package spd.trello.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spd.trello.domain.User;
import spd.trello.repository.UserRepository;
import spd.trello.service.AbstractService;

@RestController
@RequestMapping("/api/users")
public class UserRESTController extends AbstractRESTController<User, UserRepository>{
    public UserRESTController(AbstractService<User, UserRepository> service) {
        super(service);
    }
}
