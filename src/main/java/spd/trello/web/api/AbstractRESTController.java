package spd.trello.web.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import spd.trello.domain.*;
import spd.trello.exception.ObjectNotFoundException;
import spd.trello.repository.AbstractRepository;
import spd.trello.security.SecurityConfig;
import spd.trello.service.AbstractService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

public class AbstractRESTController<T extends Domain, R extends AbstractRepository<T>> {
    final AbstractService<T,R> service;
    @Autowired
    CreateObjectContext context;
    
    private static final Logger LOG = LoggerFactory.getLogger(AbstractRESTController.class);

    public AbstractRESTController(AbstractService<T,R> service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('read')")
    public List<T> showAll() {
        return service.getAll();
    }

    @PostMapping(value = "/create")
    public ResponseEntity<T> Create(@Valid @RequestBody T t) {
        try {
            if (t instanceof ParentBased) {
                context.setParentId(((ParentBased) t).parentId);
            }
            if (t instanceof Resource) {
                ((Resource) t).setCreatedBy(SecurityConfig.getUserName());
            }
            return CreateWithId(t);
        }
        finally {
            context.setParentId(null);
        }
    }

    @PreAuthorize("hasAuthority('create')")
    public ResponseEntity<T> CreateWithId(T t) {
        if (t instanceof Resource)
        {
            ((Resource) t).setCreatedBy(SecurityConfig.getUserName());
        }
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

    @ExceptionHandler(ConstraintViolationException.class)
    public String handleIOException(ConstraintViolationException ex, HttpServletRequest request) {
        LOG.warn(ex.getMessage());
        return ClassUtils.getShortName(ex.getClass());
    }

}
