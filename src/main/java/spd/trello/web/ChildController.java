package spd.trello.web;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import spd.trello.domain.Domain;
import spd.trello.service.AbstractService;

import java.util.List;
import java.util.UUID;

public class ChildController<T extends Domain> extends AbstractRESTController<T>{
    public ChildController(AbstractService<T> service) {
        super(service);
    }

    @RequestMapping("parent/{id}")
    public List<T> byParent(@PathVariable String id)
    {
        return service.getParent(UUID.fromString(id));
    }



}
