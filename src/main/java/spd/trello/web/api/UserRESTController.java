package spd.trello.web.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Override
    @PreAuthorize("true")
    public ResponseEntity<User> Create(@RequestBody User user) {
        return super.Create(user);
    }
}
