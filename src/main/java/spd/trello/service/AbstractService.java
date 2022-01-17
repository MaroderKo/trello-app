package spd.trello.service;

import spd.trello.domain.Domain;
import spd.trello.repository.AbstractRepository;

import java.util.List;
import java.util.UUID;

public abstract class AbstractService<T extends Domain> {

    protected AbstractRepository<T> repository;

    protected AbstractService(AbstractRepository<T> repository)
    {
        this.repository = repository;
    }

    public T create(T t)
    {
        return repository.create(t);
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
        repository.delete(id);
    }
    public List<T> getAll()
    {
        return repository.getAll();
    }
    public List<T> getParent(UUID id)
    {
        return repository.getParent(id);
    }




}
