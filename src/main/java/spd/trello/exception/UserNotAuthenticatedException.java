package spd.trello.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED,reason = "User not authenticated")
public class UserNotAuthenticatedException extends RuntimeException{
    public UserNotAuthenticatedException(Exception e) {
        super(e.getCause());
    }

    public UserNotAuthenticatedException(){super("User not authenticated");}

}
