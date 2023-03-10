package spd.trello.web.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import spd.trello.domain.AuthUserDAO;
import spd.trello.domain.User;
import spd.trello.security.JWTTokenProvider;
import spd.trello.security.SecurityConfig;
import spd.trello.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationRESTController {

    private final AuthenticationManager authenticationManager;
    private JWTTokenProvider jwtTokenProvider;
    private UserService userService;

    public AuthenticationRESTController(AuthenticationManager authenticationManager, JWTTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> authenticate(@RequestBody AuthUserDAO authUser){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authUser.getLogin(), authUser.getPassword()));
        User user = userService.getByLogin(authUser.getLogin());
        if (user == null)
            throw new UsernameNotFoundException("User doesn't exist!");
        String token = jwtTokenProvider.createToken(user.getLogin(), user.getId());
        Map<Object, Object> response = new HashMap<>();
        response.put("login", user.getLogin());
        response.put("token", token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/username")
    public String username()
    {
        return SecurityConfig.getUserName();
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response)
    {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, null);
    }

}
