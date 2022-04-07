package spd.trello.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT,reason = "File with this name already exist")
public class FileAlreadyExistException extends RuntimeException{

    public FileAlreadyExistException(Exception e) {
        super(e.getCause());
    }

    public FileAlreadyExistException(){super("File with this name already exist");}
}