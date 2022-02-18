package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domain.Domain;
import spd.trello.domain.Resource;
import spd.trello.exception.ObjectNotFoundException;
import spd.trello.repository.AbstractRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public abstract class AbstractService<T extends Domain, R extends AbstractRepository<T>> {

    protected R repository;

    protected AbstractService(R repository)
    {
        this.repository = repository;
    }

    public T create(T t)
    {
        return repository.saveAndFlush(t);
    }
    public T read(UUID id)
    {
        return repository.findById(id).orElseThrow(ObjectNotFoundException::new);
    }
    public T update (T t)
    {
        if (t instanceof Resource)
        {
            ((Resource) t).setUpdatedDate(LocalDateTime.now());
        }
        return repository.update(t);
    }
    public void delete(UUID id)
    {
        repository.deleteById(id);
    }
    public List<T> getAll()
    {
        return repository.findAll();
    }




}
