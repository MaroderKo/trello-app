package spd.trello.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED,reason = "Wrong login or password")
public class WrongLoginOrPasswordException extends RuntimeException{

    public WrongLoginOrPasswordException(Exception e) {
        super(e.getCause());
    }

    public WrongLoginOrPasswordException(){super("Wrong login or password");}
}
