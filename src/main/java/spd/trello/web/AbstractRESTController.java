package spd.trello.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping(value = "/create")
    public ResponseEntity<T> Create(@RequestBody T t) {
        return new ResponseEntity<>(service.create(t), HttpStatus.OK);
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<T> Read(@PathVariable String id) {
        if (service.read(UUID.fromString(id)) == null)
            throw new ObjectNotFoundException();
        return new ResponseEntity<>(service.read(UUID.fromString(id)), HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<T> Update(@RequestBody T t) {
        if (service.read(t.getId()) == null)
            throw new ObjectNotFoundException();
        service.update(t);
        return new ResponseEntity<>(service.read((t.getId())), HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public HttpStatus Delete(@PathVariable String id)
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
