package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domain.Domain;
import spd.trello.repository.AbstractRepository;

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
        return repository.save(t);
    }
    public T read(UUID id)
    {
        return repository.getById(id);
    }
    public T update (T t)
    {
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
