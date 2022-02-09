package spd.trello.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "Resource not found")
public class ObjectNotFoundException extends RuntimeException{

    public ObjectNotFoundException(Exception e) {
        super(e.getCause());
    }

    public ObjectNotFoundException(){}
}
