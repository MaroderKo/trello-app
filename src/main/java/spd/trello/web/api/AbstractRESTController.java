package spd.trello.web.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import spd.trello.domain.Board;
import spd.trello.domain.Domain;
import spd.trello.domain.Resource;
import spd.trello.domain.Workspace;
import spd.trello.exception.ObjectNotFoundException;
import spd.trello.repository.AbstractRepository;
import spd.trello.security.SecurityConfig;
import spd.trello.service.AbstractService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

public class AbstractRESTController<T extends Domain, R extends AbstractRepository<T>> {
    final AbstractService<T,R> service;

    public AbstractRESTController(AbstractService<T,R> service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('read')")
    public List<T> showAll() {
        return service.getAll();
    }

    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('create')")
    public ResponseEntity<T> Create(@Valid @RequestBody T t) {
        return new ResponseEntity<>(service.create(t), HttpStatus.OK);
    }

    @GetMapping(value = "/id/{id}")
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<T> Read(@PathVariable String id) {
        if (service.read(UUID.fromString(id)) == null)
            throw new ObjectNotFoundException();
        return new ResponseEntity<>(service.read(UUID.fromString(id)), HttpStatus.OK);
    }

    @PostMapping(value = "/id/{id}")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<T> Update(@PathVariable String id,@Valid @RequestBody T t) {
        if (service.read(t.getId()) == null)
            throw new ObjectNotFoundException();
        if (t instanceof Resource)
            ((Resource) t).setUpdatedBy(SecurityConfig.getUserName());
        service.update(t);
        return new ResponseEntity<>(service.read((t.getId())), HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('delete')")
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
