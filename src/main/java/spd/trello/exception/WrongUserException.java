package spd.trello.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT,reason = "You can't do it from this user!")
public class WrongUserException extends RuntimeException{
    public WrongUserException() {
        super("You can't do it from this user!");
    }

    public WrongUserException(Throwable cause) {
        super(cause);
    }
}
