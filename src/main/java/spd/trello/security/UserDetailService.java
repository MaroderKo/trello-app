package spd.trello.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerMapping;
import spd.trello.domain.CreateObjectContext;
import spd.trello.domain.Role;
import spd.trello.domain.User;
import spd.trello.exception.ObjectNotFoundException;
import spd.trello.service.UserService;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserDetailService implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(UserDetailService.class);

    @Autowired
    private RoleManager roleManager;

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    CreateObjectContext context;

    Pattern pairRegex;

    @PostConstruct
    private void init()
    {
        String BASE_UUID_REGEX = "[a-f0-9]{8}(?:-[a-f0-9]{4}){4}[a-f0-9]{8}";
        pairRegex = Pattern.compile(BASE_UUID_REGEX);
    }



    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        String object_id = null;
        String url = request.getServletPath();

        try {
            Matcher matcher = pairRegex.matcher(url);
            if (matcher.find())
            {
                object_id = matcher.group();
            }
        } catch (NullPointerException ignored){};
        UUID object_uuid = null;
        if (url.contains("create") && context != null)
        {
            object_uuid = context.getParentId();
        }
        else if (object_id != null) {
            object_uuid = UUID.fromString(object_id);
        }
        User user = Optional.ofNullable(userService.getByLogin(login)).orElseThrow(() -> new ObjectNotFoundException());
        return fromUser(user, object_uuid, url);
    }

    private SecurityUser fromUser(User user, UUID object_id, String url)
    {
        if (object_id != null) {
            Role role = roleManager.getRole(url, object_id, user);
            LOG.info("???????????? ???????? ?????? ???????????????? - "+role.name());
            return new SecurityUser(user.getLogin(), user.getPassword(), role.getAuthorities());
        }
        else
        {
            LOG.info("???????????? ???????? ?????????????????????????? - ??????????");
            return new SecurityUser(user.getLogin(), user.getPassword(), Role.GUEST.getAuthorities());
        }
    }
}
