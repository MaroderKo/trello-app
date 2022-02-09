package spd.trello.web;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import spd.trello.domain.Domain;
import spd.trello.domain.ParentBased;
import spd.trello.repository.AbstractParentBasedRepository;
import spd.trello.service.AbstractParentBasedService;
import spd.trello.service.AbstractService;

import java.util.List;
import java.util.UUID;

public class ParentBasedController<T extends Domain & ParentBased, R extends AbstractParentBasedRepository<T>> extends AbstractRESTController<T, R>{

    final AbstractParentBasedService<T,R> service;

    public ParentBasedController(AbstractService<T, R> service, AbstractParentBasedService<T,R> service1) {
        super(service);
        this.service = service1;
    }

    @RequestMapping("parent/{id}")
    public List<T> byParent(@PathVariable String id)
    {
        return service.getParent(UUID.fromString(id));
    }



}
