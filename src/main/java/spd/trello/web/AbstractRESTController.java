package spd.trello.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import spd.trello.domain.Domain;
import spd.trello.exception.ObjectNotFoundException;
import spd.trello.repository.AbstractRepository;
import spd.trello.service.AbstractService;

import java.util.List;
import java.util.UUID;

public class AbstractRESTController<T extends Domain, R extends AbstractRepository<T>> {
    final AbstractService<T,R> service;

    public AbstractRESTController(AbstractService<T,R> service) {
        this.service = service;
    }

    @RequestMapping
    public List<T> showAll() {
        return service.getAll();
    }

    @RequestMapping(value = "/create")
    public ResponseEntity<T> Create(@RequestBody T t) {
        return new ResponseEntity<>(service.create(t), HttpStatus.OK);
    }

    @RequestMapping(value = "/update")
    public ResponseEntity<T> Update(@RequestBody T t) {
        if (service.read(t.getId()) == null)
            throw new ObjectNotFoundException();
        service.update(t);
        return new ResponseEntity<>(service.read((t.getId())), HttpStatus.OK);
    }


    @RequestMapping("/delete")
    public HttpStatus Delete(@RequestBody String id)
    {

        if (service.read(UUID.fromString(id)) == null)
            throw new ObjectNotFoundException();
        service.delete(UUID.fromString(id));
        if (service.read(UUID.fromString(id)) == null)
            return HttpStatus.OK;
        else
            return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
