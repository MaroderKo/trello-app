package spd.trello.exception;

import java.util.UUID;

public class ObjectNotFoundException extends RuntimeException{
    public ObjectNotFoundException(UUID id, Class<?> clas) {
        super(clas.getName()+" with id '"+id+"' not found!");
    }
}
